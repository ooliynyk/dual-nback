package justforfun.dualnback.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import justforfun.dualnback.utils.RandomTrialStateGenerator;
import justforfun.dualnback.utils.TrialStateGenerator;
import justforfun.dualnback.utils.TrialStateSequence;

public class DualNBackSession implements Session, SessionState {

	private TrialStateGenerator stateGenerator;
	private GameConfiguration gameConfig;
	private TrialStateSequence stateSequence;
	private TrialStateChecker stateChecker;
	private SessionScore score;
	private Timer scheduler;
	private ExecutorService executor;
	private CountDownLatch duringTrialsLatch;
	private List<SessionStateListener> stateListeners;

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
	}

	@Override
	public void start() {
		scheduleTrials();
		notifyOnFinish();
	}

	@Override
	public void cancel() {
		scheduler.cancel();
		executor.shutdownNow();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isCurrentLetterAsNBack() {
		boolean currentLetterAsNBack = stateChecker.checkLetterMatching();
		if (currentLetterAsNBack) {
			score.letterMatches();
		} else {
			score.letterMistake();
		}

		return currentLetterAsNBack;
	}

	@Override
	public boolean isCurrentPositionAsNBack() {
		boolean currentPositionAsNBack = stateChecker.checkPositionMatching();
		if (currentPositionAsNBack) {
			score.positionMatches();
		} else {
			score.positionMistake();
		}

		return currentPositionAsNBack;
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
				cancel();
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
				Thread.sleep(trialPeriodInMillis);
				registerMatchingMissed();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				duringTrialsLatch.countDown();
			}
		}

		private void saveState(TrialState state) {
			stateSequence.addState(state);
			System.out.println(stateSequence);
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
