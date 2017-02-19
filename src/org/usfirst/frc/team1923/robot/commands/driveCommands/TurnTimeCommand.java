package org.usfirst.frc.team1923.robot.commands.driveCommands;


import org.usfirst.frc.team1923.robot.Robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Command;

public class TurnTimeCommand extends Command{
	
	private double power;

	public TurnTimeCommand(double power, double timeOut)
	{
		requires(Robot.driveSubSys);
		this.power = power;
		setTimeout(timeOut);
	}
	
	protected void initialize()
	{
		Robot.driveSubSys.turnTime(power);;
	}
	
	protected void execute()
	{
		
	}
	
	protected void end()
	{
		Robot.driveSubSys.stop();
	}
	
	protected void interrupted()
	{
		end();
	}
	

	@Override
	protected boolean isFinished() {
		return isTimedOut();
	}

}
