package org.usfirst.frc.team1923.robot.commands.drive;

import org.usfirst.frc.team1923.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class ShiftCommand extends InstantCommand {

    private boolean up;

    public ShiftCommand(boolean up) {
        super();
        this.up = up;
    }

    @Override
    protected void initialize() {
        if (this.up) {
            Robot.driveSubSys.shiftUp();
        } else {
            Robot.driveSubSys.shiftDown();
        }
    }

}
