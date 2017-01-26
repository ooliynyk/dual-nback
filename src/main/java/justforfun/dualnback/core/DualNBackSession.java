package justforfun.dualnback.core;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections4.queue.CircularFifoQueue;

public class DualNBackSession {

	private GameStateGenerator stateGenerator = new RandomGameStateGenerator();

	private GameConfiguration gameConfig;

	private CircularFifoQueue<GameState> stateSequence;

	private Timer scheduler = new Timer();

	public DualNBackSession(GameConfiguration gameConfiguration) {
		this.gameConfig = gameConfiguration;
		this.stateSequence = new CircularFifoQueue<>(gameConfig.getNBack());
	}

	public void start() {
		long trialPeriodInMillis = TimeUnit.MILLISECONDS.convert(gameConfig.getSecPerTrial(), TimeUnit.SECONDS);
		scheduler.scheduleAtFixedRate(new Trial(), trialPeriodInMillis / 2, trialPeriodInMillis);
	}

	public void cancel() {
		scheduler.cancel();
	}

	public boolean isCurrentLetterAsNBack() {
		Letter nBackLatter = getNBackGameState().getLetter();
		Letter currentLatter = getCurrentGameState().getLetter();

		return currentLatter.equals(nBackLatter);
	}

	public boolean isCurrentPositionAsNBack() {
		Position nBackPosition = getNBackGameState().getPosition();
		Position currentPosition = getCurrentGameState().getPosition();

		return currentPosition.equals(nBackPosition);
	}

	@Override
	public String toString() {
		return "DualNBackSession [stateSequence=" + stateSequence + "]";
	}
	
	private GameState getNBackGameState() {
		return stateSequence.peek();
	}

	private GameState getCurrentGameState() {
		return stateSequence.get(0);
	}

	private class Trial extends TimerTask {

		@Override
		public void run() {
			stateSequence.add(stateGenerator.nextState());
		}

	}

	public static void main(String[] args) throws InterruptedException {
		DualNBackSession session = new DualNBackSession(new GameConfiguration(2, 32, 3));
		session.start();
		for (int i = 0; i < 10; i++) {
			System.out.println(session);
			Thread.sleep(3000);
		}
	}

}
