package org.usfirst.frc.team1923.robot.commands.gear;

import org.usfirst.frc.team1923.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class SlideCommand extends InstantCommand {

    private boolean open;
    private boolean toggle;

    public SlideCommand() {
        super();
        requires(Robot.gearSubSys);
        this.toggle = true;
    }

    public SlideCommand(boolean open) {
        super();
        requires(Robot.gearSubSys);
        this.open = open;
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
