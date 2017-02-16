package org.usfirst.frc.team1923.robot.commands;

import org.usfirst.frc.team1923.robot.Robot;
import org.usfirst.frc.team1923.robot.utils.DriveProfile.ProfileCurve;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command directly feeds the raw values on the joysticks to the motor
 * without PID
 */
public class RawDriveCommand extends Command {

	public RawDriveCommand() {
		requires(Robot.driveSubSystem);
	}

	public RawDriveCommand(ProfileCurve p) {
		requires(Robot.driveSubSystem);
		Robot.driveSubSystem.dprofile.setProfile(p);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.driveSubSystem.disable();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Robot.driveSubSystem.set(Robot.driveSubSystem.dprofile.scale(Robot.oi.driver.getLeftY()),
				Robot.driveSubSystem.dprofile.scale(Robot.oi.driver.getRightY()));
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.driveSubSystem.set(0, 0); // Stops the robot
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
