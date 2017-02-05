package justforfun.dualnback;

import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import justforfun.dualnback.controller.SessionController;
import justforfun.dualnback.core.DualNBackSession;
import justforfun.dualnback.core.GameConfiguration;
import justforfun.dualnback.core.SessionScore;

public class DualNBackApplication extends Application {

	private Stage primaryStage;
	private AnchorPane rootLayout;

	private GameConfiguration gameConfiguration = new GameConfiguration(2, 15, 3);

	private DualNBackSession session;

	public static void main(String[] args) {
		launch(args);
	}

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

	private void initSessionScene() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(DualNBackApplication.class.getResource("view/Session.fxml"));

			rootLayout = (AnchorPane) loader.load();

			Scene scene = new Scene(rootLayout);

			SessionController sessionController = loader.getController();
			sessionController.setApp(this);
			sessionController.initKeysHandling(scene);
			sessionController.initConfiguration(gameConfiguration, session);

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

			primaryStage.setScene(scene);
			primaryStage.show();

			scene.setOnKeyPressed((e) -> {
				switch (e.getCode()) {
				case SPACE:
					startSession();
					break;
				case ESCAPE:
					primaryStage.close();
					System.exit(0);
				default:
					break;
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
