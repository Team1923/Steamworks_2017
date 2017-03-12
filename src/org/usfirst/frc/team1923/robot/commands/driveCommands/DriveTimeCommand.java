package org.usfirst.frc.team1923.robot.commands.driveCommands;

import org.usfirst.frc.team1923.robot.Robot;

import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Command;

public class DriveTimeCommand extends Command {

	private double power;

	public DriveTimeCommand(double power, double timeOut) {
		requires(Robot.driveSubSys);
		setTimeout(timeOut);
		this.power = power;
	}

	protected void initialize() {

	}

	protected void execute() {
		Robot.driveSubSys.drive(power, power, TalonControlMode.PercentVbus);
	}

	protected boolean isFinished() {
		return isTimedOut();
	}

	protected void end() {
		Robot.driveSubSys.stop();
	}

	protected void interrupted() {
		end();
	}

}
