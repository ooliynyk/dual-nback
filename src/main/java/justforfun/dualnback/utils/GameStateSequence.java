package justforfun.dualnback.utils;

import java.util.concurrent.LinkedBlockingDeque;

import justforfun.dualnback.core.GameState;

public class GameStateSequence {

	private int nBackLevel;
	private LinkedBlockingDeque<GameState> deque;

	public GameStateSequence(int nBackLevel) {
		this.nBackLevel = nBackLevel;
		deque = new LinkedBlockingDeque<>(nBackLevel);
	}

	public void addState(GameState state) {
		if (deque.size() == nBackLevel) {
			deque.removeLast();
		}

		deque.addFirst(state);
	}

	public GameState getNBackState() {
		awaitWhileEmpty();
		return deque.peekLast();
	}

	public GameState getCurrentState() {
		awaitWhileEmpty();
		return deque.peekFirst();
	}

	private void awaitWhileEmpty() {
		GameState state = null;
		if (deque.isEmpty()) {
			try {
				state = deque.takeFirst();
				deque.addFirst(state);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public String toString() {
		return "GameStateSequence [deque=" + deque + "]";
	}

}
