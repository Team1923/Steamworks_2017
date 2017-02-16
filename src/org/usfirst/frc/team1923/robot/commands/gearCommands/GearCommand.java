package org.usfirst.frc.team1923.robot.commands.gearCommands;

import org.usfirst.frc.team1923.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class GearCommand extends InstantCommand {

	private boolean open;

	public GearCommand() {
		super();
		requires(Robot.gearSubSys);
		this.open = !Robot.gearSubSys.gearIsShifted;
	}

	public GearCommand(boolean open) {
		super();
		requires(Robot.gearSubSys);
		this.open = open;
	}

	// Called once when the command executes
	protected void initialize() {
		if (open) {
			Robot.gearSubSys.gearOpen();
		} else {
			Robot.gearSubSys.gearClose();
		}
	}
}
