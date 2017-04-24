package org.usfirst.frc.team1923.robot.subsystems;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import org.usfirst.frc.team1923.robot.Robot;
import org.usfirst.frc.team1923.robot.commands.LogDataCommand;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Subsystem;

public class DebugSubsystem extends Subsystem {

    private final int REFRESH_INTERVAL = 250;

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
            createLogger();
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

        double matchTime = driverStation.getMatchTime() == -1 ? System.currentTimeMillis() : driverStation.getMatchTime();

        String message = String.format(
                "[%f %s] Brown out: %s, Front Ultra: %f, CenterX: %f, Vision Found: %b, Target Width: %f, Calc Turn: %f, IMU Heading: %f, Left Enc: %f, Right Enc: %f",
                matchTime, controlMode, driverStation.isBrownedOut(), Robot.visionSubSys.getDistance(), Robot.visionSubSys.getGearCenterX(),
                Robot.visionSubSys.isGearFound(), Robot.visionSubSys.getGearWidth(), Robot.visionSubSys.getGearTurn(),
                Robot.driveSubSys.getFusedHeading(), Robot.driveSubSys.getLeftPosition(), Robot.driveSubSys.getRightPosition());
        message += eventMessage.equals("") ? "" : "\n" + eventMessage;
        if (logger != null) {
            logger.println(message);
        } else {
            System.out.println("Logger is null");
        }
    }

    public void logData() {
        this.logData("");
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new LogDataCommand());
    }

    protected void createLogger() {
        try {
            File loggingDirectory = new File("/home/lvuser/logs");

            if (!loggingDirectory.exists()) {
                loggingDirectory.mkdir();
            }

            if (!loggingDirectory.isDirectory()) {
                throw new Exception("Logging directory is not a directory!");
            }

            logger = new PrintWriter(new BufferedWriter(new FileWriter("/home/lvuser/logs/" + (System.currentTimeMillis() / 1000) + ".log")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
