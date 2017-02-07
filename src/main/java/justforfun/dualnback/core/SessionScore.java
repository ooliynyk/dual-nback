package justforfun.dualnback.core;

public class SessionScore {

	private int positionMatchesTotal;
	private int letterMatchesTotal;

	private int positionMistakesTotal;
	private int letterMistakesTotal;
	
	public void positionMatches() {
		positionMatchesTotal++;
	}

	public void letterMatches() {
		letterMatchesTotal++;
	}

	public void positionMistake() {
		positionMistakesTotal++;
	}

	public void letterMistake() {
		letterMistakesTotal++;
	}

	public int getPositionMatchesTotal() {
		return positionMatchesTotal;
	}

	public int getLetterMatchesTotal() {
		return letterMatchesTotal;
	}

	public int getPositionMistakesTotal() {
		return positionMistakesTotal;
	}

	public int getLetterMistakesTotal() {
		return letterMistakesTotal;
	}

	@Override
	public String toString() {
		return "SessionScore [positionMatchesTotal=" + positionMatchesTotal + ", letterMatchesTotal="
				+ letterMatchesTotal + ", positionMistakesTotal=" + positionMistakesTotal + ", letterMistakesTotal="
				+ letterMistakesTotal + "]";
	}

}
