package org.usfirst.frc.team1923.robot;

import org.usfirst.frc.team1923.robot.utils.DriveProfile.ProfileCurve;

public class RobotMap {

    /**
     * Driver profiles.
     */
    public static final ProfileCurve DRIVER_PROFILE = ProfileCurve.LINEAR;
    public static final ProfileCurve CHINMAY_PROFILE = ProfileCurve.LINEAR;
    public static final ProfileCurve ANISH_PROFILE = ProfileCurve.LINEAR;
    public static final ProfileCurve SURAJ_PROFILE = ProfileCurve.LINEAR;

    /**
     * Controller ports for driver and operator controllers.
     */
    public static final int DRIVER_CONTROLLER_PORT = 0;
    public static final int OP_CONTROLLER_PORT = 1;

    /**
     * List of ports for the talons on the left and right drivetrain, respectively.
     * The master talon will be the first element in the array.
     */
    public static final int[] LEFT_DRIVE_PORTS = { 8, 7, 6 };
    public static final int[] RIGHT_DRIVE_PORTS = { 1, 2, 3 };

    /**
     * Ports for the talons that control the climbing motors.
     */
    public static final int LEFT_CLIMB_PORT = 4;
    public static final int RIGHT_CLIMB_PORT = 9;

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
    public static final int FRONT_SONAR_PING_PORT = 8;
    public static final int FRONT_SONAR_ECHO_PORT = 9;

    /**
     * Settings and ports for GRIP and vision processing.
     */
    public static final String CAMERA_IP = "10.19.23.15";
    public static final String NEWTORK_TABLE_ADDRESS = "GRIP/table";
    public static final int IMG_WIDTH = 320;
    public static final int IMG_HEIGHT = 240;
    public static final double TURN_CONSTANT = 1000;
    public static final double MAX_WIDTH = 100;
    public static final double MAX_DIST = 12.5;

}
