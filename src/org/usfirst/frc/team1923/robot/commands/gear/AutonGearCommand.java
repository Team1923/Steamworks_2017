package org.usfirst.frc.team1923.robot.commands.gear;

import org.usfirst.frc.team1923.robot.Robot;
import org.usfirst.frc.team1923.robot.RobotMap;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 * TODO: Change to a ConditionalCommand that uses GearCommand
 */
public class AutonGearCommand extends InstantCommand {

    private boolean open;
    private boolean toggle;

    public AutonGearCommand() {
        super();
        requires(Robot.gearSubSys);
        this.toggle = true;
    }

    public AutonGearCommand(boolean open) {
        super();
        requires(Robot.gearSubSys);
        this.open = open;
    }

    // Called once when the command executes
    @Override
    protected void initialize() {
        if (Robot.visionSubSys.isGearFound() && Robot.visionSubSys.getDistance() < RobotMap.PEG_DIST) {
            if (this.toggle) {
                Robot.gearSubSys.gearShift();
            } else if (this.open) {
                Robot.gearSubSys.gearOpen();
            } else {
                Robot.gearSubSys.gearClose();
            }
        }
    }

}
