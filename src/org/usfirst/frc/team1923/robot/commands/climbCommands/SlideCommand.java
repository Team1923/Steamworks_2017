package org.usfirst.frc.team1923.robot.commands.climbCommands;

import org.usfirst.frc.team1923.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 * This command controls the sliding of the mechanism
 */
public class SlideCommand extends InstantCommand {

	private boolean forward; // True if shifting forward

	public SlideCommand() {
		super();
	}

	/**
	 * Creates a command to slide the mechanism frame
	 * 
	 * @param forward
	 *            True if the frame will extend away from the robot body
	 */
	public SlideCommand(boolean forward) {
		this.forward = forward;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		System.out.println("SLIDE FORWARD!");	//Test print
		if (forward) {
			Robot.climbSubSys.slideForward();
		} else {
			Robot.climbSubSys.slideBackward();
		}
	}
}
