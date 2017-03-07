package org.usfirst.frc.team1923.robot.commands.visionCommands;

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
		// arm

		//Add sweeping if target not found
		//Add Gyro Code
		addParallel(new ShiftCommand(true));
		addSequential(new SlideCommand(true));
		//addSequential(new VisionScanCommand(0.3, 5));
		addSequential(new VisionAlignCommand());//Aligns Gear
		//addSequential(new WaitCommand(1));
		//Wiggle around for the peg to settle into gear
		//    	addSequential(new TurnTimeCommand(0.4,0.3));
		//    	addSequential(new TurnTimeCommand(-0.4,0.3));
		//    	addSequential(new TurnTimeCommand(0.4,0.3));
		//    	addSequential(new TurnTimeCommand(-0.4,0.3));
		//addSequential(new GearCommand(false));
		//Timer.delay(1);
		addSequential(new GearCommand(true));
		addSequential(new DriveDistanceCommand(-36));
		addSequential(new GearCommand(false));
		//TODO: Get as close to the feeder station as possible
	}
}
