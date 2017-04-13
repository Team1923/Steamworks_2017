package org.usfirst.frc.team1923.robot.commands.shooter;

import org.usfirst.frc.team1923.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class VisionShooterSpinUpCommand extends Command {

	private double speed;

	/**
	 * When executed, the climber motors will spin based on trigger level, right
	 * trigger overrides
	 */
	public VisionShooterSpinUpCommand() {
		requires(Robot.shooterSubSys);
	}

	@Override
	protected void initialize() {
		this.speed = 0;
	}

	@Override
	protected void execute() {
		// Get setpoint from function based on the area that the vision sees
		Robot.visionSubSys.refreshShooter();
		// Some function that returns a setpoint based on the area
		speed = 1000 - Robot.visionSubSys.shooterArea;// Made up for now
		Robot.shooterSubSys.setSetpoint(speed);
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
