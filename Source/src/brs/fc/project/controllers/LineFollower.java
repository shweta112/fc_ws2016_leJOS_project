package brs.fc.project.controllers;

import brs.fc.project.linefollower.LineFollowerRobot;

public class LineFollower {

	public static void main(String[] args) {
		LineFollowerRobot lRobot = new LineFollowerRobot();
		lRobot.calibrate();
		lRobot.start();
	}

}
