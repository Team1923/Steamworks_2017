package org.usfirst.frc.team1923.robot.commands;

import org.usfirst.frc.team1923.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SpeedDriveCommand extends Command {

	public SpeedDriveCommand() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.driveSubSystem);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.driveSubSystem.setSpeed(0, 0);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Robot.driveSubSystem.setSpeed(Robot.driveSubSystem.dprofile.scale(Robot.oi.driver.getLeftY()),
				Robot.driveSubSystem.dprofile.scale(Robot.oi.driver.getRightY()));
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
