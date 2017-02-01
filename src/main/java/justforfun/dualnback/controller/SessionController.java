package justforfun.dualnback.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.shape.Circle;
import justforfun.dualnback.core.Position;
import justforfun.dualnback.core.SessionScore;
import justforfun.dualnback.core.SessionStateListener;
import justforfun.dualnback.core.TrialState;
import justforfun.dualnback.model.VisualStimuli;

public class SessionController implements SessionStateListener, Initializable {

	private VisualStimuli visualStimuli;

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

	public Circle getTopLeftCircle() {
		return topLeftCircle;
	}

	public Circle getTopCenterCircle() {
		return topCenterCircle;
	}

	public Circle getTopRightCircle() {
		return topRightCircle;
	}

	public Circle getMidLeftCircle() {
		return midLeftCircle;
	}

	public Circle getMidCenterCircle() {
		return midCenterCircle;
	}

	public Circle getMidRightCircle() {
		return midRightCircle;
	}

	public Circle getBotLeftCircle() {
		return botLeftCircle;
	}

	public Circle getBotCenterCircle() {
		return botCenterCircle;
	}

	public Circle getBotRightCircle() {
		return botRightCircle;
	}

	public void setTopLeftCircle(Circle topLeftCircle) {
		this.topLeftCircle = topLeftCircle;
	}

	public void setTopCenterCircle(Circle topCenterCircle) {
		this.topCenterCircle = topCenterCircle;
	}

	public void setTopRightCircle(Circle topRightCircle) {
		this.topRightCircle = topRightCircle;
	}

	public void setMidLeftCircle(Circle midLeftCircle) {
		this.midLeftCircle = midLeftCircle;
	}

	public void setMidCenterCircle(Circle midCenterCircle) {
		this.midCenterCircle = midCenterCircle;
	}

	public void setMidRightCircle(Circle midRightCircle) {
		this.midRightCircle = midRightCircle;
	}

	public void setBotLeftCircle(Circle botLeftCircle) {
		this.botLeftCircle = botLeftCircle;
	}

	public void setBotCenterCircle(Circle botCenterCircle) {
		this.botCenterCircle = botCenterCircle;
	}

	public void setBotRightCircle(Circle botRightCircle) {
		this.botRightCircle = botRightCircle;
	}

	@Override
	public String toString() {
		return "SessionController [topLeftCircle=" + topLeftCircle + ", topCenterCircle=" + topCenterCircle
				+ ", topRightCircle=" + topRightCircle + ", midLeftCircle=" + midLeftCircle + ", midCenterCircle="
				+ midCenterCircle + ", midRightCircle=" + midRightCircle + ", botLeftCircle=" + botLeftCircle
				+ ", botCenterCircle=" + botCenterCircle + ", botRightCircle=" + botRightCircle + "]";
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Circle[][] circles = { { topLeftCircle, topCenterCircle, topRightCircle },
				{ midLeftCircle, midCenterCircle, midRightCircle },
				{ botLeftCircle, botCenterCircle, botRightCircle } };
		visualStimuli = new VisualStimuli(circles);
	}

	@Override
	public void onNextTrial(TrialState trialState) {
		Position position = trialState.getPosition();
		visualStimuli.activateAt(position);
	}

	@Override
	public void onFinish(SessionScore sessionScore) {
		System.out.println("score: " + sessionScore);
	}

}
