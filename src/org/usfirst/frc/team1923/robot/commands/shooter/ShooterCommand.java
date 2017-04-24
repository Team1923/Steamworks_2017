package org.usfirst.frc.team1923.robot.commands.shooter;

import org.usfirst.frc.team1923.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ShooterCommand extends Command {

    private double setpoint;
    private boolean wait;

    public ShooterCommand(double setpoint) {
        this(setpoint, false);
    }

    /**
     * Changes shooter speed to the given value.
     *
     * @param setpoint
     *            The speed at which to run the shooter at.
     * @param wait
     *            Whether or not to wait until the shooter has reached the speed
     *            before ending the command.
     */
    public ShooterCommand(double setpoint, boolean wait) {
        requires(Robot.shooterSubSys);

        this.setpoint = setpoint;
        this.wait = wait;
    }

    @Override
    protected void initialize() {
        Robot.shooterSubSys.setSetpoint(this.setpoint);
    }

    @Override
    protected void execute() {

    }

    @Override
    protected boolean isFinished() {
        return this.wait && Robot.shooterSubSys.getError() < Robot.shooterSubSys.allowableError;
    }

    @Override
    protected void end() {

    }

    @Override
    protected void interrupted() {
        end();
    }

}