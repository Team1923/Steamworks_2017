package org.usfirst.frc.team1923.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {

	// OI port numbers
	public static final int DRIVER_CONTROLLER_PORT = 0;
	public static final int OP_CONTROLLER_PORT = 1;

	// Drive motor numbers
	public static final int[] LEFT_DRIVE_PORTS = { 2, 3, 4 }; // Master Talon
																// first
	public static final int[] RIGHT_DRIVE_PORTS = { 7, 8, 9 };

	// Talons that have the SRX Mag Enc installed (masters);
	public static final int LEFT_ENCODER_PORT = 7;
	public static final int RIGHT_ENCODER_PORT = 2;

	// Climb motor numbers
	public static final int LEFT_CLIMB_PORT = 0;
	public static final int RIGHT_CLIMB_PORT = 1;

	// Gear box shifting pistons ports on the PCM
	public static final int SHIFT_FORWARD_PORT = 0;
	public static final int SHIFT_BACKWARD_PORT = 1;

	// Mechanism actuator piston ports
	public static final int MECH_FORWARD_PORT = 0;
	public static final int MECH_BACKWARD_PORT = 1;

	// Drop down omniwheel piston ports
	public static final int OMNI_FORWARD_PORT = 0;
	public static final int OMNI_BACKWARD_PORT = 1;

	/*
	 * 2 controllers 6 drive talons 2 climb talons 1 shift piston 1 drop piston
	 * 1 gear piston TO BE DETERMINED: Ultrasonic? Button? Camera?
	 */

}
