package justforfun.dualnback;

import justforfun.dualnback.core.DualNBackSession;
import justforfun.dualnback.core.GameConfiguration;

public class DualNbackApplication {

	public static void main(String[] args) throws InterruptedException {
		DualNBackSession session = new DualNBackSession(new GameConfiguration(2, 32, 3));
		session.start();
		for (int i = 0; i < 5; i++) {
			Thread.sleep(3000);
			System.out.println(session.isCurrentLetterAsNBack());
		}
		System.out.println(session.get());
	}

}
