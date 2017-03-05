package org.usfirst.frc.team1923.robot.commands.climbCommands;

import org.usfirst.frc.team1923.robot.Robot;
import org.usfirst.frc.team1923.robot.subsystems.ClimberSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ClimbCommand extends Command {
 
	private double speed,lt,rt;
	
	public ClimbCommand() {
		requires(Robot.climbSubSys);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		speed=0;
		lt=0;
		rt=0;
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if(Robot.oi.op.rt.getX()>0.07)
			rt=Robot.oi.op.rt.getX();
		else
			rt=0;
		if(Robot.oi.op.lt.getX()>0.07)
			lt=-Robot.oi.op.lt.getX();
		else
			lt=0;
		if(rt>0.07)
			speed=rt;
		else
			speed=lt;
		
		Robot.climbSubSys.set(speed);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return true;			//TODO: Figure out overcurrent situation
	}

	// Called once after isFinished returns true
	protected void end() {
		//Robot.climbSubSys.set(0); //TODO: Add limit switch to top of climber to automatically stop climb
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
