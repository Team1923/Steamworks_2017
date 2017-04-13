package org.usfirst.frc.team1923.robot.commands.shooter;

import org.usfirst.frc.team1923.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class BallFeedCommand extends InstantCommand {

	private boolean up;

	/**
	 * This command shifts the ball feed piston in or out
	 */
	public BallFeedCommand(boolean up) {
		super();
		this.up = up;
	}

	@Override
	protected void initialize() {
		if (this.up) {
			Robot.driveSubSys.shiftUp();
		} else {
			Robot.driveSubSys.shiftDown();
		}
	}

}
