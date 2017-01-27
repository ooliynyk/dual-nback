package justforfun.dualnback.core;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import justforfun.dualnback.utils.RandomTrialStateGenerator;
import justforfun.dualnback.utils.TrialStateGenerator;
import justforfun.dualnback.utils.TrialStateSequence;

public class DualNBackSession {

	private TrialStateGenerator stateGenerator;

	private GameConfiguration gameConfig;

	private TrialStateSequence stateSequence;

	private Timer scheduler = new Timer();

	public DualNBackSession(GameConfiguration gameConfiguration) {
		gameConfig = new GameConfiguration(gameConfiguration);
		stateSequence = new TrialStateSequence(gameConfig.getNBackLevel());
		stateGenerator = new RandomTrialStateGenerator();
	}

	public void start() {
		long trialPeriodInMillis = TimeUnit.MILLISECONDS.convert(gameConfig.getSecPerTrial(), TimeUnit.SECONDS);
		scheduler.scheduleAtFixedRate(new Trial(), trialPeriodInMillis / 2, trialPeriodInMillis);
	}

	public void cancel() {
		scheduler.cancel();
	}

	public boolean isCurrentLetterAsNBack() {
		Letter nBackLatter = stateSequence.getNBackState().getLetter();
		Letter currentLatter = stateSequence.getCurrentState().getLetter();

		return currentLatter.equals(nBackLatter);
	}

	public boolean isCurrentPositionAsNBack() {
		Position nBackPosition = stateSequence.getNBackState().getPosition();
		Position currentPosition = stateSequence.getCurrentState().getPosition();

		return currentPosition.equals(nBackPosition);
	}

	@Override
	public String toString() {
		return "DualNBackSession [stateSequence=" + stateSequence + "]";
	}

	private class Trial extends TimerTask {

		@Override
		public void run() {
			stateSequence.addState(stateGenerator.nextState());
		}

	}

}
