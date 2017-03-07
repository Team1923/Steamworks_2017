package org.usfirst.frc.team1923.robot.commands.visionCommands;

import org.usfirst.frc.team1923.robot.Robot;
import com.ctre.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.command.Command;

public class VisionScanCommand extends Command {

	private double power;

	public VisionScanCommand(double power, double timeOut) {
		requires(Robot.driveSubSys);
		this.power = power;
		setTimeout(timeOut);
	}

	protected void initialize() {
		Robot.driveSubSys.drive(power, -power, TalonControlMode.PercentVbus);
	}

	protected void execute() {

	}

	protected void end() {
		Robot.driveSubSys.stop();
	}

	protected void interrupted() {
		end();
	}

	@Override
	protected boolean isFinished() {
		return isTimedOut() || Robot.visionSubSys.centerx>20;
	}

}
