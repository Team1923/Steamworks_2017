package org.usfirst.frc.team1923.robot.commands.shooter;

import org.usfirst.frc.team1923.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class IndexerCommand extends InstantCommand {

    private double speed;

    public IndexerCommand(boolean on) {
        this.speed = on ? 0.3 : 0;
    }

    public IndexerCommand(double speed) {
        requires(Robot.shooterSubSys);

        this.speed = speed;
    }

    @Override
    protected void initialize() {
        Robot.shooterSubSys.index(this.speed);
    }

    @Override
    protected void execute() {

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
