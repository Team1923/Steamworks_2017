package org.usfirst.frc.team1923.robot.commands.gear;

import org.usfirst.frc.team1923.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class GearCommand extends InstantCommand {

    private boolean open;
    private boolean toggle;

    public GearCommand() {
        super();
        requires(Robot.gearSubSys);
        this.toggle = true;
    }

    public GearCommand(boolean open) {
        super();
        requires(Robot.gearSubSys);
        this.open = open;
    }

    @Override
    protected void initialize() {
        if (this.toggle) {
            Robot.gearSubSys.gearShift();
        } else if (this.open) {
            Robot.gearSubSys.gearOpen();
        } else {
            Robot.gearSubSys.gearClose();
        }
    }

}
