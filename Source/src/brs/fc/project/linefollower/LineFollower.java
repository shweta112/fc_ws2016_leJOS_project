package brs.fc.project.linefollower;

import lejos.nxt.Button;

public class LineFollower {

	public static void main(String[] args) {
		LineFollowerRobot lRobot = new LineFollowerRobot();
		lRobot.calibrate();
		// wait till right button is pressed
		Button.RIGHT.waitForPressAndRelease();
		lRobot.start();
	}

}
