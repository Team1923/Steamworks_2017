package org.usfirst.frc.team1923.robot.commands.vision;

import org.usfirst.frc.team1923.robot.Robot;

import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

public class VisionScanCommand extends Command {

	private double power;

	/**
	 * Turns a certain direction in order to look for the vision target
	 * 
	 * @param power
	 *            Power of the left wheel
	 * @param timeOut
	 *            Timeout of the scan
	 */
	public VisionScanCommand(double power, double timeOut) {
		requires(Robot.driveSubSys);
		this.power = power;
		setTimeout(timeOut);
	}

	@Override
	protected void initialize() {

	}

	@Override
	protected void execute() {
		Robot.visionSubSys.refreshGear();
		Robot.driveSubSys.drive(power, -power, TalonControlMode.PercentVbus);
	}

	@Override
	protected void end() {
		if (isTimedOut()) {
			// If it times out without finding the target, don't do anything
			Scheduler.getInstance().removeAll();
		}
		Robot.driveSubSys.stop();
	}

	@Override
	protected void interrupted() {
		end();
	}

	@Override
	protected boolean isFinished() {
		return isTimedOut() || (Robot.visionSubSys.gearCenterx > 30 && Robot.visionSubSys.gearCenterx < 280);
	}

}
