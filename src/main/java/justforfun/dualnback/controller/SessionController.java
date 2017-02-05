package justforfun.dualnback.controller;

import java.net.URL;
import java.util.ResourceBundle;

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
import justforfun.dualnback.core.GameConfiguration;
import justforfun.dualnback.core.Letter;
import justforfun.dualnback.core.Position;
import justforfun.dualnback.core.SessionScore;
import justforfun.dualnback.core.SessionState;
import justforfun.dualnback.core.SessionStateListener;
import justforfun.dualnback.core.TrialState;
import justforfun.dualnback.model.VisualStimuli;
import justforfun.dualnback.utils.LetterSpeaker;

public class SessionController implements SessionStateListener, Initializable {

	private static final String N_BACK_LEVEL_LABEL_PATTERN = "Dual %d-Back";

	private VisualStimuli visualStimuli;
	private LetterSpeaker speaker;
	private SessionState sessionState;

	private Circle activeVisualStimuliCircle;
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
		if (sessionState.isCurrentLetterAsNBack()) {
			matchButtonStyle(positionMatchButton);
		} else {
			unmatchByttonStyle(positionMatchButton);
		}
	}

	@FXML
	private void handleAudioMatchClick() {
		if (sessionState.isCurrentPositionAsNBack()) {
			matchButtonStyle(audioMatchButton);
		} else {
			unmatchByttonStyle(audioMatchButton);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Circle[][] circles = { { topLeftCircle, topCenterCircle, topRightCircle },
				{ midLeftCircle, midCenterCircle, midRightCircle },
				{ botLeftCircle, botCenterCircle, botRightCircle } };
		visualStimuli = new VisualStimuli(circles);
		speaker = new LetterSpeaker();
	}

	@Override
	public void onNextTrial(TrialState trialState) {
		clearButtonStyle(positionMatchButton);
		clearButtonStyle(audioMatchButton);

		Position position = trialState.getPosition();
		activateVisualStimuliAtPosition(position);

		Letter letter = trialState.getLetter();
		speaker.speak(letter);

		progressIndicator.setProgress(trialsCounter / (double) trialsTotal);
		trialsCounter += 1;
	}

	@Override
	public void onFinish(SessionScore sessionScore) {
		clearButtonStyle(positionMatchButton);
		clearButtonStyle(audioMatchButton);
		System.out.println("score: " + sessionScore);
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
					break;
				default:
					break;
				}
			}

		});
	}

	public void initConfiguration(GameConfiguration gameConfiguration, SessionState sessionState) {
		trialsTotal = gameConfiguration.getTrials();

		int nbackLevel = gameConfiguration.getNBackLevel();
		nbackLevelLabel.setText(String.format(N_BACK_LEVEL_LABEL_PATTERN, nbackLevel));

		sessionState.addStateListener(this);
		this.sessionState = sessionState;
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

	private void unmatchByttonStyle(Button button) {
		button.setTextFill(Paint.valueOf("red"));
	}

	private void clearButtonStyle(Button button) {
		button.setTextFill(Paint.valueOf("black"));
	}

}
