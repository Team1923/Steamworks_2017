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
	private double left_target, right_target;

	public DriveDistanceCommand(double left, double right) {
		requires(Robot.driveSubSys);
		this.left = left;
		this.right = right;

	}

	// Called just before this Command runs the first time
	protected void initialize() {

		Robot.driveSubSys.resetPosition();

		left_target = left / Robot.driveSubSys.DISTANCE_TO_ROTATION_DENOMINATOR;
		right_target = right / Robot.driveSubSys.DISTANCE_TO_ROTATION_DENOMINATOR;

		SmartDashboard.putNumber("Left Target", left_target);
		SmartDashboard.putNumber("Right target", right_target);

	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Robot.driveSubSys.drive(left_target, right_target, TalonControlMode.Position);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		System.out.println(Robot.driveSubSys.getLeftError() + " " + Robot.driveSubSys.getRightError());
		return (Robot.driveSubSys.getLeftTarget() != 0 && Robot.driveSubSys.getRightTarget() != 0)
				&& (Math.abs(Robot.driveSubSys.getLeftError()) < 400)
				&& (Math.abs(Robot.driveSubSys.getRightError()) < 400);
	}

	// Called once after isFinished returns true
	protected void end() {
		System.out.println("END END END");
//		Robot.driveSubSys.resetPosition();
		// Robot.driveSubSys.disable();
		Robot.driveSubSys.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		System.out.println("INTERRUPT");
		end();
	}
}
