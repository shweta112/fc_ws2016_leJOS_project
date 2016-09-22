package brs.fc.project.linefollower;

import brs.fc.project.controllers.PController;
import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.util.Delay;

/**
 * Line follower robot implementation.
 */
public class LineFollowerRobot {
	/**
	 * Initial speed.
	 */
	private static float INITIAL_SPEED = 250;
	
	/**
	 * Left light sensor.
	 */
	private LightSensor mLeftLS;
	
	/**
	 * Right light sensor.
	 */
	private LightSensor mRightLS;
	
	/**
	 * Right motor.
	 */
	private NXTRegulatedMotor mRightMotor;
	
	/**
	 * Left motor.
	 */
	private NXTRegulatedMotor mLeftMotor;
	
	/**
	 * Offset to adjust difference between left and right sensor.
	 */
	private float mSensorOffset;

	/**
	 * Constructor
	 */
	public LineFollowerRobot() {

		mLeftLS = new LightSensor(SensorPort.S4);
		mRightLS = new LightSensor(SensorPort.S1);
		mRightMotor = Motor.A;
		mLeftMotor = Motor.C;
		mRightMotor.setSpeed(INITIAL_SPEED);
		mLeftMotor.setSpeed(INITIAL_SPEED);
		Delay.msDelay(100);
	}

	/**
	 * Calibrates the robot to adjust for the difference between the two sensors.
	 */
	public void calibrate() {

		mSensorOffset = mLeftLS.getLightValue() - mRightLS.getLightValue();
		LCD.drawString("Calibrated. Ready to start.", 0, 0);
		LCD.drawString("Offset: " + mSensorOffset, 0, 1);
	}

	/**
	 * Starts the line following process.
	 */
	public void start() {

		PController lController = new PController((float) 12);
		mRightMotor.forward();
		mLeftMotor.forward();

		while (!Button.ENTER.isDown()) {
			int liError = getError();
			float lfControlValue = lController.getControlValueForErr(liError, mSensorOffset);
			LCD.drawString("Control value: " + lfControlValue, 0, 2);
			float lfRightSpeed = INITIAL_SPEED - lfControlValue;
			float lfLeftSpeed = INITIAL_SPEED + lfControlValue;
			LCD.drawString("Right speed: " + lfRightSpeed, 0, 3);
			LCD.drawString("Left speed: " + lfLeftSpeed, 0, 4);

			setSpeed(mRightMotor, lfRightSpeed);
			setSpeed(mLeftMotor, lfLeftSpeed);

			Delay.msDelay(30);
		}

	}

	/**
	 * Gets the current error. It is the difference between the intensities detected by the left and right light sensors.
	 * @return error
	 */
	private int getError() {

		int liLeftLSValue = mLeftLS.getLightValue();
		int liRightLSValue = mRightLS.getLightValue();
		return (liLeftLSValue - liRightLSValue);

	}

	/**
	 * Sets the speed of the motor
	 * @param pMotor the motor to set speed for
	 * @param pSpeed speed to set
	 */
	private void setSpeed(NXTRegulatedMotor pMotor, float pSpeed) {
		if (isValidSpeed(pMotor, pSpeed) == true) {
			pMotor.setSpeed(pSpeed);
			return;
		}
		if (pSpeed > pMotor.getMaxSpeed()) {
			pMotor.setSpeed(pMotor.getMaxSpeed());
			return;
		}
		pMotor.setSpeed(0);
	}

	/**
	 * Checks if the speed is valid. 
	 * @param pMotor motor to set speed
	 * @param pSpeed speed to be set
	 * @return false if the speed is greater than maximum of motor or non-positive
	 */
	private boolean isValidSpeed(NXTRegulatedMotor pMotor, float pSpeed) {
		if (pSpeed > pMotor.getMaxSpeed() || pSpeed < 0) {
			return false;
		}
		return true;
	}

}
