package justforfun.dualnback.core;

public interface SessionStateListener {
	
	void onNextTrial(TrialState trialState);
	
	void onFinish(SessionScore sessionScore);
	
}
