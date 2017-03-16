package org.usfirst.frc.team1923.robot.utils;

public class DriveProfile {

    public enum ProfileCurve {
        LINEAR, QUAD, QUINT, SIN, EXP
    }

    private ProfileCurve curve;

    /**
     * Creates a default linear curve
     */
    public DriveProfile() {
        this.curve = ProfileCurve.LINEAR;
    }

    /**
     * Creates a profile with the specified curve
     * 
     * @param curve
     *            Input ProfileCurve
     */
    public DriveProfile(ProfileCurve curve) {
        this.curve = curve;
    }

    public void setProfileCurve(ProfileCurve curve) {
        this.curve = curve;
    }

    /**
     * Scales the input value from the joysticks
     * 
     * @param initial
     *            Input value from joysticks, assuming (-1, 1) domain
     * @return (-1, 1) output range
     */
    public double scale(double initial) {
        switch (this.curve) {
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
