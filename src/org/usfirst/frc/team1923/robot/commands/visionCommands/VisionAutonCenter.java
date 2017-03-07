package org.usfirst.frc.team1923.robot.commands.visionCommands;

import org.usfirst.frc.team1923.robot.commands.driveCommands.*;
import org.usfirst.frc.team1923.robot.commands.gearCommands.*;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class VisionAutonCenter extends CommandGroup {

    public VisionAutonCenter() {
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
    	//Aligns Gear
    	addParallel(new DriveDistanceCommand(20));
    	addParallel(new ShiftCommand(true));
    	addSequential(new SlideCommand(true));
    	addSequential(new VisionAlignCommand());//Aligns Gear
    	//Wiggle around for the peg to settle into gear
    	addSequential(new VisionScanCommand(0.4,0.3));
    	addSequential(new VisionScanCommand(-0.4,0.3));
    	addSequential(new VisionScanCommand(0.4,0.3));
    	addSequential(new VisionScanCommand(-0.4,0.3));
    	try {
			Thread.sleep(1000);
			addSequential(new GearCommand(true));
	    	addSequential(new DriveDistanceCommand(-36));
	    	addParallel(new GearCommand(false));
	    	addSequential(new TurnAngleCommand(-90));
	    	addSequential(new DriveDistanceCommand(60));
	    	addSequential(new TurnAngleCommand(90));
	    	addSequential(new DriveDistanceCommand(120));
	    	//TODO: Get as close to the feeder station as possible
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	
    	//TODO: Add Gear Drop and drive past line
    }
}
