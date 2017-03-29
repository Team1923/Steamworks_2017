
package org.usfirst.frc.team1923.robot;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;

import org.usfirst.frc.team1923.robot.commands.auton.DoNothingAuton;
import org.usfirst.frc.team1923.robot.commands.auton.VisionAutonCenter;
import org.usfirst.frc.team1923.robot.commands.auton.VisionAutonLeft;
import org.usfirst.frc.team1923.robot.commands.auton.VisionAutonRight;
import org.usfirst.frc.team1923.robot.commands.drive.DriveDistanceCommand;
import org.usfirst.frc.team1923.robot.commands.drive.DriveTimeCommand;
import org.usfirst.frc.team1923.robot.commands.vision.TeleopVisionAlignCommand;
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

    public static ClimberSubsystem climbSubSys;
    public static GearSubsystem gearSubSys;
    public static VisionSubsystem visionSubSys;
    public static DrivetrainSubsystem driveSubSys;
    public static OI oi;

    private static PrintWriter logger;

    private long lastLog;
    private Command autonomousCommand;
    private SendableChooser<Command> autonChooser = new SendableChooser<Command>();
    private SendableChooser<Command> driverChooser = new SendableChooser<Command>();
    public static DriverStation driverStation = DriverStation.getInstance();

    /**
     * Called once when the robot first starts up.
     */
    @Override
    public void robotInit() {
        gearSubSys = new GearSubsystem();
        driveSubSys = new DrivetrainSubsystem();
        climbSubSys = new ClimberSubsystem();
        visionSubSys = new VisionSubsystem();

        oi = new OI();

        this.autonChooser.addDefault("Do Nothing Auto", new DoNothingAuton());
        this.autonChooser.addObject("Vision Auton Right", new VisionAutonRight());
        this.autonChooser.addObject("Vision Auton Center", new VisionAutonCenter());
        this.autonChooser.addObject("Vision Auton Left", new VisionAutonLeft());
        this.autonChooser.addObject("Vision Test", new TeleopVisionAlignCommand());
        this.autonChooser.addObject("Drive 100 inches", new DriveDistanceCommand(100));
        this.autonChooser.addObject("Drive 2 seconds", new DriveTimeCommand(1, 2));

        SmartDashboard.putData("Auto Mode", this.autonChooser);
        SmartDashboard.putData("Driver", this.driverChooser);
    }

    /**
     * Called once when the robot is disabled.
     */
    @Override
    public void disabledInit() {

    }

    /**
     * Called every 20ms when the robot is disabled.
     */
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
     * Called once at the start of autonomous mode.
     */
    @Override
    public void autonomousInit() {
        if (RobotMap.DEBUG) {
            this.startLog();
            this.logData();
        }
        visionSubSys.refresh();
        this.autonomousCommand = this.autonChooser.getSelected();

        if (this.autonomousCommand != null) {
            this.autonomousCommand.start();
        }
    }

    /**
     * Called every 20ms during autonomous mode.
     */
    @Override
    public void autonomousPeriodic() {
        if (RobotMap.DEBUG) {
            this.logData();
            SmartDashboard.putNumber("Left Encoder", driveSubSys.getLeftPosition());
            SmartDashboard.putNumber("Right Encoder", driveSubSys.getRightPosition());
        }
        Scheduler.getInstance().run();
    }

    /**
     * Called once at the start of tele-op mode.
     */
    @Override
    public void teleopInit() {
        if (this.autonomousCommand != null) {
            this.autonomousCommand.cancel();
        }

        if (RobotMap.DEBUG) {
            this.stopLog();
        }
    }

    /**
     * Called every 20ms during tele-op mode.
     */
    @Override
    public void teleopPeriodic() {
        Robot.visionSubSys.refresh();
        if (RobotMap.DEBUG) {
            SmartDashboard.putNumber("Ultrasonic", Robot.visionSubSys.getDistance());
        }
        Scheduler.getInstance().run();
    }

    // visionSubSys.refresh();

    /**
     * Called every 20ms during testing mode.
     */
    @Override
    public void testPeriodic() {
        LiveWindow.run();
    }

    /**
     * Log critical sensor data into a file.
     */
    private void logData() {
        if (this.lastLog + 100 > System.currentTimeMillis()) {
            return;
        }
        this.lastLog = System.currentTimeMillis();

        Robot.visionSubSys.refresh();

        String logMessage = "[" + DriverStation.getInstance().getMatchTime() + "] Voltage: " + DriverStation.getInstance().getBatteryVoltage()
                + ", Sonar: " + Robot.visionSubSys.getDistance() + ", VCenter: " + Robot.visionSubSys.getCenterX() + ", VFound: "
                + Robot.visionSubSys.isFound() + ", VWidth: " + Robot.visionSubSys.getWidth() + ", VTurn: " + Robot.visionSubSys.getTurn()
                + ", IMU Heading: " + Robot.driveSubSys.getImu().GetFusedHeading(new PigeonImu.FusionStatus()) + ", LEncPos: "
                + Robot.driveSubSys.getLeftPosition() + ", REncPos: " + Robot.driveSubSys.getRightPosition();

        if (logger != null) {
            logger.println(logMessage);
        }
    }

    /**
     * Initialize the PrintWriter for logging to match.log.
     */
    private void startLog() {
        try {
            logger = new PrintWriter(new BufferedWriter(new FileWriter("~/match.log")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Flush the buffer in PrintWriter to the disk and close the logger.
     */
    private void stopLog() {
        logger.close();
    }

}
