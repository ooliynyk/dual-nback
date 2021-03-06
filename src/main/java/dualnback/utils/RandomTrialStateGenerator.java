package dualnback.utils;

import java.util.Random;

import dualnback.core.TrialState;
import dualnback.core.Letter;
import dualnback.core.Position;

public class RandomTrialStateGenerator implements TrialStateGenerator {

	private static final int TOTAL_LETTERS = Letter.values().length;
	private static final int TOTAL_POSITIONS = Position.values().length;

	private final Random rand = new Random(System.currentTimeMillis());

	@Override
	public TrialState nextState() {
		return new TrialState(generateNextLetter(), generateNextPosition());
	}

	private Letter generateNextLetter() {
		int letterAtIdx = rand.nextInt(TOTAL_LETTERS);
		return Letter.values()[letterAtIdx];
	}

	private Position generateNextPosition() {
		int positionAtIdx = rand.nextInt(TOTAL_POSITIONS);
		return Position.values()[positionAtIdx];
	}

}
