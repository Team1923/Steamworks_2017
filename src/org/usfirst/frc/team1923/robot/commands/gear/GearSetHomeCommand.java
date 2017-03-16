package org.usfirst.frc.team1923.robot.commands.gear;

import org.usfirst.frc.team1923.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class GearSetHomeCommand extends InstantCommand {

    public GearSetHomeCommand() {
        super();
        requires(Robot.gearSubSys);
    }

    @Override
    protected void initialize() {
        Robot.gearSubSys.goHome();
    }

}
