package org.usfirst.frc.team1923.robot.commands.climb;

import org.usfirst.frc.team1923.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ClimbCommand extends Command {

    private double speed, leftTrigger, rightTrigger;

    public ClimbCommand() {
        requires(Robot.climbSubSys);
    }

    @Override
    protected void initialize() {
        this.speed = 0;
        this.leftTrigger = 0;
        this.rightTrigger = 0;
    }

    @Override
    protected void execute() {
        this.rightTrigger = Robot.oi.op.rt.getX() > 0.07 ? Robot.oi.op.rt.getX() : 0;
        this.leftTrigger = Robot.oi.op.lt.getX() > 0.07 ? Robot.oi.op.lt.getX() : 0;

        this.speed = this.rightTrigger > 0.07 ? this.rightTrigger : this.leftTrigger;
        Robot.climbSubSys.set(this.speed);
    }

    @Override
    protected boolean isFinished() {
        return true; // TODO: Figure out overcurrent situation
    }

    @Override
    protected void end() {
        // Robot.climbSubSys.set(0); //TODO: Add limit switch to top of climber
        // to automatically stop org.usfirst.frc.team1923.robot.commands.climb
    }

    @Override
    protected void interrupted() {
        end();
    }

}
