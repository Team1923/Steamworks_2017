package org.usfirst.frc.team1923.robot.commands.shooter;

import org.usfirst.frc.team1923.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ShooterSpinUpCommand extends Command {

	private double setpoint;

	/**
	 * When executed, the climber motors will spin based on trigger level, right
	 * trigger overrides
	 */
	public ShooterSpinUpCommand(double setpoint) {
		requires(Robot.shooterSubSys);
	}

	@Override
	protected void initialize() {
		setpoint = 0;
	}

	@Override
	protected void execute() {
		Robot.shooterSubSys.setSetpoint(setpoint);
	}

	@Override
	protected boolean isFinished() {
		return Robot.shooterSubSys.getError() < 1; // TODO: Change this error
													// value
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
		end();
	}

}
