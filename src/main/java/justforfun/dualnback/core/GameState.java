package justforfun.dualnback.core;

public class GameState {

	private final Letter letter;
	private final Position position;

	public GameState(Letter letter, Position position) {
		super();
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
		return "GameState [letter=" + letter + ", position=" + position + "]";
	}

}
