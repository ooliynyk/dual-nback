package dualnback.core;

import dualnback.utils.TrialStateSequence;

public class TrialStateChecker implements SessionStateListener {

	private TrialStateSequence stateSequence;

	private boolean letterAsNBackTested;
	private boolean positionAsNBackTested;

	public TrialStateChecker(TrialStateSequence stateSequence) {
		this.stateSequence = stateSequence;
	}

	@Override
	public void onNextTrial(TrialState trialState) {
		letterAsNBackTested = false;
		positionAsNBackTested = false;
	}

	@Override
	public void onFinish(SessionScore sessionScore) {
		// TODO Auto-generated method stub
	}
	
	public boolean isLetterMatchingMissed() {
		return !letterAsNBackTested && checkLetterMatching();
	}
	
	public boolean isPositionMatchingMissed() {
		return !positionAsNBackTested && checkPositionMatching();
	}

	public boolean checkLetterMatching() {
		letterAsNBackTested = true;
		Letter nBackLatter = stateSequence.getNBackState().getLetter();
		Letter currentLatter = stateSequence.getCurrentState().getLetter();

		return currentLatter.equals(nBackLatter);
	}

	public boolean checkPositionMatching() {
		positionAsNBackTested = true;
		Position nBackPosition = stateSequence.getNBackState().getPosition();
		Position currentPosition = stateSequence.getCurrentState().getPosition();

		return currentPosition.equals(nBackPosition);
	}

}
