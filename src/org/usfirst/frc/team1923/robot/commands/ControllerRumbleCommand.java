package org.usfirst.frc.team1923.robot.commands;

import org.usfirst.frc.team1923.robot.Robot;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ControllerRumbleCommand extends Command {

	public String hand;
	public double intensity;
	public double timeOut;

	public ControllerRumbleCommand(){
		this(0.9, 0.8);
	}
	
	public ControllerRumbleCommand(double intensity, double time) {
		super();
		hand = "both";
		this.intensity = intensity;
		timeOut = time;
		setTimeout(time);
	}

	/**
	 * 
	 * @param hand
	 * 			The side of the controller to rumble "left", "right", "both"
	 * @param intensity
	 * 			How strong the rumble is 0 <= intensity <= 1
	 */
	public ControllerRumbleCommand(String hand, double intensity) {
		super();
		this.hand = hand;
		this.intensity = intensity;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		//TODO does setRumble() give it a pulse or it never stops until we tell it to?
		if (hand.equalsIgnoreCase("left")) {
			Robot.oi.driver.setRumble(RumbleType.kLeftRumble, intensity);
		} else if (hand.equalsIgnoreCase("right")) {
			Robot.oi.driver.setRumble(RumbleType.kRightRumble, intensity);
		} else {
			Robot.oi.driver.setRumble(intensity);
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		//TODO does setRumble() give it a pulse or it never stops until we tell it to?
		return isTimedOut();
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.oi.driver.setRumble(0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
