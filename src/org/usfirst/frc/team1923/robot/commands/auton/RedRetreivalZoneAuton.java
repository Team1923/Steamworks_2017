package org.usfirst.frc.team1923.robot.commands.auton;

import org.usfirst.frc.team1923.robot.RobotMap;
import org.usfirst.frc.team1923.robot.commands.driveCommands.DriveDistanceCommand;
import org.usfirst.frc.team1923.robot.commands.driveCommands.GyroTurnCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class RedRetreivalZoneAuton extends CommandGroup {
	public RedRetreivalZoneAuton(){
		this.addSequential(new DriveDistanceCommand(RobotMap.BASELINE_DISTANCE + RobotMap.NEUTRAL_ZONE_LENGTH));
		// turn below not necessary => if wanted remove comment
		this.addSequential(new GyroTurnCommand(-30)); //to angle towards feeder station
	}
}
