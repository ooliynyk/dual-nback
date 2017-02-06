package justforfun.dualnback.controller;

import javafx.scene.Scene;
import justforfun.dualnback.DualNBackApplication;

public class MainController {

	private DualNBackApplication app;

	public void initKeysHandling(Scene scene) {
		scene.setOnKeyPressed((e) -> {
			switch (e.getCode()) {
			case SPACE:
				app.startSession();
				break;
			case ESCAPE:
				app.close();
			default:
				break;
			}
		});
	}

	public DualNBackApplication getApp() {
		return app;
	}

	public void setApp(DualNBackApplication app) {
		this.app = app;
	}

}
