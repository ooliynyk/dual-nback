package justforfun.dualnback.core;

import java.util.ArrayDeque;
import java.util.Deque;

public class DualNBackSession {

	private final GameStateGenerator gameStateGenerator = new RandomGameStateGenerator();

	private final int nBack;
	private final int trials;
	private final int speed;

	private Deque<GameState> stateSequence = new ArrayDeque<>();

	public DualNBackSession(int nBack, int trials, int speed) {
		this.nBack = nBack;
		this.trials = trials;
		this.speed = speed;
	}

}
