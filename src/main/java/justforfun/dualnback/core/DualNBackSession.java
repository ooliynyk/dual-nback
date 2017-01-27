package justforfun.dualnback.core;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import justforfun.dualnback.utils.RandomTrialStateGenerator;
import justforfun.dualnback.utils.TrialStateGenerator;
import justforfun.dualnback.utils.TrialStateSequence;

public class DualNBackSession {

	private TrialStateGenerator stateGenerator;

	private GameConfiguration gameConfig;

	private TrialStateSequence stateSequence;

	private SessionScore score;

	private Timer scheduler = new Timer();
	
	private CountDownLatch duringTrialsLatch;

	public DualNBackSession(GameConfiguration gameConfiguration) {
		gameConfig = new GameConfiguration(gameConfiguration);
		stateSequence = new TrialStateSequence(gameConfig.getNBackLevel());
		stateGenerator = new RandomTrialStateGenerator();
		score = new SessionScore();
		duringTrialsLatch = new CountDownLatch(gameConfig.getTrials());
	}

	public void start() {
		long trialPeriodInMillis = TimeUnit.MILLISECONDS.convert(gameConfig.getSecPerTrial(), TimeUnit.SECONDS);
		scheduler.scheduleAtFixedRate(new Trial(), trialPeriodInMillis / 2, trialPeriodInMillis);
	}
	
	public SessionScore get() throws InterruptedException {
		duringTrialsLatch.await();
		return score;
	}

	public void cancel() {
		scheduler.cancel();
	}

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
	public String toString() {
		return "DualNBackSession [stateSequence=" + stateSequence + "]";
	}

	private class Trial extends TimerTask {

		@Override
		public void run() {
			stateSequence.addState(stateGenerator.nextState());
			System.out.println(stateSequence);
			duringTrialsLatch.countDown();
		}

	}

}
