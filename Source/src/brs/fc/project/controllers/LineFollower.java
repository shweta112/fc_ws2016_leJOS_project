package brs.fc.project.controllers;

public class LineFollower {

	public static void main(String[] args) {
		LineFollowerRobot lRobot = new LineFollowerRobot();
		lRobot.caliberate();
		lRobot.start();
	}

}
