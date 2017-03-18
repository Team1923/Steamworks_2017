package org.usfirst.frc.team1923.robot.subsystems;

import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team1923.robot.RobotMap;
import org.usfirst.frc.team1923.robot.commands.climbCommands.ClimbCommand;
import org.usfirst.frc.team1923.robot.commands.visionCommands.VisionAlignCommand;
import org.usfirst.frc.team1923.robot.commands.visionCommands.VisionProcessing;
import org.usfirst.frc.team1923.robot.utils.GripPipeline;

import edu.wpi.cscore.AxisCamera;
import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.VisionThread;
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
	
	public boolean found;
	
	private double sum;
	private double[] def; 
	
	public double centerX;
	
	public Ultrasonic frontSonar;

	private VisionThread visionThread;
	 Mat source = new Mat();
     Mat output = new Mat();
     CvSink cvSink;
     CvSource outputStream;
     GripPipeline pipe;
	
	/**
	 * Initializes CameraServer and NetworkTables
	 */
	public VisionSubsystem() {
		//TODO: Implement Bounding Rectangle
		//TODO: IMplement Controller Vibration when Match time is getting low
		//Start Camera Server
		//CameraServer.getInstance().addAxisCamera(RobotMap.CAMERA_IP);
		//Testing Drawing Bounding Rectangle Around Peg
		/*
		CvSource output = CameraServer.getInstance().putVideo("Annotated", 320, 240);
		*/
		//TODO: Account for difference in areas of tape to change turn value
		//TODO: Add ultrasonic sensors
		def = new double[0];
		table = NetworkTable.getTable(RobotMap.NEWTORK_TABLE_ADDRESS);
		frontSonar = new Ultrasonic(RobotMap.FRONT_SONAR_PING_PORT, RobotMap.FRONT_SONAR_ECHO_PORT, Unit.kInches);
		frontSonar.setEnabled(true);
		frontSonar.setAutomaticMode(true);
		dist=frontSonar.getRangeInches();
		found=false;

		 AxisCamera camera = CameraServer.getInstance().addAxisCamera("Axis Cam", "10.19.21.15");
		camera.setResolution(320, 240);
        
        cvSink = CameraServer.getInstance().getVideo();
        outputStream = CameraServer.getInstance().putVideo("Blur", 320, 240);
        
        pipe = new GripPipeline();
       	
		refresh();
	}
	
	public void refresh(){
		//Process Image
		try{
		cvSink.grabFrameNoTimeout(source);
		
		pipe.process(source);
		if (!pipe.filterContoursOutput().isEmpty()) {
	            Rect r = Imgproc.boundingRect(pipe.filterContoursOutput().get(0));
	                centerX = r.x + (r.width / 2);
	            SmartDashboard.putNumber("Center X Pipeline: ", centerX);
	        }
	        
		//Imgproc.cvtColor(source, output, Imgproc.COLOR_BGR2GRAY);
		outputStream.putFrame(pipe.hslThresholdOutput());
		//outputStream.putFrame(output);
		}catch (UnsatisfiedLinkError b){
			System.out.println("Error");
		}
		
		
		//Extrapolate Values (Turn, distance, etc.)
		dist=frontSonar.getRangeInches();
		x = table.getNumberArray("centerX", def);
		widtharr= table.getNumberArray("width", def);
		sum=0;
		for(double a: widtharr){
			sum+=a;
		}
		if(widtharr.length>0)
			width=sum/widtharr.length;
		else
			width=Integer.MAX_VALUE;
		sum=0;
		for(double a : x){
			sum+=a;
		}
		if(x.length>0)
			centerx=sum/x.length;		//centerx: pixel value of middle of peg
		else
			centerx=Integer.MIN_VALUE;   
		turn=centerx-RobotMap.IMG_WIDTH/2+13;
		turn/=RobotMap.TURN_CONSTANT; 
		//Check Boundaries of turn
		if(turn<-1)
			turn=-1;
		else if(turn>1)
			turn=1;						//TODO: Use PID to get to turn value and use an angle instead of turn (Using IMU)
		if(x.length==0)
			turn=Integer.MIN_VALUE;
		
		//Logging
		SmartDashboard.putNumber("Center X: ", centerx);
		SmartDashboard.putNumber("Distance to target(Ultrasonic): ", dist);
		SmartDashboard.putNumber("Width: ", width);
		SmartDashboard.putNumber("Turn: ", width);
		
	}
	

	public void initDefaultCommand() {
		setDefaultCommand(new VisionProcessing());
	}
	
}
