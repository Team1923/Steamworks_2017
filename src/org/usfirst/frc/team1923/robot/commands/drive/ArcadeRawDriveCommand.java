package org.usfirst.frc.team1923.robot.commands.drive;

import org.usfirst.frc.team1923.robot.Robot;
import org.usfirst.frc.team1923.robot.utils.DriveProfile.ProfileCurve;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command directly feeds the raw values on the joysticks to the motor
 * without PID.
 */
public class ArcadeRawDriveCommand extends Command {

    public ArcadeRawDriveCommand() {
        requires(Robot.driveSubSys);
    }

    public ArcadeRawDriveCommand(ProfileCurve p) {
        requires(Robot.driveSubSys);
        Robot.driveSubSys.driveProfile.setProfileCurve(p);
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void execute() {
        Robot.driveSubSys.auto(Robot.driveSubSys.driveProfile.scale(Robot.oi.driver.getLeftY()),
                Robot.driveSubSys.driveProfile.scale(Robot.oi.driver.getRightX()));
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
