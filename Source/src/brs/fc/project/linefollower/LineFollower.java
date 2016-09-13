package brs.fc.project.linefollower;

public class LineFollower {

	public static void main(String[] args) {
		LineFollowerRobot lRobot = new LineFollowerRobot();
		lRobot.calibrate();
		lRobot.start();
	}

}
