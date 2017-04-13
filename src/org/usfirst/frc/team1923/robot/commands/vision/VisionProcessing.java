package org.usfirst.frc.team1923.robot.commands.vision;

import org.usfirst.frc.team1923.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class VisionProcessing extends Command {

    public VisionProcessing() {
        // TODO: Is this really necessary?
        // Multiple commands should be able to access VisionSubsystem at the
        // same time since nothing is being modified physically
        requires(Robot.visionSubSys);
    }

    @Override
    protected void initialize() {
        Robot.visionSubSys.refresh();
    }

    @Override
    protected void execute() {
        Robot.visionSubSys.refresh();
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

    @Override
    protected void end() {

    }

    @Override
    protected void interrupted() {

    }

}
