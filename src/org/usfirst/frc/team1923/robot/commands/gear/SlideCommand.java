package org.usfirst.frc.team1923.robot.commands.gear;

import org.usfirst.frc.team1923.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class SlideCommand extends InstantCommand {

    private boolean open;
    private boolean toggle;

    /**
     * Toggles the position of the superstructure
     */
    public SlideCommand() {
        super();
        requires(Robot.gearSubSys);
        this.toggle = true;
    }

    /**
     * Sets the position of the superstructure to a known state
     * 
     * @param out
     *            true if sliding outwards
     */
    public SlideCommand(boolean out) {
        super();
        requires(Robot.gearSubSys);
        this.open = out;
    }

    @Override
    protected void initialize() {
        if (this.toggle) {
            Robot.gearSubSys.slideShift();
        } else if (this.open) {
            Robot.gearSubSys.slideForward();
        } else {
            Robot.gearSubSys.slideReverse();
        }
    }

}
