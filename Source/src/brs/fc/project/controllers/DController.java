package brs.fc.project.controllers;

public class DController {
	private float mkd, dControlValue, derivative;
	private float lastError = 0;

	public DController(float pkd) {

		mkd = pkd;

	}

	public float getControlValueForErr(float pError, float pOffset) {
		derivative = (pError - pOffset) - lastError;
		dControlValue = mkd * derivative;
		lastError = pError - pOffset;
		return dControlValue;
	}

}
