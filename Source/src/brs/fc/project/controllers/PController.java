package brs.fc.project.controllers;

public class PController {
	private float mKp;
	private float mfControlValue;

	public PController(float pKp) {
		mKp = pKp;
	}

	public float getControlValueForErr(float pError, float pOffset) {
		mfControlValue = mKp * (pError - pOffset);
		return mfControlValue;
	}

}
