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

    public double centerx, turn;
    public double width;
    public double dist;

    public boolean found;

    private double sumx;
    private double sumw;

    public double contourX;

    public Ultrasonic frontSonar;

    private Mat source = new Mat();
    private CvSink cvSink;
    private CvSource outputStream;
    private GripPipeline pipe;
    private Rect r;

    /**
     * Initializes CameraServer and NetworkTables
     */
    public VisionSubsystem() {
        try {
            this.frontSonar = new Ultrasonic(RobotMap.FRONT_SONAR_PING_PORT, RobotMap.FRONT_SONAR_ECHO_PORT, Unit.kInches);
            this.frontSonar.setEnabled(true);
            this.frontSonar.setAutomaticMode(true);
            this.dist = this.frontSonar.getRangeInches();

            this.found = false;

            AxisCamera camera = CameraServer.getInstance().addAxisCamera("Axis Cam", RobotMap.CAMERA_IP);
            camera.setResolution(320, 240);

            this.cvSink = CameraServer.getInstance().getVideo();
            this.outputStream = CameraServer.getInstance().putVideo("Processed", 320, 240);

            SmartDashboard.putNumber("Power", 0);

            this.pipe = new GripPipeline();

            refresh();
        } catch (Exception e) {
            System.out.println("Exception was thrown: " + e);
        }
    }

    public void refresh() {
        // TODO: Move refresh method to a separate command to get automatic
        // multi-threading
        try {
            // Process Image
            try {
                this.cvSink.grabFrameNoTimeout(this.source);

                this.pipe.process(this.source);
                this.sumx = 0;
                this.sumw = 0;
                if (!this.pipe.filterContoursOutput().isEmpty()) {
                    // Find sum of center x and width of contours
                    // TODO: Add ranking system for each contour
                    for (MatOfPoint a : this.pipe.filterContoursOutput()) {
                        this.r = Imgproc.boundingRect(a);
                        this.contourX = this.r.x + this.r.width / 2;
                        this.sumx += this.contourX;
                        this.sumw += this.r.width;
                    }
                } else {
                    this.width = Integer.MAX_VALUE;
                    this.centerx = Integer.MIN_VALUE;
                }
                this.outputStream.putFrame(this.pipe.hslThresholdOutput());
            } catch (UnsatisfiedLinkError b) {
                System.out.println("Unsatisfied Link Error");
                Robot.debugSubSys.logData("Unsatisfied Link Error!!");
            }

            // Extrapolate Values (Turn, distance, etc.)
            this.dist = this.frontSonar.getRangeInches();
            if (!this.pipe.filterContoursOutput().isEmpty()) {
                this.width = this.sumw / this.pipe.filterContoursOutput().size();
                // center x is pixel value of the middle of the peg
                this.centerx = this.sumx / this.pipe.filterContoursOutput().size();
            }
            // Add 4 to make sure we don't hit the center of the gear
            this.turn = this.centerx - RobotMap.IMG_WIDTH / 2 + 4;
            this.turn /= RobotMap.TURN_CONSTANT;
            // Check Boundaries of turn
            if (this.turn < -1) {
                this.turn = -1;
            } else if (this.turn > 1) {
                this.turn = 1;
            }

            // Make sure if no contours are seen the robot will not move
            if (this.pipe.filterContoursOutput().isEmpty()) {
                this.turn = Integer.MIN_VALUE;
            }

            // Logging
            SmartDashboard.putNumber("Center X: ", this.centerx);
            SmartDashboard.putNumber("Distance to target(Ultrasonic): ", this.dist);
            SmartDashboard.putNumber("Width: ", this.width);
            SmartDashboard.putNumber("Turn: ", this.turn);

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

    public double getCenterX() {
        return this.centerx;
    }

    public double getDistance() {
        return this.dist;
    }

    public double getWidth() {
        return this.width;
    }

    public boolean isFound() {
        return this.centerx >= 0;
    }

    public double getTurn() {
        return this.turn;
    }

}
