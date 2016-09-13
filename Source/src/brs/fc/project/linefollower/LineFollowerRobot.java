package brs.fc.project.linefollower;

import brs.fc.project.controllers.PController;
import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.util.Delay;

public class LineFollowerRobot {
	private static float INITIAL_SPEED, MAX_SPEED, MIN_SPEED;
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
		INITIAL_SPEED = 50;

	}

	public void calibrate() {

		mSensorOffset = mLeftLS.getLightValue() - mRightLS.getLightValue();

	}

	public void start() {

		PController lController = new PController(3);
		mRightMotor.forward();
		mLeftMotor.forward();

		while (!Button.ENTER.isDown()) {
			int liError = getError();
			float lfControlValue = lController.getControlValueForErr(liError, mSensorOffset);

			if (liError == 0) {

				settingSpeed(mRightMotor, INITIAL_SPEED);
				settingSpeed(mLeftMotor, INITIAL_SPEED);

			} else if (liError > 0) {

				settingSpeed(mRightMotor, INITIAL_SPEED * (1 - lfControlValue));
				settingSpeed(mLeftMotor, INITIAL_SPEED * (1 + lfControlValue));

			} else {

				settingSpeed(mRightMotor, INITIAL_SPEED * (1 + lfControlValue));
				settingSpeed(mLeftMotor, INITIAL_SPEED * (1 - lfControlValue));

			}
			Delay.msDelay(100);
		}

	}

	private int getError() {

		int liLeftLSValue = mLeftLS.getLightValue();
		int liRightLSValue = mRightLS.getLightValue();
		return (liLeftLSValue - liRightLSValue);

	}

	private void settingSpeed(NXTRegulatedMotor pMotor, float pSpeed){
		if (isValidSpeed(pMotor, pSpeed) == true) {
			pMotor.setSpeed(pSpeed);
		}
		else {
			if (pSpeed > MAX_SPEED){
				pMotor.setSpeed(MAX_SPEED);
			}
			else {
				pMotor.setSpeed(MIN_SPEED);
			}
			
		}
	}

	private boolean isValidSpeed(NXTRegulatedMotor pMotor, float pSpeed) {
		if (pSpeed > MAX_SPEED || pSpeed < MIN_SPEED) {
			return (false);
		} else {
			return (true);
		}

	}

	public static void main(String[] args) {

	}

}
