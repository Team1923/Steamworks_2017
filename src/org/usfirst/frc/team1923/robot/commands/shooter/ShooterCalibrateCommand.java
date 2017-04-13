package org.usfirst.frc.team1923.robot.commands.shooter;

import org.usfirst.frc.team1923.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ShooterCalibrateCommand extends Command {

	private double speed;

	/**
	 * When executed, the climber motors will spin based on trigger level, right
	 * trigger overrides
	 */
	public ShooterCalibrateCommand() {
		requires(Robot.shooterSubSys);
	}

	@Override
	protected void initialize() {
		this.speed = 0;
	}

	@Override
	protected void execute() {
		Robot.shooterSubSys.set(Robot.oi.op.getY());
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
		end();
	}

}
