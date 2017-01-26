package justforfun.dualnback.core;

public class GameConfiguration {

	private final int nBack;
	private final int trials;
	private final int secPerTrial;

	public GameConfiguration(int nBack, int trials, int secPerTrial) {
		this.nBack = nBack;
		this.trials = trials;
		this.secPerTrial = secPerTrial;
	}

	public int getNBack() {
		return nBack;
	}

	public int getTrials() {
		return trials;
	}

	public int getSecPerTrial() {
		return secPerTrial;
	}

}
