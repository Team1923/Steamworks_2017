package org.usfirst.frc.team1923.robot.commands.visionCommands;

import org.usfirst.frc.team1923.robot.commands.driveCommands.DriveTimeCommand;
import org.usfirst.frc.team1923.robot.commands.gearCommands.GearCommand;
import org.usfirst.frc.team1923.robot.commands.gearCommands.SlideCommand;

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
    	addSequential(new SlideCommand(true));
    	addSequential(new VisionAlignCommand());//Aligns Gear
    	try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	addSequential(new GearCommand(true));
    	addSequential(new DriveTimeCommand(-0.2, 1));
    	//addSequential(new DriveTimeCommand(-0.5,1)); 
    	
    	//TODO: Add Gear Drop and drive past line
    }
}
