package justforfun.dualnback;

import java.io.IOException;
import java.util.Scanner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import justforfun.dualnback.core.DualNBackSession;
import justforfun.dualnback.core.GameConfiguration;
import justforfun.dualnback.core.SessionScore;
import justforfun.dualnback.core.SessionStateListener;
import justforfun.dualnback.core.TrialState;

public class DualNbackApplication extends Application {

	private Stage primaryStage;
	private AnchorPane rootLayout;

	public static void test(String[] args) throws InterruptedException {
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
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Double N-Back");
		initSessionScene();
	}

	public void initSessionScene() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(DualNbackApplication.class.getResource("ui/Session.fxml"));

			rootLayout = (AnchorPane) loader.load();

			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
