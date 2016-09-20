package brs.fc.project.linefollower;

import lejos.nxt.Button;

/**
 * Line follower initialization.
 */
public class LineFollower {

	/**
	 * Main method
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		
		LineFollowerRobot lRobot = new LineFollowerRobot();
		while(!Button.ENTER.isDown())	
		{
			lRobot.calibrate();
		}
		
		// wait till right button is pressed
		Button.RIGHT.waitForPressAndRelease();
		lRobot.start();
	}

}
