package org.usfirst.frc.team1923.robot.commands.driveCommands;

import org.usfirst.frc.team1923.robot.Robot;

/**
 *
 */
public class TurnAngleCommand extends DriveDistanceCommand {

    public TurnAngleCommand( double angle ) {
    	super(  Robot.driveSubSys.angleToDistance(angle), - Robot.driveSubSys.angleToDistance(angle) );
    }
}