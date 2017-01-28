package justforfun.dualnback.core;

public interface SessionState {
	
	boolean isCurrentLetterAsNBack();

	boolean isCurrentPositionAsNBack();
	
	void addStateListener(SessionStateListener listener);
	
}
