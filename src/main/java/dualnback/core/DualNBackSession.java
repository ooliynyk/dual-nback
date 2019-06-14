package dualnback.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import dualnback.utils.RandomTrialStateGenerator;
import dualnback.utils.TrialStateGenerator;
import dualnback.utils.TrialStateSequence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DualNBackSession implements Session, SessionState {

	private static final Logger logger = LoggerFactory.getLogger(DualNBackSession.class);

	private TrialStateGenerator stateGenerator;
	private GameConfiguration gameConfig;
	private TrialStateSequence stateSequence;
	private TrialStateChecker stateChecker;
	private SessionScore score;
	private Timer scheduler;
	private ExecutorService executor;
	private CountDownLatch duringTrialsLatch;
	private List<SessionStateListener> stateListeners;

	private static volatile boolean pause;

	public DualNBackSession(GameConfiguration gameConfiguration) {
		gameConfig = new GameConfiguration(gameConfiguration);
		stateSequence = new TrialStateSequence(gameConfig.getNBackLevel());
		stateChecker = new TrialStateChecker(stateSequence);
		stateGenerator = new RandomTrialStateGenerator();
		score = new SessionScore();
		scheduler = new Timer();
		executor = Executors.newSingleThreadExecutor();
		duringTrialsLatch = new CountDownLatch(gameConfig.getTrials());
		stateListeners = new ArrayList<>();

		addStateListener(stateChecker);
	}

	@Override
	public void start() {
		logger.info("Starting a new session");
		scheduleTrials();
		notifyOnFinish();
	}

	@Override
	public void finish() {
		logger.info("Finishing session");
		scheduler.cancel();
		executor.shutdownNow();
	}

	@Override
	public synchronized void pause() {
		pause = true;
	}

	@Override
	public synchronized void unpause() {
		pause = false;
	}

	@Override
	public boolean letterMatches() {
		boolean matches = stateChecker.checkLetterMatching();
		if (matches) {
			score.letterMatches();
		} else {
			score.letterMistake();
		}

		return matches;
	}

	@Override
	public boolean positionMatches() {
		boolean matches = stateChecker.checkPositionMatching();
		if (matches) {
			score.positionMatches();
		} else {
			score.positionMistake();
		}

		return matches;
	}

	@Override
	public void addStateListener(SessionStateListener listener) {
		stateListeners.add(listener);
	}

	@Override
	public String toString() {
		return "DualNBackSession [stateSequence=" + stateSequence + "]";
	}

	public TrialStateChecker getStateChecker() {
		return stateChecker;
	}

	private void scheduleTrials() {
		long trialPeriodInMillis = TimeUnit.MILLISECONDS.convert(gameConfig.getSecPerTrial(), TimeUnit.SECONDS);
		scheduler.scheduleAtFixedRate(new Trial(trialPeriodInMillis), trialPeriodInMillis / 2, 1);
	}

	private void notifyOnFinish() {
		executor.execute(() -> {
			try {
				duringTrialsLatch.await();
				stateListeners.forEach(sl -> sl.onFinish(score));
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				finish();
			}
		});
	}

	private void notifyOnNextTrial(TrialState state) {
		stateListeners.forEach(sl -> sl.onNextTrial(state));
	}

	private class Trial extends TimerTask {

		private long trialPeriodInMillis;

		public Trial(long trialPeriodInMillis) {
			this.trialPeriodInMillis = trialPeriodInMillis;
		}

		@Override
		public void run() {
			try {
				TrialState state = stateGenerator.nextState();
				saveState(state);
				notifyOnNextTrial(state);
				awaitForFinish();
				registerMatchingMissed();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				duringTrialsLatch.countDown();
			}
		}

		private void awaitForFinish() throws InterruptedException {
			int periods = 50;
			long periodDelta = trialPeriodInMillis / periods;
			for (int i = 0; i < periods; i++) {
				Thread.sleep(periodDelta);
				while (pause)
					;
			}
		}

		private void saveState(TrialState state) {
			logger.info("Saving trial state '{}'", state);
			stateSequence.addState(state);
			logger.debug("Sequence '{}'", stateSequence);
		}

		private void registerMatchingMissed() throws InterruptedException {
			if (stateChecker.isLetterMatchingMissed()) {
				score.letterMistake();
			}

			if (stateChecker.isPositionMatchingMissed()) {
				score.positionMistake();
			}
		}

	}

}
