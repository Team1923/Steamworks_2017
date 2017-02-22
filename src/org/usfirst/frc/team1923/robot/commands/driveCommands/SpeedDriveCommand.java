package org.usfirst.frc.team1923.robot.commands.driveCommands;

import org.usfirst.frc.team1923.robot.Robot;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SpeedDriveCommand extends Command {

	public static final int MAX_RPM = 1000; // aka 100% power

	public SpeedDriveCommand() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.driveSubSys);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Robot.driveSubSys.drive(MAX_RPM * Robot.driveSubSys.dprofile.scale(Robot.oi.driver.getLeftY()),
				MAX_RPM * Robot.driveSubSys.dprofile.scale(Robot.oi.driver.getRightY()), TalonControlMode.Speed);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.driveSubSys.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
