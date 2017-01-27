package justforfun.dualnback.core;

public class GameConfiguration {

	private int nBackLevel;
	private int trials;
	private int secPerTrial;

	public GameConfiguration(GameConfiguration other) {
		this.nBackLevel = other.nBackLevel;
		this.trials = other.trials;
		this.secPerTrial = other.secPerTrial;
	}

	public GameConfiguration(int nBackLevel, int trials, int secPerTrial) {
		this.nBackLevel = nBackLevel;
		this.trials = trials;
		this.secPerTrial = secPerTrial;
	}

	public int getNBackLevel() {
		return nBackLevel;
	}

	public void setNBackLevel(int nBackLevel) {
		this.nBackLevel = nBackLevel;
	}

	public int getTrials() {
		return trials;
	}

	public void setTrials(int trials) {
		this.trials = trials;
	}

	public int getSecPerTrial() {
		return secPerTrial;
	}

	public void setSecPerTrial(int secPerTrial) {
		this.secPerTrial = secPerTrial;
	}

}
