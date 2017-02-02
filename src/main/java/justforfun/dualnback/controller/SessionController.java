package justforfun.dualnback.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.shape.Circle;
import justforfun.dualnback.core.Letter;
import justforfun.dualnback.core.Position;
import justforfun.dualnback.core.SessionScore;
import justforfun.dualnback.core.SessionStateListener;
import justforfun.dualnback.core.TrialState;
import justforfun.dualnback.model.VisualStimuli;
import justforfun.dualnback.utils.LetterSpeaker;

public class SessionController implements SessionStateListener, Initializable {

	private VisualStimuli visualStimuli;
	private LetterSpeaker speaker;

	private Circle activeVisualStimuliCircle;

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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Circle[][] circles = { 
				{ topLeftCircle, topCenterCircle, topRightCircle },
				{ midLeftCircle, midCenterCircle, midRightCircle },
				{ botLeftCircle, botCenterCircle, botRightCircle } };
		visualStimuli = new VisualStimuli(circles);
		speaker = new LetterSpeaker();
	}

	@Override
	public void onNextTrial(TrialState trialState) {
		Position position = trialState.getPosition();
		activateVisualStimuliAtPosition(position);

		Letter letter = trialState.getLetter();
		speaker.speak(letter);
	}

	@Override
	public void onFinish(SessionScore sessionScore) {
		System.out.println("score: " + sessionScore);
	}

	private void activateVisualStimuliAtPosition(Position position) {
		if (activeVisualStimuliCircle != null) {
			activeVisualStimuliCircle.setVisible(false);
		}

		activeVisualStimuliCircle = visualStimuli.getCircleAtPosition(position);
		activeVisualStimuliCircle.setVisible(true);
	}

}
