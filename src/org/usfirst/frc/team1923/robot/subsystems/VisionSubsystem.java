package org.usfirst.frc.team1923.robot.subsystems;

import org.usfirst.frc.team1923.robot.RobotMap;
import org.usfirst.frc.team1923.robot.commands.climbCommands.ClimbCommand;
import org.usfirst.frc.team1923.robot.commands.visionCommands.VisionAlignCommand;
import org.usfirst.frc.team1923.robot.commands.visionCommands.VisionProcessing;

import edu.wpi.cscore.CvSource;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Ultrasonic.Unit;
import edu.wpi.first.wpilibj.Ultrasonic;

/**
 *
 */
public class VisionSubsystem extends Subsystem {

	public NetworkTable table;
	public double[] x;
	public double centerx,turn;
	public double[] widtharr;
	public double width;
	public double dist;
	
	private double sum;
	private double[] def; 
	
	public Ultrasonic frontSonar;
	
	/**
	 * Initializes CameraServer and NetworkTables
	 */
	public VisionSubsystem() {
		//TODO: Implement Bounding Rectangle
		//TODO: IMplement Controller Vibration when Match time is getting low
		//Start Camera Server
		CameraServer.getInstance().addAxisCamera(RobotMap.CAMERA_IP);
		//Testing Drawing Bounding Rectangle Around Peg
		/*
		CvSource output = CameraServer.getInstance().putVideo("Annotated", 320, 240);
		*/
		//TODO: Account for difference in areas of tape to change turn value
		//TODO: Add ultrasonic sensors
		def = new double[0];
		table = NetworkTable.getTable(RobotMap.NEWTORK_TABLE_ADDRESS);
		frontSonar = new Ultrasonic(RobotMap.FRONT_SONAR_PING_PORT, RobotMap.FRONT_SONAR_ECHO_PORT, Unit.kMillimeters);
		frontSonar.setEnabled(true);
		frontSonar.setAutomaticMode(true);
		dist=frontSonar.getRangeInches();
		refresh();
	}
	
	public void refresh(){
		dist=frontSonar.getRangeInches();
		x = table.getNumberArray("centerX", def);
		widtharr= table.getNumberArray("width", def);
		if(widtharr.length>0)
			width=widtharr[0];
		else
			width=Integer.MAX_VALUE;
		System.out.println("Width " + width);
		sum=0;
		for(double a : x){
			sum+=a;
		}
		if(x.length>0)
			centerx=sum/x.length;		//centerx: pixel value of middle of peg
		else
			centerx=Integer.MIN_VALUE;   
		turn=centerx-RobotMap.IMG_WIDTH/2;
		turn/=RobotMap.TURN_CONSTANT; 
		//Check Boundaries of turn
		if(turn<-1)
			turn=-1;
		else if(turn>1)
			turn=1;						//TODO: Use PID to get to turn value and use an angle instead of turn (Using IMU)
		if(x.length==0)
			turn=Integer.MIN_VALUE;
		
		//Testing
		SmartDashboard.putNumber("Center X: ", centerx);
		SmartDashboard.putNumber("Distance to target: ", dist);
		SmartDashboard.putNumber("Width: ", width);
		SmartDashboard.putNumber("Turn: ", width);
		
	}
	

	public void initDefaultCommand() {
		setDefaultCommand(new VisionProcessing());
	}
}
