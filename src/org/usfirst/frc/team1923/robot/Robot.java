
package org.usfirst.frc.team1923.robot;

import org.usfirst.frc.team1923.robot.commands.auton.DoNothingAuton;
import org.usfirst.frc.team1923.robot.commands.auton.GearCenterAuton;
import org.usfirst.frc.team1923.robot.commands.auton.GearLeftAuton;
import org.usfirst.frc.team1923.robot.commands.auton.GearRightAuton;
import org.usfirst.frc.team1923.robot.commands.drive.DriveTimeCommand;
import org.usfirst.frc.team1923.robot.subsystems.ClimberSubsystem;
import org.usfirst.frc.team1923.robot.subsystems.DebugSubsystem;
import org.usfirst.frc.team1923.robot.subsystems.DrivetrainSubsystem;
import org.usfirst.frc.team1923.robot.subsystems.GearSubsystem;
import org.usfirst.frc.team1923.robot.subsystems.ShooterSubsystem;
import org.usfirst.frc.team1923.robot.subsystems.VisionSubsystem;

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
    public static DebugSubsystem debugSubSys;
    public static DrivetrainSubsystem driveSubSys;
    public static GearSubsystem gearSubSys;
    public static ShooterSubsystem shooterSubSys;
    public static VisionSubsystem visionSubSys;
    public static OI oi;

    private Command autonomousCommand;
    private SendableChooser<Command> autonChooser = new SendableChooser<Command>();

    /**
     * Called once when the robot first starts up.
     */
    @Override
    public void robotInit() {
        climbSubSys = new ClimberSubsystem();
        debugSubSys = new DebugSubsystem();
        driveSubSys = new DrivetrainSubsystem();
        gearSubSys = new GearSubsystem();
        shooterSubSys = new ShooterSubsystem();
        visionSubSys = new VisionSubsystem();

        oi = new OI();

        this.autonChooser.addDefault("Do Nothing Auto", new DoNothingAuton());
        this.autonChooser.addObject("Drive 2 Seconds", new DriveTimeCommand(1.0, 2, true));
        this.autonChooser.addObject("Gear Right Auton", new GearRightAuton());
        this.autonChooser.addObject("Gear Center Auton", new GearCenterAuton());
        this.autonChooser.addObject("Gear Left Auton", new GearLeftAuton());

        // SmartDashboard.putData("Motion Magic SRX", new
        // DriveMotionMagicCommand(100));

        SmartDashboard.putData("Auto Mode", this.autonChooser);
    }

    /**
     * Called once when the robot is disabled.
     */
    @Override
    public void disabledInit() {
        debugSubSys.stopLog();

        // Stop the shooter and indexer (backup)
        shooterSubSys.index(0);
        shooterSubSys.setSetpoint(0);
    }

    /**
     * Called every 20ms when the robot is disabled.
     */
    @Override
    public void disabledPeriodic() {

    }

    /**
     * Called once at the start of autonomous mode.
     */
    @Override
    public void autonomousInit() {
        visionSubSys.refreshGear();
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
    }

    /**
     * Called every 20ms during tele-op mode.
     */
    @Override
    public void teleopPeriodic() {
        if (RobotMap.DEBUG) {
            SmartDashboard.putNumber("Ultrasonic", Robot.visionSubSys.getDistance());
        }
        Scheduler.getInstance().run();
    }

    /**
     * Called every 20ms during testing mode.
     */
    @Override
    public void testPeriodic() {
        LiveWindow.run();
    }

}
