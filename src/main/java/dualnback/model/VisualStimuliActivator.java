package dualnback.model;

import javafx.scene.shape.Circle;
import dualnback.core.Position;

public class VisualStimuliActivator {

	private Circle[][] circles;

	private Circle activeVisualStimuliCircle;

	public VisualStimuliActivator(Circle[][] circles) {
		this.circles = circles;
	}

	public void activate(Position position) {
		if (activeVisualStimuliCircle != null) {
			activeVisualStimuliCircle.setVisible(false);
		}

		activeVisualStimuliCircle = getCircleAtPosition(position);
		activeVisualStimuliCircle.setVisible(true);
	}

	private Circle getCircleAtPosition(Position position) {
		Circle circle;
		switch (position) {
		case BOTTOM_CENTER:
			circle = circles[2][1];
			break;
		case BOTTOM_LEFT:
			circle = circles[2][0];
			break;
		case BOTTOM_RIGHT:
			circle = circles[2][2];
			break;
		case MIDDLE_CENTER:
			circle = circles[1][1];
			break;
		case MIDDLE_LEFT:
			circle = circles[1][0];
			break;
		case MIDDLE_RIGHT:
			circle = circles[1][2];
			break;
		case TOP_CENTER:
			circle = circles[0][1];
			break;
		case TOP_LEFT:
			circle = circles[0][0];
			break;
		case TOP_RIGHT:
			circle = circles[0][2];
			break;
		default:
			throw new RuntimeException("Illegal position " + position);
		}
		return circle;
	}

}
