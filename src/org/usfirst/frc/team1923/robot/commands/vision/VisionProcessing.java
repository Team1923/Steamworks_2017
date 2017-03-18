package org.usfirst.frc.team1923.robot.commands.vision;

import org.usfirst.frc.team1923.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class VisionProcessing extends Command {

    /**
     * This command calls the refresh function, thus updating the center values
     */
    public VisionProcessing() {
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
        return false;
    }

    @Override
    protected void end() {

    }

    @Override
    protected void interrupted() {

    }

}
