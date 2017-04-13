package org.usfirst.frc.team1923.robot.commands.auton;

import org.usfirst.frc.team1923.robot.Robot;
import org.usfirst.frc.team1923.robot.commands.drive.DriveTimeCommand;
import org.usfirst.frc.team1923.robot.commands.drive.ShiftCommand;
import org.usfirst.frc.team1923.robot.commands.gear.AutonGearCommand;
import org.usfirst.frc.team1923.robot.commands.gear.SlideCommand;
import org.usfirst.frc.team1923.robot.commands.vision.VisionAlignCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class VisionAutonCenter extends CommandGroup {

    public VisionAutonCenter() {
        // Drops of gear on center peg
        Robot.visionSubSys.refresh();
        addSequential(new ShiftCommand(false));
        addSequential(new WaitCommand(0.2));

        addSequential(new VisionAlignCommand());// Aligns Gear
        addSequential(new WaitCommand(0.2));

        addSequential(new SlideCommand(true));
        addSequential(new WaitCommand(0.4));
        addSequential(new AutonGearCommand(true));
        addSequential(new WaitCommand(0.4));
        addSequential(new DriveTimeCommand(-0.5, 1));
    }

}
