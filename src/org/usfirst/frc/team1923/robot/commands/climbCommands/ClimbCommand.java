package org.usfirst.frc.team1923.robot.commands.climbCommands;

import org.usfirst.frc.team1923.robot.Robot;
import org.usfirst.frc.team1923.robot.subsystems.ClimberSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ClimbCommand extends Command {

	public ClimbCommand() {
		requires(Robot.climbSubSys);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Robot.climbSubSys.set(Robot.climbSubSys.CLIMB_POWER);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return Robot.climbSubSys.overCurrent.get(); // Ends if over currents
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.climbSubSys.set(0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
