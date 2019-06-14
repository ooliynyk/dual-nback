package dualnback.utils;

import static org.junit.Assert.*;

import org.junit.Test;

import dualnback.core.Letter;
import dualnback.core.Position;
import dualnback.core.TrialState;

public class TrialStateSequenceTest {

	private static final int N_BACK_LEVEL = 2;

	private TrialState firstState = new TrialState(Letter.A, Position.BOTTOM_CENTER);
	private TrialState secondState = new TrialState(Letter.B, Position.MIDDLE_LEFT);
	private TrialState thirdState = new TrialState(Letter.C, Position.TOP_CENTER);

	@Test
	public void testNBackState() {
		TrialStateSequence seq = new TrialStateSequence(N_BACK_LEVEL);
		seq.addState(firstState);
		seq.addState(secondState);
		seq.addState(thirdState);

		assertEquals(firstState, seq.getNBackState());
	}

	@Test
	public void testCurrentState() {
		TrialStateSequence seq = new TrialStateSequence(N_BACK_LEVEL);
		seq.addState(firstState);
		seq.addState(secondState);
		seq.addState(thirdState);

		assertEquals(thirdState, seq.getCurrentState());
	}

	@Test
	public void testShift() {
		TrialState newState = new TrialState(Letter.C, Position.TOP_CENTER);
		TrialStateSequence seq = new TrialStateSequence(N_BACK_LEVEL);
		seq.addState(firstState);
		seq.addState(secondState);
		seq.addState(thirdState);
		seq.addState(newState);

		assertEquals(secondState, seq.getNBackState());
		assertEquals(newState, seq.getCurrentState());
	}

}
