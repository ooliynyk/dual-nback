package dualnback.core;

public interface SessionState {
	
	boolean letterMatches();

	boolean positionMatches();
	
	void addStateListener(SessionStateListener listener);
	
}
