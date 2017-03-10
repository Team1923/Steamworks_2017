package org.usfirst.frc.team1923.robot.commands.auton;

import org.usfirst.frc.team1923.robot.commands.driveCommands.DriveDistanceCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class DriveToBaseline extends CommandGroup{
	public DriveToBaseline(){
		this.addSequential(new DriveDistanceCommand(93.25, 93.25));
	}
}
