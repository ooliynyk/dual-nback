package justforfun.dualnback.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import justforfun.dualnback.utils.RandomTrialStateGenerator;
import justforfun.dualnback.utils.TrialStateGenerator;
import justforfun.dualnback.utils.TrialStateSequence;

public class DualNBackSession implements Session, SessionState {

	private TrialStateGenerator stateGenerator;
	private GameConfiguration gameConfig;
	private TrialStateSequence stateSequence;
	private SessionScore score;
	private Timer scheduler;
	private CountDownLatch duringTrialsLatch;
	private List<SessionStateListener> stateListeners;

	public DualNBackSession(GameConfiguration gameConfiguration) {
		gameConfig = new GameConfiguration(gameConfiguration);
		stateSequence = new TrialStateSequence(gameConfig.getNBackLevel());
		stateGenerator = new RandomTrialStateGenerator();
		score = new SessionScore();
		scheduler = new Timer();
		duringTrialsLatch = new CountDownLatch(gameConfig.getTrials());
		stateListeners = new ArrayList<>();
	}

	@Override
	public void start() {
		long trialPeriodInMillis = TimeUnit.MILLISECONDS.convert(gameConfig.getSecPerTrial(), TimeUnit.SECONDS);
		scheduler.scheduleAtFixedRate(new Trial(), trialPeriodInMillis / 2, trialPeriodInMillis);
	}

	@Override
	public void cancel() {
		scheduler.cancel();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public SessionScore get() throws InterruptedException {
		duringTrialsLatch.await();
		return score;
	}

	@Override
	public boolean isCurrentLetterAsNBack() {
		Letter nBackLatter = stateSequence.getNBackState().getLetter();
		Letter currentLatter = stateSequence.getCurrentState().getLetter();

		boolean currentLetterAsNBack = currentLatter.equals(nBackLatter);
		if (currentLetterAsNBack) {
			score.letterMatches();
		} else {
			score.letterMistake();
		}

		return currentLetterAsNBack;
	}

	@Override
	public boolean isCurrentPositionAsNBack() {
		Position nBackPosition = stateSequence.getNBackState().getPosition();
		Position currentPosition = stateSequence.getCurrentState().getPosition();

		boolean currentPositionAsNBack = currentPosition.equals(nBackPosition);
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

	private class Trial extends TimerTask {

		@Override
		public void run() {
			TrialState state = stateGenerator.nextState();
			System.out.println(state);
			saveState(state);
			notifyListeners(state);
		}
		
		private void saveState(TrialState state) {
			stateSequence.addState(state);
			duringTrialsLatch.countDown();
		}
		
		private void notifyListeners(TrialState state) {
			for (SessionStateListener listener : stateListeners) {
				listener.onNextTrial(state);
			}
		}

	}

}
