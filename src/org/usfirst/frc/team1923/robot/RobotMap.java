package org.usfirst.frc.team1923.robot;

import org.usfirst.frc.team1923.robot.utils.DriveProfile.ProfileCurve;

public class RobotMap {

    /**
     * Debug options
     */
    public static final boolean DEBUG = true;

    /**
     * Driver profiles.
     */
    public static final ProfileCurve DRIVER_PROFILE = ProfileCurve.LINEAR;

    /**
     * Controller ports for driver and operator controllers.
     */
    public static final int DRIVER_CONTROLLER_PORT = 0;
    public static final int OP_CONTROLLER_PORT = 1;

    /**
     * List of ports for the talons on the left and right drivetrain,
     * respectively. The master talon will be the first element in the array.
     */
    public static final int[] LEFT_DRIVE_PORTS = { 8, 7, 6 };
    public static final int[] RIGHT_DRIVE_PORTS = { 1, 2, 3 };

    /**
     * Ports for the talons that control the climbing motors.
     */
    public static final int LEFT_CLIMB_PORT = 9;
    public static final int RIGHT_CLIMB_PORT = 0;

    /**
     * Ports for the shooter talon.
     */
    public static final int SHOOTER_PORT = 4;
    public static final int INDEXER_PORT = 5;

    /**
     * Climber slider port numbers
     */
    public static final int SLIDE_FORWARD_PORT = 6;
    public static final int SLIDE_BACKWARD_PORT = 7;

    /**
     * Gear box shifting pistons ports on the PCM.
     */
    public static final int SHIFT_FORWARD_PORT = 2;
    public static final int SHIFT_BACKWARD_PORT = 3;

    /**
     * Mechanism actuator piston ports.
     */
    public static final int MECH_FORWARD_PORT = 4;
    public static final int MECH_BACKWARD_PORT = 5;

    /**
     * Drop down omniwheel piston ports.
     */
    public static final int OMNI_FORWARD_PORT = 0;
    public static final int OMNI_BACKWARD_PORT = 1;

    /**
     * PCM Module number.
     */
    public static final int PCM_MODULE_NUM = 12;

    /**
     * Port for the talon that the PigeonIMU is connected to.
     */
    public static final int IMU_PORT = 9;

    /**
     * Ultrasonic Sensor DIO ports.
     */
    public static final int FRONT_SONAR_PING_PORT = 0;
    public static final int FRONT_SONAR_ECHO_PORT = 1;

    /**
     * Settings and ports for GRIP and vision processing.
     */
    public static final String GEAR_CAMERA_IP = "10.19.23.15";
    public static final String SHOOTER_CAMERA_IP = "10.19.23.16";
    public static final int IMG_WIDTH = 320;
    public static final int IMG_HEIGHT = 240;
    public static final double TURN_CONSTANT = 1000;
    public static final double MAX_WIDTH = 100;
    public static final double PEG_DIST = 12.5;
    public static final double FEEDER_DIST = 6;

    /*
     * Shooter Constants
     */
    // Setpoint for speed of shooter from center gear auton
    public static final double SHOOTER_CENTER_SETPOINT_PEG = 2000;
    public static final double SHOOTER_CENTER_SETPOINT_BASE = 2000;

}
