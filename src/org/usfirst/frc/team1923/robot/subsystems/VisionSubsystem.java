package org.usfirst.frc.team1923.robot.subsystems;

import org.usfirst.frc.team1923.robot.RobotMap;
import org.usfirst.frc.team1923.robot.commands.vision.VisionProcessing;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.Ultrasonic.Unit;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class VisionSubsystem extends Subsystem {

    private final double[] EMPTY_ARRAY = new double[0];

    private NetworkTable table;
    private Ultrasonic frontSonar;

    private double centerX, turn;
    private double width;
    private double distance;
    private double alignmentOffset;
    private boolean found;

    /**
     * Initializes CameraServer and NetworkTables
     */
    public VisionSubsystem() {
        // TODO: Implement Bounding Rectangle
        // TODO: Implement Controller Vibration when Match time is getting low
        // TODO: Account for difference in areas of tape to change turn value
        // TODO: Add ultrasonic sensors

        CameraServer.getInstance().addAxisCamera(RobotMap.CAMERA_IP);

        this.table = NetworkTable.getTable(RobotMap.NEWTORK_TABLE_ADDRESS);
        this.frontSonar = new Ultrasonic(RobotMap.FRONT_SONAR_PING_PORT, RobotMap.FRONT_SONAR_ECHO_PORT, Unit.kInches);
        this.frontSonar.setEnabled(true);
        this.frontSonar.setAutomaticMode(true);

        this.distance = this.frontSonar.getRangeInches();
        this.found = false;
        this.alignmentOffset = 13;

        refresh();
    }

    public double getCenterX() {
        return this.centerX;
    }

    public double getTurn() {
        return this.turn;
    }

    public double getWidth() {
        return this.width;
    }

    public double getDistance() {
        return this.distance;
    }

    public double getAlignmentOffset() {
        return this.alignmentOffset;
    }

    public Ultrasonic getUltrasonic() {
        return this.frontSonar;
    }

    public boolean isFound() {
        return this.found;
    }

    public void setAlignmentOffset(double alignmentOffset) {
        this.alignmentOffset = alignmentOffset;
    }

    public void setFound(boolean found) {
        this.found = found;
    }

    public void refresh() {
        this.distance = this.frontSonar.getRangeInches();

        double[] centerXs = table.getNumberArray("centerX", EMPTY_ARRAY);
        double[] widths = table.getNumberArray("width", EMPTY_ARRAY);

        double sum = 0;

        for (double width : widths) {
            sum += width;
        }
        this.width = widths.length > 0 ? sum / widths.length : Integer.MAX_VALUE;

        sum = 0;
        for (double centerX : centerXs) {
            sum += centerX;
        }
        this.centerX = centerXs.length > 0 ? sum / centerXs.length : Integer.MIN_VALUE;

        this.turn = this.centerX - RobotMap.IMG_WIDTH / 2 + this.alignmentOffset;
        this.turn /= RobotMap.TURN_CONSTANT;

        this.turn = this.turn < -1 ? -1 : this.turn;
        this.turn = this.turn > 1 ? 1 : this.turn;
        this.turn = centerXs.length == 0 ? Integer.MIN_VALUE : this.turn;

        // TODO: Use PID to get to turn value and use an angle instead of turn
        // (Using IMU)

        SmartDashboard.putNumber("Center X: ", this.centerX);
        SmartDashboard.putNumber("Distance to target: ", this.distance);
        SmartDashboard.putNumber("Width: ", this.width);
        SmartDashboard.putNumber("Turn: ", this.width);
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new VisionProcessing());
    }

}
