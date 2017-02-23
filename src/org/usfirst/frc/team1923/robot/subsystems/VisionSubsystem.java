package org.usfirst.frc.team1923.robot.subsystems;

import org.usfirst.frc.team1923.robot.RobotMap;
import org.usfirst.frc.team1923.robot.commands.climbCommands.ClimbCommand;
import org.usfirst.frc.team1923.robot.commands.visionCommands.VisionAlignCommand;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 *
 */
public class VisionSubsystem extends Subsystem {

	public NetworkTable table;
	public double[] x;
	public double centerx,turn;
	
	private double sum;
	private double[] def; 
	
	/**
	 * Initializes CameraServer and NetworkTables
	 */
	public VisionSubsystem() {
		//Start Camera Server
		
		//TODO: Account for difference in areas of tape to change turn value
		//TODO: Add ultrasonic sensors
		def = new double[0];
		table = NetworkTable.getTable(RobotMap.NEWTORK_TABLE_ADDRESS);
		refresh();
	}
	
	public void refresh(){
		x = table.getNumberArray("centerx", def);
		sum=0;
		for(double a : x){
			sum+=a;
		}
		if(x.length>0)
			centerx=sum/x.length;		//centerx: pixel value of middle of peg
		else
			centerx=Integer.MIN_VALUE;   
		turn=centerx-RobotMap.IMG_WIDTH;
		turn/=RobotMap.TURN_CONSTANT; 
		//Check Boundaries of turn
		if(turn<-1)
			turn=-1;
		else if(turn>1)
			turn=1;						//TODO: Use PID to got to turn value and use an angle instead of turn (Using IMU)
	}
	

	public void initDefaultCommand() {
		setDefaultCommand(new VisionAlignCommand());
	}
}
