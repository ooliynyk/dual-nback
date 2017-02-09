package justforfun.dualnback.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import justforfun.dualnback.DualNBackApplication;
import justforfun.dualnback.core.GameConfiguration;
import justforfun.dualnback.core.Letter;
import justforfun.dualnback.core.Position;
import justforfun.dualnback.core.SessionScore;
import justforfun.dualnback.core.SessionState;
import justforfun.dualnback.core.SessionStateListener;
import justforfun.dualnback.core.TrialState;
import justforfun.dualnback.core.TrialStateChecker;
import justforfun.dualnback.model.VisualStimuli;
import justforfun.dualnback.utils.LetterSpeaker;

public class SessionController implements SessionStateListener, Initializable {

	private static final String N_BACK_LEVEL_LABEL_PATTERN = "Dual %d-Back";

	private Timer scheduler;

	private VisualStimuli visualStimuli;
	private Circle activeVisualStimuliCircle;

	private DualNBackApplication app;

	private LetterSpeaker speaker;

	private SessionState sessionState;
	private TrialStateChecker trialStateChecker;

	private long trialDuratoinInMs;
	private int trialsTotal;
	private int trialsCounter = 1;

	@FXML
	private Circle topLeftCircle;
	@FXML
	private Circle topCenterCircle;
	@FXML
	private Circle topRightCircle;
	@FXML
	private Circle midLeftCircle;
	@FXML
	private Circle midCenterCircle;
	@FXML
	private Circle midRightCircle;
	@FXML
	private Circle botLeftCircle;
	@FXML
	private Circle botCenterCircle;
	@FXML
	private Circle botRightCircle;
	@FXML
	private Label nbackLevelLabel;
	@FXML
	private Label remainingNumberLabel;
	@FXML
	private ProgressIndicator progressIndicator;
	@FXML
	private Button positionMatchButton;
	@FXML
	private Button audioMatchButton;

	@FXML
	private void handlePositionMatchClick() {
		if (sessionState.isCurrentPositionAsNBack()) {
			matchButtonStyle(positionMatchButton);
		} else {
			unmatchButtonStyle(positionMatchButton);
		}
	}

	@FXML
	private void handleAudioMatchClick() {
		if (sessionState.isCurrentLetterAsNBack()) {
			matchButtonStyle(audioMatchButton);
		} else {
			unmatchButtonStyle(audioMatchButton);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Circle[][] circles = { { topLeftCircle, topCenterCircle, topRightCircle },
				{ midLeftCircle, midCenterCircle, midRightCircle },
				{ botLeftCircle, botCenterCircle, botRightCircle } };
		scheduler = new Timer();
		visualStimuli = new VisualStimuli(circles);
		speaker = new LetterSpeaker();
	}

	@Override
	public void onNextTrial(TrialState trialState) {
		clear();
		activateVisualStimuliAtPosition(trialState.getPosition());
		speakLetter(trialState.getLetter());
		updateProgressIndicator();
		scheduleShowMatchingMissed();
	}

	@Override
	public void onFinish(SessionScore sessionScore) {
		app.finishSession(sessionScore);
	}

	public void initKeysHandling(Scene scene) {
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				KeyCode keyCode = event.getCode();
				switch (keyCode) {
				case A:
					handlePositionMatchClick();
					break;
				case L:
					handleAudioMatchClick();
					break;
				case ESCAPE:
					app.finishSession(null);
					break;
				default:
					break;
				}
			}

		});
	}

	public void initConfiguration(GameConfiguration gameConfiguration, TrialStateChecker trialStateChecker,
			SessionState sessionState) {
		trialDuratoinInMs = gameConfiguration.getSecPerTrial() * 1000l;
		trialsTotal = gameConfiguration.getTrials();
		this.trialStateChecker = trialStateChecker;

		int nbackLevel = gameConfiguration.getNBackLevel();
		nbackLevelLabel.setText(String.format(N_BACK_LEVEL_LABEL_PATTERN, nbackLevel));

		sessionState.addStateListener(this);
		this.sessionState = sessionState;
	}

	public DualNBackApplication getApp() {
		return app;
	}

	public void setApp(DualNBackApplication app) {
		this.app = app;
	}

	private void activateVisualStimuliAtPosition(Position position) {
		if (activeVisualStimuliCircle != null) {
			activeVisualStimuliCircle.setVisible(false);
		}

		activeVisualStimuliCircle = visualStimuli.getCircleAtPosition(position);
		activeVisualStimuliCircle.setVisible(true);
	}

	private void matchButtonStyle(Button button) {
		button.setTextFill(Paint.valueOf("green"));
	}

	private void unmatchButtonStyle(Button button) {
		button.setTextFill(Paint.valueOf("red"));
	}

	private void clearButtonStyle(Button button) {
		button.setTextFill(Paint.valueOf("black"));
	}

	private void updateProgressIndicator() {
		progressIndicator.setProgress(trialsCounter++ / (double) trialsTotal);
	}

	private void speakLetter(Letter letter) {
		speaker.speak(letter);
	}

	private void clear() {
		clearButtonStyle(positionMatchButton);
		clearButtonStyle(audioMatchButton);
	}

	private void scheduleShowMatchingMissed() {
		scheduler.schedule(new ShowMatchingMissedTask(), trialDuratoinInMs - 500l);
	}

	private class ShowMatchingMissedTask extends TimerTask {

		@Override
		public void run() {
			if (trialStateChecker.isLetterMatchingMissed()) {
				unmatchButtonStyle(audioMatchButton);
			}

			if (trialStateChecker.isPositionMatchingMissed()) {
				unmatchButtonStyle(positionMatchButton);
			}
		}

	}

}
