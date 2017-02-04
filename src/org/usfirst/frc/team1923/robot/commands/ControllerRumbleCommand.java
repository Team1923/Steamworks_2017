package org.usfirst.frc.team1923.robot.commands;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 *
 */
public class ControllerRumbleCommand extends TimedCommand {

	private Joystick controller;
	private double power;

	/**
	 * Command to rumble the controller of the driver and operator for a certain
	 * amount of time. Default power of 50%
	 * 
	 * @param timeout
	 *            time to rumble
	 * @param controller
	 *            the controller to rumble (declared in OI)
	 */
	public ControllerRumbleCommand(double timeout, Joystick controller) {
		super(timeout);
		this.controller = controller;
		this.power = 0.5;
	}

	/**
	 *Command to rumble the controller of the driver and operator for a certain
	 * amount of time.
	 * 
	 * @param timeout time to rumble
	 * @param controller controller to rumble
	 * @param power 0-1 intensity of rumble
	 */
	public ControllerRumbleCommand(double timeout, Joystick controller, double power) {
		super(timeout);
		this.controller = controller;
		this.power = power;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		controller.setRumble(RumbleType.kRightRumble, power);
		controller.setRumble(RumbleType.kLeftRumble, power);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
	}

	// Called once after timeout
	protected void end() {
		controller.setRumble(RumbleType.kLeftRumble, 0);
		controller.setRumble(RumbleType.kRightRumble, 0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
