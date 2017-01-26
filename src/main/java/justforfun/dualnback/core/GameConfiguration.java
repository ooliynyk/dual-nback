package justforfun.dualnback.core;

public class GameConfiguration {

	private final int nBackLevel;
	private final int trials;
	private final int secPerTrial;

	public GameConfiguration(int nBackLevel, int trials, int secPerTrial) {
		this.nBackLevel = nBackLevel;
		this.trials = trials;
		this.secPerTrial = secPerTrial;
	}

	public int getNBackLevel() {
		return nBackLevel;
	}

	public int getTrials() {
		return trials;
	}

	public int getSecPerTrial() {
		return secPerTrial;
	}

}
