package org.usfirst.frc.team1923.robot.commands.vision;

import org.usfirst.frc.team1923.robot.Robot;

import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Command;

public class VisionScanRightCommand extends Command {

    private double power;

    public VisionScanRightCommand(double power, double timeOut) {
        requires(Robot.driveSubSys);
        this.power = power;
        setTimeout(timeOut);
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void execute() {
        Robot.visionSubSys.refresh();
        Robot.driveSubSys.drive(-this.power, this.power, TalonControlMode.PercentVbus);
    }

    @Override
    protected void end() {
        Robot.driveSubSys.stop();
    }

    @Override
    protected void interrupted() {
        end();
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut() || Robot.visionSubSys.getCenterX() > 20;
    }

}
