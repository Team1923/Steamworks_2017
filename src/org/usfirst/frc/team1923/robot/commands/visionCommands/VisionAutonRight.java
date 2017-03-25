package org.usfirst.frc.team1923.robot.commands.visionCommands;

import org.usfirst.frc.team1923.robot.Robot;
import org.usfirst.frc.team1923.robot.commands.driveCommands.*;
import org.usfirst.frc.team1923.robot.commands.gearCommands.*;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class VisionAutonRight extends CommandGroup {

    public VisionAutonRight() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    	addSequential(new ShiftCommand(true));
		addSequential(new SlideCommand(true));
		addSequential(new DriveDistanceCommand(93,2.5, false));
		addSequential(new TurnAngleCommand(-60));
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
			addSequential(new DriveTimeCommand(-0.4, 1));
			addSequential(new GearCommand(false));
		}
		else{
			//Add code for if target is not seen

		}
    }
}
