package org.usfirst.frc.team1923.robot.commands.shooter;

import org.usfirst.frc.team1923.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ShooterCalibrateCommand extends Command {

    /**
     * When executed, the shooter motor(s) will spin based on trigger level,
     * right trigger overrides
     */
    public ShooterCalibrateCommand() {
        requires(Robot.shooterSubSys);
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void execute() {
        Robot.shooterSubSys.set(Robot.oi.op.getY());
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
        end();
    }

}
