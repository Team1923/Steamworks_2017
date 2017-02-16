package org.usfirst.frc.team1923.robot.commands;

import org.usfirst.frc.team1923.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class DriveTimeCommand extends Command{

	private double speed;
	
	public DriveTimeCommand(double speed, double timeOut){
		requires(Robot.driveSubSys);
		setTimeout(timeOut);
		this.speed = speed;
	}

	protected void initialize() {
		
	}

	protected void execute() {
		Robot.driveSubSys.set(speed, speed);
	}

	protected boolean isFinished() {
		return isTimedOut();
	}

	protected void end() {
		Robot.driveSubSys.stop();
	}

	protected void interrupted() {
		end();
	}


}
