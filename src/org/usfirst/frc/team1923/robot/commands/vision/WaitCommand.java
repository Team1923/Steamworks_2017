package org.usfirst.frc.team1923.robot.commands.vision;

import edu.wpi.first.wpilibj.command.Command;

public class WaitCommand extends Command {

    public WaitCommand(double seconds) {
        setTimeout(seconds);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

    @Override
    protected void end() {

    }

    @Override
    protected void interrupted() {

    }

}
