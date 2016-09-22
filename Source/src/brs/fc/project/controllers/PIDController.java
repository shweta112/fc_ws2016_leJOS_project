package brs.fc.project.controllers;

public class PIDController {
	private PController pController;
	private IController iController;
	private DController dController;

	public PIDController(float pkp, float pki, float pkd) {

		pController = new PController(pkp);
		iController = new IController(pki);
		dController = new DController(pkd);

	}

	public float getControlValueForErr(float pError, float pOffset) {
		float pControlValue = pController.getControlValueForErr(pError, pOffset);
		float iControlValue = iController.getControlValueForErr(pError, pOffset);
		float dControlValue = dController.getControlValueForErr(pError, pOffset);
		float fControlValue = pControlValue + iControlValue + dControlValue;
		return fControlValue;
	}

}
