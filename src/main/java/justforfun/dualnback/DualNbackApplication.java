package justforfun.dualnback;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import justforfun.dualnback.controller.SessionController;
import justforfun.dualnback.core.DualNBackSession;
import justforfun.dualnback.core.GameConfiguration;

public class DualNbackApplication extends Application {

	private Stage primaryStage;
	private AnchorPane rootLayout;

	private GameConfiguration gameConfiguration = new GameConfiguration(3, 5, 3);

	private DualNBackSession session;
	private SessionController sessionController;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		session = new DualNBackSession(gameConfiguration);
		initSessionScene();
		session.start();
	}

	private void initSessionScene() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(DualNbackApplication.class.getResource("view/Session.fxml"));

			rootLayout = (AnchorPane) loader.load();

			Scene scene = new Scene(rootLayout);

			sessionController = loader.getController();
			sessionController.initKeysHandling(scene);
			sessionController.initConfiguration(gameConfiguration, session);

			primaryStage.setTitle("Dual N-Back");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
