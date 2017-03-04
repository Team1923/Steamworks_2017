package org.usfirst.frc.team1923.robot.commands.driveCommands;

import org.usfirst.frc.team1923.robot.subsystems.DrivetrainSubsystem;

/**
 *
 */
public class TurnAngleCommand extends DriveDistanceCommand {

	public TurnAngleCommand(double angle) {
		super(DrivetrainSubsystem.angleToDistance(angle), -DrivetrainSubsystem.angleToDistance(angle));
	}
}