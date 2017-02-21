package org.usfirst.frc.team1923.robot.commands.driveCommands;

import org.usfirst.frc.team1923.robot.Robot;

import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveDistanceCommand extends Command {

	private double left, right;

	/**
	 * This command drives the robot a set distance with the left and right
	 * encoder targets
	 * 
	 * @param left
	 *            Distance traveled by left wheel in inches
	 * @param right
	 *            Distance traveled by right wheel in inches
	 */
	public DriveDistanceCommand(double left, double right) {
		requires(Robot.driveSubSys);
		this.left = left;
		this.right = right;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.driveSubSys.resetPosition();
		double left_target = left / Robot.driveSubSys.DISTANCE_TO_ROTATION_DENOMINATOR;
		double right_target = right / Robot.driveSubSys.DISTANCE_TO_ROTATION_DENOMINATOR;

		// double left_target = left;
		// double right_target = right;

		SmartDashboard.putNumber("Left Target", left_target);
		SmartDashboard.putNumber("Right target", right_target);

		Robot.driveSubSys.drive(left_target, right_target, TalonControlMode.Position);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {

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
