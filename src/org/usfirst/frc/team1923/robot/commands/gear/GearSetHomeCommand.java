package org.usfirst.frc.team1923.robot.commands.gear;

import org.usfirst.frc.team1923.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class GearSetHomeCommand extends InstantCommand {

    /**
     * Sets the super structure in its uninitialized condition with the gear
     * closed and slide back
     */
    public GearSetHomeCommand() {
        super();
        requires(Robot.gearSubSys);
    }

    @Override
    protected void initialize() {
        Robot.gearSubSys.goHome();
    }

}
