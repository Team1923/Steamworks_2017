package org.usfirst.frc.team1923.robot.commands.gear;

import org.usfirst.frc.team1923.robot.Robot;
import org.usfirst.frc.team1923.robot.RobotMap;

import edu.wpi.first.wpilibj.command.ConditionalCommand;

public class AutonGearCommand extends ConditionalCommand {

    public AutonGearCommand() {
        super(new GearCommand());
    }

    public AutonGearCommand(boolean open) {
        super(new GearCommand(open));
    }

    @Override
    protected boolean condition() {
        return Robot.visionSubSys.isGearFound() && Robot.visionSubSys.getDistance() < RobotMap.PEG_DIST;
    }

}
