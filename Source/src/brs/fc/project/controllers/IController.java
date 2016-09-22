package brs.fc.project.controllers;

public class IController {
	private float mki, iControlValue;
	private float integral;
	private Float mfLastErr;

	public IController(float pki) {
		mki = pki;
		integral = 0;
	}

	public float getControlValueForErr(float pError, float pOffset) {
		float lfAdjErr = pError - pOffset;
		// boolean lbisPositiveAdjErr = lfAdjErr >= 0;
		if (mfLastErr != null) {
			if (lfAdjErr == 0 || (mfLastErr < 0 && lfAdjErr > 0) || (mfLastErr > 0 && lfAdjErr < 0)) {
				integral = 0;
			} else {
				mfLastErr = 0.0f;
			}
		}

		integral = integral + lfAdjErr;
		iControlValue = mki * integral;
		mfLastErr = lfAdjErr;
		return iControlValue;
	}

}
