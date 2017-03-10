package org.usfirst.frc.team1923.robot.commands.auton;

import org.usfirst.frc.team1923.robot.RobotMap;
import org.usfirst.frc.team1923.robot.commands.driveCommands.DriveDistanceCommand;
import org.usfirst.frc.team1923.robot.commands.driveCommands.GyroTurnCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class BlueRetreivalZoneAuton extends CommandGroup {
	public BlueRetreivalZoneAuton(){
		this.addSequential(new DriveDistanceCommand(RobotMap.BASELINE_DISTANCE + RobotMap.NEUTRAL_ZONE_LENGTH));
		// turn below not necessary => if wanted to remove add comment
		this.addSequential(new GyroTurnCommand(30)); //to angle towards feeder station
	}
}
