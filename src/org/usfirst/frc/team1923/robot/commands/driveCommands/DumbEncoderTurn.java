package org.usfirst.frc.team1923.robot.commands.driveCommands;

import org.usfirst.frc.team1923.robot.Robot;

import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DumbEncoderTurn extends Command {

	double angle, power;
    
	public DumbEncoderTurn() {
    	requires(Robot.driveSubSys);
    	this.angle = 90;
    	this.power= 0.3;
    }
	
	public DumbEncoderTurn(double angle) {
    	requires(Robot.driveSubSys);
    	this.angle=angle;
    	this.power= 0.3;
    }
	
	public DumbEncoderTurn(double angle, double power) {
    	requires(Robot.driveSubSys);
    	this.angle=angle;
    	this.power= power;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.driveSubSys.resetPosition();    
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {	
    	Robot.driveSubSys.drive(power, -1 * power, TalonControlMode.PercentVbus);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (Math.abs(Robot.driveSubSys.angleToDistance(angle)/Robot.driveSubSys.DISTANCE_TO_ROTATION_DENOMINATOR - Robot.driveSubSys.getLeftPosition())< 0.5)
        		&& (Math.abs(Robot.driveSubSys.angleToDistance(angle)/Robot.driveSubSys.DISTANCE_TO_ROTATION_DENOMINATOR - Robot.driveSubSys.getRightPosition())< 0.5);
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveSubSys.drive(0, 0, TalonControlMode.PercentVbus);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
