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
 * TODO: Formatting & Testing
 */
public class VisionSubsystem extends Subsystem {

    private double gearCenterX, gearTurn, gearWidth;
    private boolean gearFound;

    private CvSink gearCvSink;
    private CvSource gearOutputStream;
    private GripPipeline pipeline;
    private Mat source = new Mat();
    private Ultrasonic frontSonar;

    /**
     * Initializes CameraServer and NetworkTables
     */
    public VisionSubsystem() {
        try {
            this.frontSonar = new Ultrasonic(RobotMap.FRONT_SONAR_PING_PORT, RobotMap.FRONT_SONAR_ECHO_PORT, Unit.kInches);
            this.frontSonar.setEnabled(true);
            this.frontSonar.setAutomaticMode(true);

            this.gearFound = false;

            AxisCamera gearCamera = CameraServer.getInstance().addAxisCamera("Gear Camera", RobotMap.GEAR_CAMERA_IP);
            gearCamera.setResolution(RobotMap.IMG_WIDTH, RobotMap.IMG_HEIGHT);

            this.gearCvSink = CameraServer.getInstance().getVideo("Gear Camera");
            this.gearOutputStream = CameraServer.getInstance().putVideo("Gear Processed Feed", RobotMap.IMG_WIDTH, RobotMap.IMG_HEIGHT);

            SmartDashboard.putNumber("Power", 0);

            this.pipeline = new GripPipeline();

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
                this.gearCvSink.grabFrameNoTimeout(this.source);
                this.pipeline.process(this.source);

                if (!this.pipeline.filterContoursOutput().isEmpty()) {
                    // Find sum of center x and width of contours
                    // TODO: Add ranking system for each contour
                    double sumX = 0, sumW = 0;

                    for (MatOfPoint a : this.pipeline.filterContoursOutput()) {
                        Rect rect = Imgproc.boundingRect(a);
                        sumX += rect.x + rect.width / 2;
                        sumW += rect.width;
                    }

                    this.gearCenterX = sumX / this.pipeline.filterContoursOutput().size();
                    this.gearWidth = sumW / this.pipeline.filterContoursOutput().size();
                } else {
                    this.gearWidth = Integer.MAX_VALUE;
                    this.gearCenterX = Integer.MIN_VALUE;
                }

                this.gearOutputStream.putFrame(this.pipeline.hslThresholdOutput());
            } catch (UnsatisfiedLinkError b) {
                System.out.println("Unsatisfied Link Error");
                Robot.debugSubSys.logData("Unsatisfied Link Error!");
            }

            // Extrapolate Values (Turn, distance, etc.)

            // Add 4 to make sure we don't hit the center of the gear
            this.gearTurn = this.gearCenterX - RobotMap.IMG_WIDTH / 2 + 4;
            this.gearTurn /= RobotMap.TURN_CONSTANT;
            // Check Boundaries of turn
            if (this.gearTurn < -1) {
                this.gearTurn = -1;
            } else if (this.gearTurn > 1) {
                this.gearTurn = 1;
            }

            // Make sure if no contours are seen the robot will not move
            if (this.pipeline.filterContoursOutput().isEmpty()) {
                this.gearTurn = Integer.MIN_VALUE;
            }

            // Logging
            SmartDashboard.putNumber("Center X Gear: ", this.gearCenterX);
            SmartDashboard.putNumber("Distance to target(Ultrasonic): ", getDistance());
            SmartDashboard.putNumber("Gear Width: ", this.gearWidth);
            SmartDashboard.putNumber("Gear Turn: ", this.gearTurn);
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
        return this.frontSonar;
    }

    public double getGearCenterX() {
        return this.gearCenterX;
    }

    public double getDistance() {
        return this.frontSonar.getRangeInches();
    }

    public double getGearWidth() {
        return this.gearWidth;
    }

    public double getGearTurn() {
        return this.gearTurn;
    }

    public boolean isGearFound() {
        return this.gearFound;
    }

    public void setGearFound(boolean gearFound) {
        this.gearFound = gearFound;
    }

}
