package org.usfirst.frc.team1923.robot.subsystems;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team1923.robot.Robot;
import org.usfirst.frc.team1923.robot.RobotMap;
import org.usfirst.frc.team1923.robot.utils.GripPipeline;

import edu.wpi.cscore.AxisCamera;
import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.Ultrasonic.Unit;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class VisionSubsystem extends Subsystem {

    public double gearCenterx, gearTurn, shooterCenterx, shooterTurn;
    public double[] gearWidtharr;
    public double gearWidth, shooterArea;
    public double dist;

    public boolean found;

    private double sumx, sumw;

    public double contourX;

    public Ultrasonic frontSonar;

    Mat source = new Mat();
    Mat output = new Mat();
    CvSink gearSink;
    CvSource gearOutputStream;
    GripPipeline pipe;
    Rect r;

    /**
     * Initializes CameraServer and NetworkTables
     */
    public VisionSubsystem() {
        try {
            frontSonar = new Ultrasonic(RobotMap.FRONT_SONAR_PING_PORT, RobotMap.FRONT_SONAR_ECHO_PORT, Unit.kInches);
            frontSonar.setEnabled(true);
            frontSonar.setAutomaticMode(true);
            dist = frontSonar.getRangeInches();

            found = false;

            AxisCamera gearCamera = CameraServer.getInstance().addAxisCamera("Gear Cam", RobotMap.GEAR_CAMERA_IP);
            gearCamera.setResolution(320, 240);

            gearSink = CameraServer.getInstance().getVideo("Gear Cam");

            gearOutputStream = CameraServer.getInstance().putVideo("Gear Processed", 320, 240);

            SmartDashboard.putNumber("Power", 0);

            pipe = new GripPipeline();

            refreshGear();

        } catch (Exception e) {
            System.out.println("Exception was thrown: " + e);
        }
    }

    public void refreshGear() {
        // TODO: Move refresh method to a separate command to get automatic
        // multi-threading
        try {
            // Process Image
            try {
                gearSink.grabFrameNoTimeout(source);

                pipe.process(source);
                sumx = 0;
                sumw = 0;
                if (!pipe.filterContoursOutput().isEmpty()) {
                    // Find sum of center x and width of contours
                    // TODO: Add ranking system for each contour
                    for (MatOfPoint a : pipe.filterContoursOutput()) {
                        r = Imgproc.boundingRect(a);
                        contourX = r.x + (r.width / 2);
                        sumx += contourX;
                        sumw += r.width;
                    }
                } else {
                    gearWidth = Integer.MAX_VALUE;
                    gearCenterx = Integer.MIN_VALUE;
                }
                gearOutputStream.putFrame(pipe.hslThresholdOutput());

            } catch (UnsatisfiedLinkError b) {
                System.out.println("Unsatisfied Link Error");
                Robot.debug.logData("Unsatisfied Link Error!!");
            }

            // Extrapolate Values (Turn, distance, etc.)
            dist = frontSonar.getRangeInches();
            if (!pipe.filterContoursOutput().isEmpty()) {
                gearWidth = sumw / pipe.filterContoursOutput().size();
                // center x is pixel value of the middle of the peg
                gearCenterx = sumx / pipe.filterContoursOutput().size();
            }
            // Add 4 to make sure we don't hit the center of the gear
            gearTurn = gearCenterx - RobotMap.IMG_WIDTH / 2 + 4;
            gearTurn /= RobotMap.TURN_CONSTANT;
            // Check Boundaries of turn
            if (gearTurn < -1)
                gearTurn = -1;
            else if (gearTurn > 1)
                gearTurn = 1;

            // Make sure if no contours are seen the robot will not move
            if (pipe.filterContoursOutput().isEmpty())
                gearTurn = Integer.MIN_VALUE;

            // Logging
            SmartDashboard.putNumber("Center X Gear: ", gearCenterx);
            SmartDashboard.putNumber("Distance to target(Ultrasonic): ", dist);
            SmartDashboard.putNumber("Gear Width: ", gearWidth);
            SmartDashboard.putNumber("Gear Turn: ", gearTurn);

        } catch (Exception e) {
            System.out.println("Exception was thrown: " + e);
        }

    }

    @Override
    public void initDefaultCommand() {
        // Uncomment for continuous Processing
        // setDefaultCommand(new VisionProcessing());
    }

    public Ultrasonic getUltrasonic() {
        return frontSonar;
    }

    public double getCenterX() {
        return gearCenterx;
    }

    public double getDistance() {
        return dist;
    }

    public double getWidth() {
        return gearWidth;
    }

    public boolean isFound() {
        return gearCenterx >= 0;
    }

    public double getTurn() {
        return gearTurn;
    }

}
