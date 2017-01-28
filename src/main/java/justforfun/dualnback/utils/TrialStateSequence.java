package justforfun.dualnback.utils;

import java.util.concurrent.LinkedBlockingDeque;

import justforfun.dualnback.core.TrialState;

public class TrialStateSequence {

	private int capacity;
	private LinkedBlockingDeque<TrialState> deque;

	public TrialStateSequence(int nBackLevel) {
		capacity = nBackLevel + 1;
		deque = new LinkedBlockingDeque<>(capacity);
	}

	public void addState(TrialState state) {
		if (deque.size() == capacity) {
			deque.removeLast();
		}

		deque.addFirst(state);
	}

	public TrialState getNBackState() {
		awaitWhileEmpty();
		return deque.peekLast();
	}

	public TrialState getCurrentState() {
		awaitWhileEmpty();
		return deque.peekFirst();
	}

	private void awaitWhileEmpty() {
		TrialState state = null;
		if (deque.isEmpty()) {
			try {
				state = deque.takeFirst();
				deque.addLast(state);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public String toString() {
		return "TrialStateSequence [deque=" + deque + "]";
	}

}
