package dualnback.core;

public class TrialState {

	private final Letter letter;
	private final Position position;

	public TrialState(Letter letter, Position position) {
		this.letter = letter;
		this.position = position;
	}

	public Letter getLetter() {
		return letter;
	}

	public Position getPosition() {
		return position;
	}

	@Override
	public String toString() {
		return "TrialState [letter=" + letter + ", position=" + position + "]";
	}

}
