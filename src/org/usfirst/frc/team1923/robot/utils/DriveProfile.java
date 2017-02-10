package org.usfirst.frc.team1923.robot.utils;

public class DriveProfile {

	public enum ProfileCurve {
		LINEAR, QUAD, QUINT, SIN, EXP
	};

	private ProfileCurve p;

	/**
	 * Creates a default linear curve
	 */
	public DriveProfile() {
		p = ProfileCurve.LINEAR;
	}

	/**
	 * Creates a profile with the specified curve
	 * 
	 * @param c
	 *            Input ProfileCurve
	 */
	public DriveProfile(ProfileCurve c) {
		p = c;
	}

	public void setProfile(ProfileCurve c) {
		p = c;
	}

	/**
	 * Scales the input value from the joysticks
	 * 
	 * @param initial
	 *            Input value from joysticks, assuming (-1,1) domain
	 * @return (-1,1) output range
	 */
	public double scale(double initial) {
		switch (p) {
		case LINEAR:
			return initial;
		case QUAD:
			return initial > 0 ? initial * initial : -initial * initial;
		case QUINT:
			return initial * initial * initial;
		case SIN:
			return Math.sin(initial * Math.PI / 2);
		case EXP:
			return initial > 0 ? Math.exp(initial * 0.69) - 1 : -(Math.exp(-initial * 0.69) - 1);
		default:
			return initial;
		}
	}

}
