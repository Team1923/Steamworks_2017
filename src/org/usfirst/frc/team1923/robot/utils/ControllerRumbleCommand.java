package org.usfirst.frc.team1923.robot.utils;

import org.usfirst.frc.team1923.robot.Robot;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.command.TimedCommand;

public class ControllerRumbleCommand extends TimedCommand {

    private double power;

    /**
     * Command to rumble the controller of the operator for a certain amount of
     * time. Default power of 50%
     * 
     * @param timeout time to rumble
     */
    public ControllerRumbleCommand(double timeout) {
        super(timeout);
        this.power = 0.5;
    }

    /**
     * Command to rumble the controller of the operator for a certain amount of
     * time.
     * 
     * @param timeout time to rumble
     * @param power 0-1 intensity of rumble
     */
    public ControllerRumbleCommand(double timeout, double power) {
        super(timeout);
        this.power = power;
    }

    @Override
    protected void initialize() {
        Robot.oi.op.setRumble(RumbleType.kRightRumble, power);
        Robot.oi.op.setRumble(RumbleType.kLeftRumble, power);
    }

    @Override
    protected void execute() {

    }

    @Override
    protected void end() {
        Robot.oi.op.setRumble(RumbleType.kLeftRumble, 0);
        Robot.oi.op.setRumble(RumbleType.kRightRumble, 0);
    }

    @Override
    protected void interrupted() {
        end();
    }

}
