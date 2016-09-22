package brs.fc.project.controllers;

/**
 * Implementation of Proportional element of controller.
 */
public class PController {
	/**
	 * Proportional constant
	 */
	private float mKp;
	
	/**
	 * Computed control value 
	 */
	private float mfControlValue;

	/**
	 * Parameterized constructor
	 * @param pKp value of proportional constant
	 */
	public PController(float pKp) {
		mKp = pKp;
	}

	/**
	 * Returns the final control value and updates respective member
	 * @param pError the input error
	 * @param pOffset offset to adjust for
	 * @return computed control value
	 */
	public float getControlValueForErr(float pError, float pOffset) {
		mfControlValue = mKp * (pError - pOffset);
		return mfControlValue;
	}

}
