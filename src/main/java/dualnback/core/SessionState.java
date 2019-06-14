package dualnback.core;

/**
 * Checks the current letter and position to match ones of the N-th back step
 */
public interface SessionState {

    /**
     * Checks if the current letter equals to a letter N-th steps back.
     *
     * @return {@code true} if the current letter equals to one on N-th step back, {code false} otherwise.
     */
    boolean isCurrentLetterMatchesNBack();

    /**
     * Checks if the current position equals to a position N-th steps back.
     *
     * @return {@code true} if the current position equals to one on N-th step back, {code false} otherwise.
     */
    boolean isCurrentPositionMatchesNBack();

    /**
     * Registers a listener of {@link SessionState} that is being notified each time the {@link TrialState trial state}  is changed or the session is finished.
     *
     * @param listener a listener to be registered
     */
    void addStateListener(SessionStateListener listener);

}
