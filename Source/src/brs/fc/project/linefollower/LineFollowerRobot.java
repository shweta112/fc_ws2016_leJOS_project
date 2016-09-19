package brs.fc.project.linefollower;

import brs.fc.project.controllers.PController;
import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.util.Delay;

public class LineFollowerRobot {
	private static float INITIAL_SPEED = 150;
	private LightSensor mLeftLS;
	private LightSensor mRightLS;
	private NXTRegulatedMotor mRightMotor;
	private NXTRegulatedMotor mLeftMotor;
	private float mSensorOffset;

	public LineFollowerRobot() {

		mLeftLS = new LightSensor(SensorPort.S4);
		mRightLS = new LightSensor(SensorPort.S1);
		mRightMotor = Motor.A;
		mLeftMotor = Motor.C;
		mRightMotor.setSpeed(INITIAL_SPEED);
		mLeftMotor.setSpeed(INITIAL_SPEED);
		Delay.msDelay(100);
	}

	public void calibrate() {

		mSensorOffset = mLeftLS.getLightValue() - mRightLS.getLightValue();
		LCD.drawString("Calibrated. Ready to start.", 0, 0);
		LCD.drawString("Offset: " + mSensorOffset, 0, 1);
	}

	public void start() {

		PController lController = new PController((float)7);
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

			Delay.msDelay(50);
		}

	}

	private int getError() {

		int liLeftLSValue = mLeftLS.getLightValue();
		int liRightLSValue = mRightLS.getLightValue();
		return (liLeftLSValue - liRightLSValue);

	}

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

	private boolean isValidSpeed(NXTRegulatedMotor pMotor, float pSpeed) {
		if (pSpeed > pMotor.getMaxSpeed() || pSpeed < 0) {
			return false;
		}
		return true;
	}

}
