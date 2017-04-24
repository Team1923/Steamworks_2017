package org.usfirst.frc.team1923.robot.commands.auton;

import org.usfirst.frc.team1923.robot.Robot;
import org.usfirst.frc.team1923.robot.RobotMap;
import org.usfirst.frc.team1923.robot.commands.drive.ShiftCommand;
import org.usfirst.frc.team1923.robot.commands.gear.AutonGearCommand;
import org.usfirst.frc.team1923.robot.commands.gear.SlideCommand;
import org.usfirst.frc.team1923.robot.commands.shooter.ShooterCommand;
import org.usfirst.frc.team1923.robot.commands.vision.VisionGearAlignCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class GearCenterShootAuton extends CommandGroup {

    public GearCenterShootAuton() {
        // Drops of gear on center peg
        Robot.visionSubSys.refreshGear();
        addSequential(new ShiftCommand(false));
        addSequential(new WaitCommand(0.2));

        addSequential(new VisionGearAlignCommand()); // Aligns Gear
        addSequential(new WaitCommand(0.2));

        addParallel(new ShooterCommand(RobotMap.SHOOTER_CENTER_SETPOINT_PEG, true));
        addSequential(new SlideCommand(true));
        addSequential(new WaitCommand(0.4));
        addSequential(new AutonGearCommand(true));
        addSequential(new WaitCommand(0.4));
        // addSequential(new IndexerCommand(true));
        // addSequential(new WaitCommand(4));
        // addSequential(new ShooterCommand(0));
        // addSequential(new DriveTimeCommand(-0.5, 1));
    }

}
