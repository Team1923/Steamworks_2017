package org.usfirst.frc.team1923.robot.commands.drive;

import org.usfirst.frc.team1923.robot.Robot;
import org.usfirst.frc.team1923.robot.RobotMap;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class ResetEncoderCommand extends InstantCommand {

    /**
     * Resets the position values of the drive encoders
     */
    public ResetEncoderCommand() {
        super();
    }

    @Override
    protected void initialize() {
        if (RobotMap.DEBUG) {
            System.out.println("Reset Position");
        }
        Robot.driveSubSys.resetPosition();
    }

}
