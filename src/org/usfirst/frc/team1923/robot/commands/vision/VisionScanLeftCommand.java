package org.usfirst.frc.team1923.robot.commands.vision;

import org.usfirst.frc.team1923.robot.Robot;

import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Command;

public class VisionScanLeftCommand extends Command {

    private double power;

    public VisionScanLeftCommand(double power, double timeOut) {
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
        Robot.driveSubSys.drive(power, -power, TalonControlMode.PercentVbus);
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
        return isTimedOut() || (Robot.visionSubSys.centerx > 30 && Robot.visionSubSys.centerx < 28);
    }

}
