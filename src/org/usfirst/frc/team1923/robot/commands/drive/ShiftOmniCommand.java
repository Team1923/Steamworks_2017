package org.usfirst.frc.team1923.robot.commands.drive;

import org.usfirst.frc.team1923.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class ShiftOmniCommand extends InstantCommand {

    private boolean up;

    /**
     * Shifts the back omni wheels up or down
     * 
     * @param up
     *            true if omnis lifts off floor
     */
    public ShiftOmniCommand(boolean up) {
        super();
        this.up = up;
    }

    @Override
    protected void initialize() {
        if (this.up) {
            Robot.driveSubSys.shiftUpOmnis();
        } else {
            Robot.driveSubSys.shiftDownOmnis();
        }
    }

}
