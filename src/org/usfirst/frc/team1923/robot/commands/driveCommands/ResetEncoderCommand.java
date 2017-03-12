package org.usfirst.frc.team1923.robot.commands.driveCommands;

import org.usfirst.frc.team1923.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class ResetEncoderCommand extends InstantCommand {

	public ResetEncoderCommand() {
		super();
	}

	// Called once when the command executes
	protected void initialize() {
		Robot.driveSubSys.resetPosition();
	}

}
