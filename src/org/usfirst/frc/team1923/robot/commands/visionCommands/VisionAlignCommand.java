package org.usfirst.frc.team1923.robot.commands.visionCommands;

import org.usfirst.frc.team1923.robot.Robot;
import org.usfirst.frc.team1923.robot.RobotMap;
import org.usfirst.frc.team1923.robot.commands.gearCommands.GearCommand;

//import com.sun.webkit.Timer;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class VisionAlignCommand extends Command {

	public double power,turn;
	//private Timer time;
	
    public VisionAlignCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.visionSubSys);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	//time = Timer.getTimer();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//TODO: Change power value to account for distance
    	//TODO: Take into account values from ultrasonic sensors
    	//new VisionAlignCommand(); TODO: Change to only run when needed to not waste processor cycles
    	Robot.visionSubSys.refresh();
    	if(Robot.visionSubSys.turn<-1){
    		power=0;
    		turn=0;
    	}
    	else{
    		power=-0.25;
    		//power=0;
    		turn=Robot.visionSubSys.turn;
    	}
    	
    	//Testing
    	//System.out.println("Power: " + power + " Turn: " +  turn);
    	
    	Robot.driveSubSys.auto(power, turn);
//    	try {
//    		Thread.sleep(100);
//			//time.wait(100);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if(Robot.visionSubSys.dist<=18)
    		return false;
    	else
    		return false;
    }

    // Called once after isFinished returns true
    protected void end() {
//    	try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//    	Command command = new GearCommand(true);
//    	command.start();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	
    }
}
