package org.usfirst.frc.team1923.robot.commands.shooter;

import org.usfirst.frc.team1923.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ShooterSpinUpCommand extends Command {

    private final double CENTER_PEG_SPEED = 6000;
    private double setpoint;

    /**
     * When executed the shooter will spin up to speed
     */

    public ShooterSpinUpCommand() {
        setpoint = CENTER_PEG_SPEED;
    }

    public ShooterSpinUpCommand(double setpoint) {
        this.setpoint = setpoint;
        requires(Robot.shooterSubSys);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        Robot.shooterSubSys.setSetpoint(setpoint);
    }

    @Override
    protected boolean isFinished() {
        return Robot.shooterSubSys.getError() < Robot.shooterSubSys.allowableError; // TODO:
                                                                                    // Change
                                                                                    // this
                                                                                    // error
        // value
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
        end();
    }

}
