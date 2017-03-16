
package org.usfirst.frc.team1923.robot;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;

import org.usfirst.frc.team1923.robot.commands.auton.DoNothingAuton;
import org.usfirst.frc.team1923.robot.commands.auton.VisionCenterAuton;
import org.usfirst.frc.team1923.robot.commands.auton.VisionLeftAuton;
import org.usfirst.frc.team1923.robot.commands.auton.VisionRightAuton;
import org.usfirst.frc.team1923.robot.commands.drive.ChoseDriverCommand;
import org.usfirst.frc.team1923.robot.commands.drive.DriveDistanceCommand;
import org.usfirst.frc.team1923.robot.commands.drive.DriveTimeCommand;
import org.usfirst.frc.team1923.robot.subsystems.ClimberSubsystem;
import org.usfirst.frc.team1923.robot.subsystems.DrivetrainSubsystem;
import org.usfirst.frc.team1923.robot.subsystems.GearSubsystem;
import org.usfirst.frc.team1923.robot.subsystems.VisionSubsystem;

import com.ctre.PigeonImu;

import edu.wpi.first.wpilibj.DriverStation;
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

    private long lastLog = 0;

    // Declare one instance of each subsystem and OI.
    public static DrivetrainSubsystem driveSubSys;
    public static ClimberSubsystem climbSubSys;
    public static GearSubsystem gearSubSys;
    public static VisionSubsystem visionSubSys;
    public static OI oi;

    public static PrintWriter logger;

    private Command autonomousCommand;
    private SendableChooser<Command> autonChooser = new SendableChooser<Command>();
    private SendableChooser<Command> driverChooser = new SendableChooser<Command>();

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit() {
        gearSubSys = new GearSubsystem();
        driveSubSys = new DrivetrainSubsystem();
        climbSubSys = new ClimberSubsystem();
        visionSubSys = new VisionSubsystem();

        oi = new OI();

        this.autonChooser.addDefault("Do Nothing Auto", new DoNothingAuton());
        this.autonChooser.addObject("Vision Auton Right", new VisionRightAuton());
        this.autonChooser.addObject("Vision Auton Center", new VisionCenterAuton());
        this.autonChooser.addObject("Vision Auton Left", new VisionLeftAuton());
        this.autonChooser.addObject("Drive 100 inches", new DriveDistanceCommand(100));
        this.autonChooser.addObject("Drive 2 seconds", new DriveTimeCommand(1, 2));


        // Driver Selection
        this.driverChooser.addDefault("Chinmay", new ChoseDriverCommand(RobotMap.CHINMAY_PROFILE));
        this.driverChooser.addObject("Suraj", new ChoseDriverCommand(RobotMap.SURAJ_PROFILE));
        this.driverChooser.addObject("Anish", new ChoseDriverCommand(RobotMap.ANISH_PROFILE));

        SmartDashboard.putData("Auto Mode", this.autonChooser);
        SmartDashboard.putData("Driver", this.driverChooser);
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

        Robot.visionSubSys.refresh();
        SmartDashboard.putNumber("Vision Center X", Robot.visionSubSys.getCenterX());
        SmartDashboard.putNumber("Vision Distance", Robot.visionSubSys.getDistance());
        SmartDashboard.putNumber("Vision Width", Robot.visionSubSys.getWidth());
        SmartDashboard.putBoolean("Vision Found", Robot.visionSubSys.isFound());
    }

    /**
     * This autonomous (along with the chooser code above) shows how to select
     * between different autonomous modes using the dashboard. The sendable
     * chooser code works with the Java SmartDashboard. If you prefer the
     * LabVIEW Dashboard, remove all of the chooser code and uncomment the
     * getString code to get the auto name from the text box below the Gyro You
     * can add additional auto modes by adding additional commands to the
     * chooser code above (like the commented example) or additional comparisons
     * to the switch structure below with additional strings & commands.
     */
    @Override
    public void autonomousInit() {
        this.startLog();
        this.logData();

        this.autonomousCommand = this.autonChooser.getSelected();

        if (this.autonomousCommand != null) {
            this.autonomousCommand.start();
        }
    }

    /**
     * This function is called periodically during autonomous
     */
    @Override
    public void autonomousPeriodic() {
        this.logData();
        SmartDashboard.putNumber("Left Encoder", driveSubSys.getLeftPosition());
        SmartDashboard.putNumber("Right Encoder", driveSubSys.getRightPosition());

        Scheduler.getInstance().run();
    }

    @Override
    public void teleopInit() {
        if (this.autonomousCommand != null) {
            this.autonomousCommand.cancel();
        }

        this.stopLog();
    }

    /**
     * This function is called periodically during operator control
     */
    @Override
    public void teleopPeriodic() {
        Robot.visionSubSys.refresh();
        SmartDashboard.putNumber("Ultrasonic", Robot.visionSubSys.getDistance());
        Scheduler.getInstance().run();
    }

    /**
     * This function is called periodically during test mode
     */
    @Override
    public void testPeriodic() {
        LiveWindow.run();
    }

    private void logData() {
        if (this.lastLog + 100 > System.currentTimeMillis()) {
            return;
        }
        this.lastLog = System.currentTimeMillis();

        Robot.visionSubSys.refresh();

        String logMessage = "[" + DriverStation.getInstance().getMatchTime() + "] Voltage: " + DriverStation.getInstance().getBatteryVoltage()
                + ", Sonar: " + Robot.visionSubSys.getDistance() + ", VCenter: " + Robot.visionSubSys.getCenterX() + ", VFound: "
                + Robot.visionSubSys.isFound() + ", VWidth: " + Robot.visionSubSys.getWidth() + ", VTurn: " + Robot.visionSubSys.getTurn() + ", IMU Heading: "
                + Robot.driveSubSys.getImu().GetFusedHeading(new PigeonImu.FusionStatus()) + ", LEncPos: " + Robot.driveSubSys.getLeftPosition()
                + ", REncPos: " + Robot.driveSubSys.getRightPosition();

        if (logger != null) {
            logger.println(logMessage);
        }
    }

    private void startLog() {
        try {
            logger = new PrintWriter(new BufferedWriter(new FileWriter("~/match.log")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void stopLog() {
        logger.close();
    }

}
