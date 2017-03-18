
package org.usfirst.frc.team1923.robot;

import org.usfirst.frc.team1923.robot.commands.DoNothing;
import org.usfirst.frc.team1923.robot.commands.driveCommands.*;
import org.usfirst.frc.team1923.robot.commands.gearCommands.GearSetHomeCommand;
import org.usfirst.frc.team1923.robot.commands.visionCommands.*;
import org.usfirst.frc.team1923.robot.commands.visionCommands.VisionAutonLeft;
import org.usfirst.frc.team1923.robot.commands.visionCommands.VisionAutonRight;
import org.usfirst.frc.team1923.robot.subsystems.*;
import org.usfirst.frc.team1923.robot.subsystems.ClimberSubsystem;
import org.usfirst.frc.team1923.robot.subsystems.DrivetrainSubsystem;
import org.usfirst.frc.team1923.robot.subsystems.GearSubsystem;
import org.usfirst.frc.team1923.robot.utils.*;

import com.ctre.PigeonImu;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;

import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team1923.robot.OI;

import edu.wpi.cscore.AxisCamera;
import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.VisionThread;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	private long lastLog = 0;

	// Declare one instance of each subsystem and OI.
	public static DrivetrainSubsystem driveSubSys;
	public static ClimberSubsystem climbSubSys;
	public static GearSubsystem gearSubSys;
	public static VisionSubsystem visionSubSys;
	public static OI oi;
	
	public static PowerDistributionPanel pdp = new PowerDistributionPanel();
	//private VisionThread visionThread;
	
	private static final int IMG_WIDTH = 320;
	private static final int IMG_HEIGHT = 240;
	
	private VisionThread visionThread;
	private double centerX = 0.0;
	
	private final Object imgLock = new Object();
	 Mat source = new Mat();
     Mat output = new Mat();
     CvSink cvSink;
     CvSource outputStream;

	public static PrintWriter writer;

	Command autonomousCommand;
	SendableChooser<Command> chooser = new SendableChooser<Command>();
	SendableChooser<Command> driver = new SendableChooser<Command>();
	DriverStation driverStation = DriverStation.getInstance();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		// Initialize all the subsystems and OI.

		gearSubSys = new GearSubsystem();
		driveSubSys = new DrivetrainSubsystem();
		climbSubSys = new ClimberSubsystem();
		visionSubSys = new VisionSubsystem();

		oi = new OI();
		
		/*
			 AxisCamera camera = CameraServer.getInstance().addAxisCamera("Axis Cam", "10.19.21.15");
			camera.setResolution(320, 240);
             
             cvSink = CameraServer.getInstance().getVideo();
             outputStream = CameraServer.getInstance().putVideo("Blur", 320, 240);
             

             new Thread(() -> {
     			while(true){
     		   	System.out.println("Testing:");
     			 cvSink.grabFrame(source);
     	         Imgproc.cvtColor(source, output, Imgproc.COLOR_BGR2GRAY);
     	         outputStream.putFrame(output);
     			}
     		}).start();
            	
		*/
		/*
		 //AxisCamera camera;// = CameraServer.getInstance().startAutomaticCapture();
         //camera=CameraServer.getInstance().addAxisCamera("Camera", "10.19.21.15");
		 AxisCamera camera = new AxisCamera("Camera", "10.19.21.15");
         camera.setResolution(320, 240);
         //camera.setFPS(30);
         //camera.setExposureManual(20);
         //camera.setWhiteBalanceManual(0);
         //camera.setExposureManual(0);
         //camera.setExposureAuto();
         CvSource outputStream = CameraServer.getInstance().putVideo("HSL", 320, 240);
        
         visionThread = new VisionThread(camera, new GripPipelineT(), pipeline -> {
        	 //while(true){
        		// outputStream.putFrame(pipeline.hslThresholdOutput());
        	 while(true){
        	 System.out.println("Test");
        		 if (!pipeline.filterContoursOutput().isEmpty()) {
     	            Rect r = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0));
     	            synchronized (imgLock) {
     	                centerX = r.x + (r.width / 2);
     	            }
     	            System.out.println("Center X Pipeline: " + centerX);
     	        }
        	 }
        	 //}
        });
        visionThread.start();
        */
		
		/*
		AxisCamera camera;// = CameraServer.getInstance().startAutomaticCapture();
        camera=CameraServer.getInstance().addAxisCamera("Camera", "10.19.21.15");
		camera.setResolution(IMG_WIDTH, IMG_HEIGHT);
	    
	    visionThread = new VisionThread(camera, new GripPipelineT(), pipeline -> {
	    	//while(true){
	        if (!pipeline.filterContoursOutput().isEmpty()) {
	            Rect r = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0));
	            synchronized (imgLock) {
	                centerX = r.x + (r.width / 2);
	            }
	            System.out.println("Center X Pipeline: " + centerX);
	        }
	    	//}
	    });
	    visionThread.start();*/

		chooser.addDefault("Do Nothing Auto", new DoNothing());
		// chooser.addObject("Turn Time Auto", new TurnTimeCommand(0.25, 0.5));
		chooser.addObject("Vision Auton Right", new VisionAutonRight());
		chooser.addObject("Vision Auton Center", new VisionAutonCenter());
		chooser.addObject("Vision Auton Left", new VisionAutonLeft());
		// chooser.addObject("Test Align" , new TestAlign());
		// chooser.addObject("My Auto", new MyAutoCommand());
		// SmartDashboard.putData("Turn Auto", chooser);
		chooser.addObject("Drive 100 inches", new DriveDistanceCommand(100));
		chooser.addObject("Drive 2 seconds", new DriveTimeCommand(1, 2));

		// if (driverStation.getAlliance().equals(Alliance.Blue)) {
		// // TODO: Add blue autons
		// } else if (driverStation.getAlliance().equals(Alliance.Red)) {
		// // TODO: Add red autons
		// } else {
		// // TODO: Add all autons
		// }
		//
		// chooser.addObject("My Auto", new MyAutoCommand());

		// Driver Selection
		driver.addDefault("Chinmay", new ChoseDriverCommand(RobotMap.CHINMAY_PROFILE));
		driver.addObject("Suraj", new ChoseDriverCommand(RobotMap.SURAJ_PROFILE));
		driver.addObject("Anish", new ChoseDriverCommand(RobotMap.ANISH_PROFILE));

		SmartDashboard.putData("Auto Mode", chooser);
		SmartDashboard.putData("Driver", driver);
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		autonomousCommand = chooser.getSelected();

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if (autonomousCommand != null)
			autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {

		SmartDashboard.putNumber("Left Enc", driveSubSys.getLeftPosition());
		SmartDashboard.putNumber("Right enc", driveSubSys.getRightPosition());

//		if (this.lastLog + 100 < System.currentTimeMillis()) {
//			this.lastLog = System.currentTimeMillis();
//			log("Distance: " + Robot.visionSubSys.frontSonar.getRangeInches() + ", Center: "
//					+ Robot.visionSubSys.centerx + ", Found?: " + Robot.visionSubSys.found + ", Width: "
//					+ Robot.visionSubSys.width + ", Turn: " + Robot.visionSubSys.turn + ", Heading: "
//					+ Robot.driveSubSys.getImu().GetFusedHeading(new PigeonImu.FusionStatus()) + ", Enc: "
//					+ Robot.driveSubSys.getLeftPosition() + "," + Robot.driveSubSys.getRightPosition());
//		}

		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null)
			autonomousCommand.cancel();

		if (writer != null) {
			writer.close();
		}
		
		
		// new GearSetHomeCommand().start();
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {

		// SmartDashboard.putNumber("Left Enc", driveSubSys.getLeftPosition());
		// SmartDashboard.putNumber("Right enc",
		// driveSubSys.getRightPosition());
		// DrivetrainSubsystem.TURNING_CONSTANT =
		// SmartDashboard.getNumber("turning", 1.06);
		// double p = SmartDashboard.getNumber("P Value", 0);
		// double i = SmartDashboard.getNumber("I Value", 0);
		// double d = SmartDashboard.getNumber("D Value", 0);
		// double f = SmartDashboard.getNumber("F Value", 0);
		//Robot.visionSubSys.refresh();
		SmartDashboard.putNumber("Ultrasonic", Robot.visionSubSys.dist);
		
        
		// driveSubSys.setPID(p, i, d, f);
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}

	public static void log(String message) {
		try {
			if (writer == null) {
				writer = new PrintWriter(new BufferedWriter(new FileWriter("/tmp/match.log")));
			}

			writer.println("[" + System.currentTimeMillis() + "] " + message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
