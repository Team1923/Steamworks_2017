package org.usfirst.frc.team1923.robot.commands.gearCommands;

import org.usfirst.frc.team1923.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class GearSetHomeCommand extends InstantCommand {

	public GearSetHomeCommand() {
		super();
		requires(Robot.gearSubSys);
	}

	// Called once when the command executes
	protected void initialize() {
		Robot.gearSubSys.goHome();
	}

}
