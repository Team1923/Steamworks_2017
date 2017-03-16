package org.usfirst.frc.team1923.robot.commands.drive;

import org.usfirst.frc.team1923.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class ResetEncoderCommand extends InstantCommand {

    public ResetEncoderCommand() {
        super();
    }

    @Override
    protected void initialize() {
        Robot.driveSubSys.resetPosition();
    }

}
