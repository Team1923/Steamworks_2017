<<<<<<< HEAD:src/org/usfirst/frc/team1923/robot/commands/driveCommands/ShiftOmnisCommand.java
package org.usfirst.frc.team1923.robot.commands.driveCommands;

import org.usfirst.frc.team1923.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class ShiftOmnisCommand extends InstantCommand {

	private boolean up;

	public ShiftOmnisCommand() {
		super();
	}

	public ShiftOmnisCommand(boolean up) {
		this.up = up;
	}

	// Called once when the command executes
	protected void initialize() {
		if (up) {
			Robot.driveSubSys.shiftUpOmnis();
		} else {
			Robot.driveSubSys.shiftDownOmnis();
		}
	}
}
=======
package org.usfirst.frc.team1923.robot.commands.driveCommands;

import org.usfirst.frc.team1923.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class ShiftOmnisCommand extends InstantCommand {

	private boolean up;

	public ShiftOmnisCommand() {
		super();
	}

	public ShiftOmnisCommand(boolean up) {
		this.up = up;
	}

	// Called once when the command executes
	protected void initialize() {
		if (up) {
			Robot.driveSubSys.shiftUpOmnis();
		} else {
			Robot.driveSubSys.shiftDownOmnis();
		}
	}
}
>>>>>>> beta:src/org/usfirst/frc/team1923/robot/commands/driveCommands/ShiftOmnisCommand.java
