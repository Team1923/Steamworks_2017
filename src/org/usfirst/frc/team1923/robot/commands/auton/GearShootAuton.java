package org.usfirst.frc.team1923.robot.commands.auton;

import org.usfirst.frc.team1923.robot.Robot;
import org.usfirst.frc.team1923.robot.RobotMap;
import org.usfirst.frc.team1923.robot.commands.drive.ShiftCommand;
import org.usfirst.frc.team1923.robot.commands.gear.AutonGearCommand;
import org.usfirst.frc.team1923.robot.commands.gear.SlideCommand;
import org.usfirst.frc.team1923.robot.commands.shooter.ShooterSpinUpCommand;
import org.usfirst.frc.team1923.robot.commands.vision.VisionGearAlignCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class GearShootAuton extends CommandGroup {

    public GearShootAuton() {
        // Drops of gear on center peg
        Robot.visionSubSys.refreshGear();
        addSequential(new ShiftCommand(false));
        addSequential(new WaitCommand(0.2));

        addSequential(new VisionGearAlignCommand());// Aligns Gear
        addSequential(new WaitCommand(0.2));

        addParallel(new ShooterSpinUpCommand(RobotMap.SHOOTER_CENTER_SETPOINT_PEG));
        addSequential(new SlideCommand(true));
        addSequential(new WaitCommand(0.4));
        addSequential(new AutonGearCommand(true));
        addSequential(new WaitCommand(0.4));
        // addSequential(new IndexOnCommand(true));
        // addSequential(new DriveTimeCommand(-0.5, 1));
    }
}
