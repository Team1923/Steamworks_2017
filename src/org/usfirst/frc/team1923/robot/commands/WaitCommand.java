package org.usfirst.frc.team1923.robot.commands;

import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 *
 */
public class WaitCommand extends TimedCommand {

    public WaitCommand(double timeout) {
        super(timeout);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	System.out.println("Wait Start");
    	}

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Called once after timeout
    protected void end() {
    	System.out.println("Wait END");
    	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
