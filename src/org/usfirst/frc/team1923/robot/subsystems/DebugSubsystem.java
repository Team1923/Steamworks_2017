package org.usfirst.frc.team1923.robot.subsystems;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;

import org.usfirst.frc.team1923.robot.Robot;
import org.usfirst.frc.team1923.robot.commands.LogDataCommand;

import com.ctre.PigeonImu;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Subsystem;

public class DebugSubsystem extends Subsystem {

    private final int REFRESH_INTERVAL = 100;

    private static PrintWriter logger;

    private long lastLog;
    private String filePath;

    /**
     * Creates a subsystem that logs all custom data that can not be reached on
     * the console log of the driver station
     */
    public DebugSubsystem() {
        this.filePath = "/home/lvuser/" + System.currentTimeMillis() % 10000 + "match.log";
        this.lastLog = 0;
    }

    /**
     * Flush the buffer in PrintWriter to the disk and close the logger.
     */
    public void stopLog() {
        if (logger != null) {
            logger.println("Log ending");
            logger.close();
        }
    }

    /**
     * Log critical sensor data into a file.
     */
    public void logData(String eventMessage) {
        if (logger == null) {
            try {
                logger = new PrintWriter(new BufferedWriter(new FileWriter(this.filePath, true)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (this.lastLog + this.REFRESH_INTERVAL > System.currentTimeMillis()) {
            return;
        }

        this.lastLog = System.currentTimeMillis();

        String controlMode = "";
        DriverStation driverStation = DriverStation.getInstance();

        if (driverStation.isAutonomous()) {
            controlMode = "Auto";
        } else if (driverStation.isOperatorControl()) {
            controlMode = "Tele";
        } else if (driverStation.isDisabled()) {
            controlMode = "Disabled";
        }

        String message = String.format(
                "[%f %s] Brown out: %s, Front Ultra: %f, CenterX: %f, Vision Found: %b, Target Wdith: %f, Calc Turn: %f, IMU Heading: %f, Left Enc: %f, Right Enc: %f\n",
                DriverStation.getInstance().getMatchTime(), controlMode, driverStation.isBrownedOut(), Robot.visionSubSys.getDistance(),
                Robot.visionSubSys.getGearCenterX(), Robot.visionSubSys.isGearFound(), Robot.visionSubSys.getGearWidth(), Robot.visionSubSys.getGearTurn(),
                Robot.driveSubSys.getImu().GetFusedHeading(new PigeonImu.FusionStatus()), Robot.driveSubSys.getLeftPosition(),
                Robot.driveSubSys.getRightPosition());
        message += eventMessage;
        if (logger != null) {
            logger.println(message);
            System.out.println(message);
        } else {
            System.out.println("Logger is null");
        }
    }

    public void logData() {
        this.logData("");
    }

    @Override
    public void initDefaultCommand() {
        new LogDataCommand();
    }

}
