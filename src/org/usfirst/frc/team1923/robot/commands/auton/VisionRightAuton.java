package org.usfirst.frc.team1923.robot.commands.auton;

import org.usfirst.frc.team1923.robot.Robot;
import org.usfirst.frc.team1923.robot.commands.drive.DriveDistanceCommand;
import org.usfirst.frc.team1923.robot.commands.drive.ShiftCommand;
import org.usfirst.frc.team1923.robot.commands.drive.TurnAngleCommand;
import org.usfirst.frc.team1923.robot.commands.gear.GearCommand;
import org.usfirst.frc.team1923.robot.commands.gear.SlideCommand;
import org.usfirst.frc.team1923.robot.commands.vision.VisionAlignCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class VisionRightAuton extends CommandGroup {

    public VisionRightAuton() {
        addSequential(new ShiftCommand(true));
        addSequential(new SlideCommand(true));
        addSequential(new DriveDistanceCommand(93, 93, 2.5));
        addSequential(new TurnAngleCommand(-60));
        addSequential(new WaitCommand(0.2));
        // addSequential(new VisionScanRightCommand(0.3, 5));
        Robot.visionSubSys.refresh();

        // Add code if target is seen
        if (Robot.visionSubSys.getCenterX() > 0) {
            addSequential(new VisionAlignCommand());// Aligns Gear
            // Wiggle around for the peg to settle into gear
            addSequential(new WaitCommand(0.2));
            // addSequential(new TurnTimeCommand(0.4,0.25));
            // addSequential(new TurnTimeCommand(-0.4,0.32));
            // addSequential(new TurnTimeCommand(-0.4,0.4));
            addSequential(new WaitCommand(0.4));
            addSequential(new GearCommand(true));
            addSequential(new WaitCommand(0.4));
            addSequential(new DriveDistanceCommand(-36));
            addSequential(new GearCommand(false));
        } else {
            // Add code for if target is not seen
        }
    }

}
