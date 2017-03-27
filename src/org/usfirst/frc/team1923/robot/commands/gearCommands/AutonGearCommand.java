package org.usfirst.frc.team1923.robot.commands.gearCommands;

import org.usfirst.frc.team1923.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class AutonGearCommand extends InstantCommand {

	private boolean open;
	private boolean toggle;

	public AutonGearCommand() {
		super();
		requires(Robot.gearSubSys);
		toggle = true;
	}

	public AutonGearCommand(boolean open) {
		super();
		requires(Robot.gearSubSys);
		this.open = open;
	}

	// Called once when the command executes
	protected void initialize() {
		if(Robot.visionSubSys.found){
			if (toggle) {
				Robot.gearSubSys.gearShift();
			} else if (open) {
				Robot.gearSubSys.gearOpen();
			} else {
				Robot.gearSubSys.gearClose();
			}
		}
	}
}
