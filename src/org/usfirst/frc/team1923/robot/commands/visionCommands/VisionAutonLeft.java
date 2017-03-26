package org.usfirst.frc.team1923.robot.commands.visionCommands;

import org.usfirst.frc.team1923.robot.Robot;
import org.usfirst.frc.team1923.robot.commands.driveCommands.*;
import org.usfirst.frc.team1923.robot.commands.gearCommands.*;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class VisionAutonLeft extends CommandGroup {

    public VisionAutonLeft() {
    	
    	Robot.visionSubSys.refresh();
    	addSequential(new ShiftCommand(true));
		addSequential(new SlideCommand(true));
		addSequential(new DriveTimeCommand(0.5,0.5));
		addSequential(new VisionScanRightCommand(-0.3,15));
		addSequential(new WaitCommand(0.2));
		//addSequential(new VisionScanRightCommand(0.3, 5));
		Robot.visionSubSys.refresh();
		Robot.visionSubSys.refresh();
		Robot.visionSubSys.refresh();
		
		//Add code if target is seen
		if(Robot.visionSubSys.centerx>0){
			addSequential(new VisionPegAlignCommand());//Aligns Gear
			//Wiggle around for the peg to settle into gear
			addSequential(new WaitCommand(0.2));
			//addSequential(new TurnTimeCommand(0.4,0.25));
			//addSequential(new TurnTimeCommand(-0.4,0.32));
			//addSequential(new TurnTimeCommand(-0.4,0.4));
			addSequential(new WaitCommand(0.4));
			addSequential(new GearCommand(true));
			addSequential(new WaitCommand(0.4));
			addSequential(new DriveTimeCommand(-0.5, 1));
			//addSequential(new GearCommand(false));
		}
		else{
			//Add code for if target is not seen

		}
    }
}
