package org.usfirst.frc.team1923.robot.commands.visionCommands;

import org.usfirst.frc.team1923.robot.Robot;
import org.usfirst.frc.team1923.robot.commands.driveCommands.*;
import org.usfirst.frc.team1923.robot.commands.gearCommands.*;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class TestAlign extends CommandGroup {

	public TestAlign() {

		addParallel(new ShiftCommand(true));
		addSequential(new SlideCommand(true));
		addSequential(new VisionScanRightCommand(0.3, 5));
		Robot.visionSubSys.refresh();
		
		//Add code if target is seen
		if(Robot.visionSubSys.centerx>0){
			addSequential(new VisionAlignCommand());//Aligns Gear
			//Wiggle around for the peg to settle into gear
			addSequential(new WaitCommand(0.2));
			//addSequential(new TurnTimeCommand(0.4,0.25));
			//addSequential(new TurnTimeCommand(-0.4,0.32));
			//addSequential(new TurnTimeCommand(-0.4,0.4));
			addSequential(new WaitCommand(0.4));
			if(Robot.visionSubSys.found)
				addSequential(new GearCommand(true));
			addSequential(new WaitCommand(0.4));
			addSequential(new DriveDistanceCommand(-36));
			addSequential(new GearCommand(false));
		}
		else{
			//Add code for if target is not seen

		}
		//TODO: Get as close to the feeder station as possible
	}
}
