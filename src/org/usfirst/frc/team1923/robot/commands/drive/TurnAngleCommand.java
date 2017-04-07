package org.usfirst.frc.team1923.robot.commands.drive;

import org.usfirst.frc.team1923.robot.subsystems.DrivetrainSubsystem;

public class TurnAngleCommand extends DriveDistanceCommand {

    /**
     * Turns a set angle using encoder counts
     * 
     * @param angle
     *            angle to turn right
     */
    public TurnAngleCommand(double angle) {
        super(DrivetrainSubsystem.angleToDistance(angle), -DrivetrainSubsystem.angleToDistance(angle));
    }

}
