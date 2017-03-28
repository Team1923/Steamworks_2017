package org.usfirst.frc.team1923.robot.commands.auton;

import org.usfirst.frc.team1923.robot.Robot;
import org.usfirst.frc.team1923.robot.commands.drive.DriveTimeCommand;
import org.usfirst.frc.team1923.robot.commands.drive.ShiftCommand;
import org.usfirst.frc.team1923.robot.commands.gear.GearCommand;
import org.usfirst.frc.team1923.robot.commands.gear.SlideCommand;
import org.usfirst.frc.team1923.robot.commands.vision.VisionPegAlignCommand;
import org.usfirst.frc.team1923.robot.commands.vision.VisionScanLeftCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class VisionAutonRight extends CommandGroup {

    public VisionAutonRight() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        // addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        // addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
        Robot.visionSubSys.refresh();
        addSequential(new ShiftCommand(true));
        addSequential(new SlideCommand(true));
        addSequential(new DriveTimeCommand(0.5, 0.5));
        addSequential(new VisionScanLeftCommand(-0.3, 15));
        addSequential(new WaitCommand(0.2));
        // addSequential(new VisionScanRightCommand(0.3, 5));

        // Add code if target is seen
        if (Robot.visionSubSys.centerx > 0) {
            addSequential(new VisionPegAlignCommand());// Aligns Gear
            // Wiggle around for the peg to settle into gear
            addSequential(new WaitCommand(0.2));
            // addSequential(new TurnTimeCommand(0.4,0.25));
            // addSequential(new TurnTimeCommand(-0.4,0.32));
            // addSequential(new TurnTimeCommand(-0.4,0.4));
            addSequential(new WaitCommand(0.4));
            addSequential(new GearCommand(true));
            addSequential(new WaitCommand(0.4));
            addSequential(new DriveTimeCommand(-0.5, 1));
            // addSequential(new GearCommand(false));
        } else {
            // Add code for if target is not seen

        }
    }
}
