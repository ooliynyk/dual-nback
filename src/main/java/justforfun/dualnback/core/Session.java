package justforfun.dualnback.core;

public interface Session {

	void start();

	void cancel();

	void pause();

	SessionScore get() throws InterruptedException;

}
