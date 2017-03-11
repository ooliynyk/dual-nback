package justforfun.dualnback.utils;

import static org.junit.Assert.*;

import org.junit.Test;

import justforfun.dualnback.core.Letter;
import justforfun.dualnback.core.Position;
import justforfun.dualnback.core.TrialState;

public class TrialStateSequenceTest {

	private static final int N_BACK_LEVEL = 2;

	private TrialState firstState = new TrialState(Letter.A, Position.BOTTOM_CENTER);
	private TrialState secondState = new TrialState(Letter.B, Position.MIDDLE_LEFT);
	private TrialState thirthState = new TrialState(Letter.C, Position.TOP_CENTER);

	@Test
	public void testNBackState() {
		TrialStateSequence seq = new TrialStateSequence(N_BACK_LEVEL);
		seq.addState(firstState);
		seq.addState(secondState);
		seq.addState(thirthState);

		assertEquals(firstState, seq.getNBackState());
	}

	@Test
	public void testCurrentState() {
		TrialStateSequence seq = new TrialStateSequence(N_BACK_LEVEL);
		seq.addState(firstState);
		seq.addState(secondState);
		seq.addState(thirthState);

		assertEquals(thirthState, seq.getCurrentState());
	}

	@Test
	public void testShift() {
		TrialState newState = new TrialState(Letter.C, Position.TOP_CENTER);
		TrialStateSequence seq = new TrialStateSequence(N_BACK_LEVEL);
		seq.addState(firstState);
		seq.addState(secondState);
		seq.addState(thirthState);
		seq.addState(newState);

		assertEquals(secondState, seq.getNBackState());
		assertEquals(newState, seq.getCurrentState());
	}

}
