
package org.usfirst.frc.team1923.robot;

import org.usfirst.frc.team1923.robot.commands.EmptyCommand;
import org.usfirst.frc.team1923.robot.commands.driveCommands.DriveDistanceCommand;
import org.usfirst.frc.team1923.robot.subsystems.ClimberSubsystem;
import org.usfirst.frc.team1923.robot.subsystems.DrivetrainSubsystem;
import org.usfirst.frc.team1923.robot.subsystems.GearSubsystem;

import org.usfirst.frc.team1923.robot.OI;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	// Declare one instance of each subsystem and OI.
	public static DrivetrainSubsystem driveSubSys;
	public static ClimberSubsystem climbSubSys;
	public static GearSubsystem gearSubSys;
	public static OI oi;

	Command autonomousCommand;
	SendableChooser<Command> chooser = new SendableChooser<Command>();
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
		oi = new OI();

		chooser.addDefault("Default Auto", new EmptyCommand());
		chooser.addObject("Drive 15 inches", new DriveDistanceCommand(15,15));

//		if (driverStation.getAlliance().equals(Alliance.Blue)) {
//			// TODO: Add blue autons
//		} else if (driverStation.getAlliance().equals(Alliance.Red)) {
//			// TODO: Add red autons
//		} else {
//			// TODO: Add all autons
//		}
		
		SmartDashboard.putNumber("P Value", 0.08);
		SmartDashboard.putNumber("I Value", 0);
		SmartDashboard.putNumber("D Value", 0);
		SmartDashboard.putNumber("F Value", 0.01);

		// chooser.addObject("My Auto", new MyAutoCommand());
		SmartDashboard.putData("auto mode", chooser);
		
		
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
		// new GearSetHomeCommand().start();
		// TODO: Uncomment for COMPETITION!!!
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {

		SmartDashboard.putNumber("Left Enc", driveSubSys.getLeftPosition());
		SmartDashboard.putNumber("Right enc", driveSubSys.getRightPosition());
		double p = SmartDashboard.getNumber("P Value", 0);
		double i = SmartDashboard.getNumber("I Value", 0);
		double d = SmartDashboard.getNumber("D Value", 0);
		double f = SmartDashboard.getNumber("F Value", 0);
		driveSubSys.setPID(p, i, d, f);
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
}
