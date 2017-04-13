package org.usfirst.frc.team1923.robot.commands.drive;

import org.usfirst.frc.team1923.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ArcadeRawDriveCommand extends Command {

    public ArcadeRawDriveCommand() {
        requires(Robot.driveSubSys);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        Robot.driveSubSys.auto(Robot.oi.driver.getLeftY(), Robot.oi.driver.getRightY());
    }

    @Override
    public void end() {
        Robot.driveSubSys.stop();
    }

    @Override
    public void interrupted() {
        Robot.driveSubSys.stop();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

}
