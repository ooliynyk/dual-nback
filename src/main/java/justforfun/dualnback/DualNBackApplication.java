package justforfun.dualnback;

import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import justforfun.dualnback.controller.MainController;
import justforfun.dualnback.controller.SessionController;
import justforfun.dualnback.core.DualNBackSession;
import justforfun.dualnback.core.GameConfiguration;
import justforfun.dualnback.core.SessionScore;

public class DualNBackApplication extends Application {

	private Stage primaryStage;
	private AnchorPane rootLayout;

	private GameConfiguration gameConfiguration = new GameConfiguration(2, 13, 2);

	private DualNBackSession session;

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Dual N-Back");
		this.primaryStage = primaryStage;
		initMainScene();
	}

	public void startSession() {
		session = new DualNBackSession(gameConfiguration);
		Platform.runLater(() -> initSessionScene());
		session.start();
	}

	public void finishSession(SessionScore sessionScore) {
		if (session != null) {
			session.cancel();
			if (sessionScore != null)
				System.out.println("score: " + sessionScore);
			Platform.runLater(() -> initMainScene());
		}
	}

	public void close() {
		primaryStage.close();
		System.exit(0);
	}

	public static void main(String[] args) {
		launch(args);
	}

	private void initSessionScene() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(DualNBackApplication.class.getResource("view/Session.fxml"));

			rootLayout = (AnchorPane) loader.load();

			Scene scene = new Scene(rootLayout);

			SessionController sessionController = loader.getController();
			sessionController.setApp(this);
			sessionController.initKeysHandling(scene);
			sessionController.initConfiguration(gameConfiguration, session.getStateChecker(), session);

			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void initMainScene() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(DualNBackApplication.class.getResource("view/Main.fxml"));

			rootLayout = (AnchorPane) loader.load();

			Scene scene = new Scene(rootLayout);

			MainController mainController = loader.getController();
			mainController.setApp(this);
			mainController.initKeysHandling(scene);

			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
