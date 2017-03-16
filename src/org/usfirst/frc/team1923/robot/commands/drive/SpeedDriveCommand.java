package org.usfirst.frc.team1923.robot.commands.drive;

import org.usfirst.frc.team1923.robot.Robot;

import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Command;

public class SpeedDriveCommand extends Command {

    public static final int MAX_RPM = 1000;

    public SpeedDriveCommand() {
        requires(Robot.driveSubSys);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        Robot.driveSubSys.drive(MAX_RPM * Robot.driveSubSys.driveProfile.scale(Robot.oi.driver.getLeftY()),
                MAX_RPM * Robot.driveSubSys.driveProfile.scale(Robot.oi.driver.getRightY()), TalonControlMode.Speed);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Robot.driveSubSys.stop();
    }

    @Override
    protected void interrupted() {
        end();
    }

}
