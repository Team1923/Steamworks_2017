package org.usfirst.frc.team1923.robot.commands.gearCommands;

import org.usfirst.frc.team1923.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class SlideCommand extends InstantCommand {
	private boolean open;
	private boolean toggle;

	public SlideCommand() {
		super();
		requires(Robot.gearSubSys);
		toggle = true;
	}

	public SlideCommand(boolean open) {
		super();
		requires(Robot.gearSubSys);
		this.open = open;
	}

	// Called once when the command executes
	protected void initialize() {
		if (toggle) {
			Robot.gearSubSys.slideShift();
		} else if (open) {
			Robot.gearSubSys.slideForward();
		} else {
			Robot.gearSubSys.slideReverse();
		}
	}
}
