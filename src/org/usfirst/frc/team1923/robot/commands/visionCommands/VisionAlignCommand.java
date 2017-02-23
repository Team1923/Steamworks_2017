package org.usfirst.frc.team1923.robot.commands.visionCommands;

import org.usfirst.frc.team1923.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class VisionAlignCommand extends Command {

	public double power,turn;
	
    public VisionAlignCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.visionSubSys);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//TODO: Change power value to account for distance
    	//TODO: Take into account values from ultrasonic sensors
    	if(Robot.visionSubSys.turn==Integer.MIN_VALUE){
    		power=0;
    		turn=0;
    	}
    	else{
    		power=0.2;
    		turn=Robot.visionSubSys.turn;
    	}
    	
    	System.out.println("Power: " + power + " Turn: " +  turn);
    	Robot.driveSubSys.auto(power, turn);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false; //TODO: return true when perfectly aligned
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
