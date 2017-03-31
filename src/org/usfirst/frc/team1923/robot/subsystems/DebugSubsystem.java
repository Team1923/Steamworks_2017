package org.usfirst.frc.team1923.robot.subsystems;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;

import org.usfirst.frc.team1923.robot.Robot;
import org.usfirst.frc.team1923.robot.utils.debug.LogDataCommand;

import com.ctre.PigeonImu;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DebugSubsystem extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    private static PrintWriter logger;
    private long lastLog;
    private int refresh_time;
    private String filePath;

    /**
     * Creates a subsystem that logs all custom data that can not be reached on
     * the console log of the driverstation
     */
    public DebugSubsystem() {

        filePath = "/home/lvuser/" + System.currentTimeMillis() % 10000 + "match.log";

        lastLog = 0;
        this.refresh_time = 100;
    }

    /**
     * Flush the buffer in PrintWriter to the disk and close the logger.
     */
    public void stopLog() {
        if (logger != null) {
            logger.println("Log ending");
            logger.close();

            // try {
            // Runtime.getRuntime().exec("chmod 777 " + filePath);
            // } catch (IOException e) {
            // e.printStackTrace();
            // }
        }
    }

    /**
     * Log critical sensor data into a file.
     */
    public void logData(String event_message) {
        if (logger == null) {
            try {
                logger = new PrintWriter(new BufferedWriter(new FileWriter(filePath, true)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (this.lastLog + refresh_time > System.currentTimeMillis()) {
            return;
        }
        this.lastLog = System.currentTimeMillis();

        String control_mode = "";
        if (Robot.dstation.isAutonomous()) {
            control_mode = "Auto";
        } else if (Robot.dstation.isOperatorControl()) {
            control_mode = "Tele";
        } else if (Robot.dstation.isDisabled()) {
            control_mode = "Disabled";
        }

        String message = String.format(
                "[%f %s] Brown out: %s, Front Ultra: %f, CenterX: %f, Vision Found: %b, Target Wdith: %f, Calc Turn: %f, IMU Heading: %f, Left Enc: %f, Right Enc: %f\n",
                DriverStation.getInstance().getMatchTime(), control_mode, Robot.dstation.isBrownedOut(), Robot.visionSubSys.getDistance(),
                Robot.visionSubSys.getCenterX(), Robot.visionSubSys.isFound(), Robot.visionSubSys.getWidth(), Robot.visionSubSys.getTurn(),
                Robot.driveSubSys.getImu().GetFusedHeading(new PigeonImu.FusionStatus()), Robot.driveSubSys.getLeftPosition(),
                Robot.driveSubSys.getRightPosition());
        message += event_message;
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
