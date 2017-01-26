package justforfun.dualnback.core;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import justforfun.dualnback.utils.GameStateGenerator;
import justforfun.dualnback.utils.GameStateSequence;
import justforfun.dualnback.utils.RandomGameStateGenerator;

public class DualNBackSession {

	private GameStateGenerator stateGenerator = new RandomGameStateGenerator();

	private GameConfiguration gameConfig;

	private GameStateSequence stateSequence;

	private Timer scheduler = new Timer();

	public DualNBackSession(GameConfiguration gameConfiguration) {
		gameConfig = gameConfiguration;
		stateSequence = new GameStateSequence(gameConfig.getNBackLevel());
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

	public static void main(String[] args) throws InterruptedException {
		DualNBackSession session = new DualNBackSession(new GameConfiguration(2, 32, 3));
		session.start();
		for (int i = 0; i < 10; i++) {
			System.out.println(session);
			Thread.sleep(3000);
			System.out.println(session.isCurrentLetterAsNBack());
		}
		session.cancel();
	}

}
