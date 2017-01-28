package justforfun.dualnback;

import java.util.Scanner;

import justforfun.dualnback.core.DualNBackSession;
import justforfun.dualnback.core.GameConfiguration;
import justforfun.dualnback.core.SessionScore;
import justforfun.dualnback.core.SessionStateListener;
import justforfun.dualnback.core.TrialState;

public class DualNbackApplication {

	public static void main(String[] args) throws InterruptedException {
		DualNBackSession session = new DualNBackSession(new GameConfiguration(2, 10, 3));
		session.addStateListener(new SessionStateListener() {

			@Override
			public void onNextTrial(TrialState trialState) {
				System.out.println("current state: " + trialState);
			}

			@Override
			public void onFinish(SessionScore sessionScore) {
				System.out.println("score: " + sessionScore);
			}

		});

		session.start();
		try (Scanner in = new Scanner(System.in)) {
			while (true) {
				String next = in.nextLine();
				if (next.equals("a")) {
					System.out.println(session.isCurrentPositionAsNBack());
				} else if (next.equals("l")) {
					System.out.println(session.isCurrentLetterAsNBack());
				}
			}
		}
	}

}
