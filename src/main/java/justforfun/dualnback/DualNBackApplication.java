package justforfun.dualnback;

import java.io.IOException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

	private static final Logger logger = LoggerFactory.getLogger(DualNBackApplication.class);

	private static final String SESSION_VIEW_PATH = "view/Session.fxml";
	private static final String MAIN_VIEW_PATH = "view/Main.fxml";
	private static final String APP_TITLE = "Dual N-Back";

	private Stage primaryStage;
	private AnchorPane rootLayout;

	private GameConfiguration gameConfiguration = new GameConfiguration(2, 13, 2);

	private DualNBackSession session;

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle(APP_TITLE);
		this.primaryStage = primaryStage;
		initMainScene();
	}

	public void startSession() {
		session = new DualNBackSession(gameConfiguration);
		Platform.runLater(() -> initSessionScene());
		session.start();
	}

	public void pauseSession() {
		session.pause();
	}

	public void unpauseSession() {
		session.unpause();
	}

	public void finishSession(SessionScore sessionScore) {
		if (session != null) {
			session.cancel();
			if (sessionScore != null)
				logger.info("Score: '{}'", sessionScore);
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
			loader.setLocation(getClasspathResource(SESSION_VIEW_PATH));

			rootLayout = loader.load();

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
			loader.setLocation(getClasspathResource(MAIN_VIEW_PATH));

			rootLayout = loader.load();

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

	private static URL getClasspathResource(String s) {
		return DualNBackApplication.class.getClassLoader().getResource(s);
	}

}
