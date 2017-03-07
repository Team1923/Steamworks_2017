package org.usfirst.frc.team1923.robot.commands.visionCommands;

import org.usfirst.frc.team1923.robot.Robot;
import org.usfirst.frc.team1923.robot.RobotMap;
import org.usfirst.frc.team1923.robot.commands.gearCommands.GearCommand;

//import com.sun.webkit.Timer;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class VisionAlignCommand extends Command {

	public double power,turn;
	public boolean found;
	public boolean aligned;
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
    		found=false;
    	}
    	else{
    		power=0.25;
    		//power=0;
    		found=true;
    		turn=Robot.visionSubSys.turn;
    	}
    	
    	//Testing
    	//System.out.println("Power: " + power + " Turn: " +  turn);
    	
    	Robot.driveSubSys.auto(power, turn);
    	if(found && (Robot.visionSubSys.width>=RobotMap.MAX_WIDTH || Robot.visionSubSys.dist<=RobotMap.MAX_DIST))
			aligned=true;
		else
			aligned=false;
		
		SmartDashboard.putBoolean("Found: ", found);
		SmartDashboard.putBoolean("Aligned and Ready to Drop: ", aligned);
//    	try {
//    		Thread.sleep(100);
//			//time.wait(100);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if(Robot.visionSubSys.width>=RobotMap.MAX_WIDTH || Robot.visionSubSys.dist<=RobotMap.MAX_DIST)
    		return true;
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
