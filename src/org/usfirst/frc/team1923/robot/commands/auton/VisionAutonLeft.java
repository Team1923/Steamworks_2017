package org.usfirst.frc.team1923.robot.commands.auton;

import org.usfirst.frc.team1923.robot.Robot;
import org.usfirst.frc.team1923.robot.commands.drive.DriveTimeCommand;
import org.usfirst.frc.team1923.robot.commands.drive.ShiftCommand;
import org.usfirst.frc.team1923.robot.commands.gear.AutonGearCommand;
import org.usfirst.frc.team1923.robot.commands.gear.SlideCommand;
import org.usfirst.frc.team1923.robot.commands.vision.VisionPegAlignCommand;
import org.usfirst.frc.team1923.robot.commands.vision.VisionScanCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class VisionAutonLeft extends CommandGroup {

    public VisionAutonLeft() {

        Robot.visionSubSys.refresh();
        addSequential(new ShiftCommand(true));
        addSequential(new DriveTimeCommand(0.5, 0.5));
        // addSequential(new VisionScanLeftCommand(-0.3, 15));
        addSequential(new WaitCommand(0.2));
        addSequential(new VisionScanCommand(0.2, 5));
        // Add code if target is seen
        addSequential(new VisionPegAlignCommand());// Aligns Gear
        // Wiggle around for the peg to settle into gear
        addSequential(new WaitCommand(0.2));
        // addSequential(new TurnTimeCommand(0.4,0.25));
        // addSequential(new TurnTimeCommand(-0.4,0.32));
        // addSequential(new TurnTimeCommand(-0.4,0.4));

        addSequential(new SlideCommand(true));
        addSequential(new WaitCommand(0.4));
        addSequential(new AutonGearCommand(true));
        addSequential(new WaitCommand(0.4));
        addSequential(new DriveTimeCommand(-0.3, 1));
        // addSequential(new GearCommand(false));
    }
}
