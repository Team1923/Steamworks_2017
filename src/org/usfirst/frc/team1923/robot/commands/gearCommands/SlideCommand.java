package org.usfirst.frc.team1923.robot.commands.gearCommands;

import org.usfirst.frc.team1923.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class SlideCommand extends InstantCommand {
	private boolean open;

	public SlideCommand() {
		super();
		requires(Robot.gearSubSys);
		this.open = !Robot.gearSubSys.slideIsShifted;
	}

	public SlideCommand(boolean open) {
		super();
		requires(Robot.gearSubSys);
		this.open = open;
	}

	// Called once when the command executes
	protected void initialize() {
		if (open) {
			Robot.gearSubSys.slideForward();
		} else {
			Robot.gearSubSys.slideReverse();
		}
	}
}
