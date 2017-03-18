package org.usfirst.frc.team1923.robot.commands.drive;

import org.usfirst.frc.team1923.robot.Robot;
import org.usfirst.frc.team1923.robot.utils.DriveProfile.ProfileCurve;

import edu.wpi.first.wpilibj.command.Command;

public class ChoseDriverCommand extends Command {

    /**
     * Command for setting a driver curve to become the current profile
     * 
     * @param p
     *            ProfileCurve to be set
     */
    public ChoseDriverCommand(ProfileCurve p) {
        Robot.driveSubSys.driveProfile.setProfileCurve(p);
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void execute() {

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
    }

}
