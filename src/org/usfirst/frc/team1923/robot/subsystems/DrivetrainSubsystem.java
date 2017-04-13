package org.usfirst.frc.team1923.robot.subsystems;

import org.usfirst.frc.team1923.robot.RobotMap;
import org.usfirst.frc.team1923.robot.commands.drive.RawDriveCommand;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;
import com.ctre.PigeonImu;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Class that houses the motors and shifters
 */
public class DrivetrainSubsystem extends Subsystem {

    public final int ALLOWABLE_ERROR = 300;

    private final double P_CONSTANT = 0.4;
    private final double I_CONSTANT = 0.0001;
    private final double D_CONSTANT = 0;
    private final double F_CONSTANT = 0;
    private final boolean LEFT_REVERSED = true;
    private final boolean RIGHT_REVERSED = false;
    private final int MAX_SAFE_SHIFT_SPEED = 100; // RPM

    // TODO: Change wheel diameter and drive base width
    private static final double WHEEL_DIAMETER = 4;
    // Every turn of the encoder equals DRIVE_RATIO turns of the wheel
    private static final double DRIVE_RATIO = 32.5 / 50.0;
    // Middle of wheels measurement in inches
    private static final double DRIVE_BASE_WIDTH = 22.5;
    private static final double DRIVE_CONSTANT = 1;
    private static final double TURNING_CONSTANT = 1.12;

    // Arrays of talons to group them together
    // The first element will always be the master Talon, the subsequent ones
    // will follow
    private CANTalon[] leftTalons, rightTalons;
    private DoubleSolenoid shifter;
    private DoubleSolenoid shiftOmnis;
    private PigeonImu imu;

    public DrivetrainSubsystem() {
        this.leftTalons = new CANTalon[RobotMap.LEFT_DRIVE_PORTS.length];
        this.rightTalons = new CANTalon[RobotMap.RIGHT_DRIVE_PORTS.length];

        this.imu = new PigeonImu(new CANTalon((RobotMap.IMU_PORT)));

        for (int i = 0; i < RobotMap.LEFT_DRIVE_PORTS.length; i++) {
            this.leftTalons[i] = new CANTalon(RobotMap.LEFT_DRIVE_PORTS[i]);
        }

        for (int i = 0; i < RobotMap.RIGHT_DRIVE_PORTS.length; i++) {
            this.rightTalons[i] = new CANTalon(RobotMap.RIGHT_DRIVE_PORTS[i]);
        }

        this.shifter = new DoubleSolenoid(RobotMap.PCM_MODULE_NUM, RobotMap.SHIFT_FORWARD_PORT, RobotMap.SHIFT_BACKWARD_PORT);
        this.shiftOmnis = new DoubleSolenoid(RobotMap.PCM_MODULE_NUM, RobotMap.OMNI_FORWARD_PORT, RobotMap.OMNI_BACKWARD_PORT);

        setToFollow();
        setReverse();
        // configPID();
    }

    private void setToFollow() {
        for (int i = 1; i < this.leftTalons.length; i++) {
            this.leftTalons[i].changeControlMode(TalonControlMode.Follower);
            this.leftTalons[i].set(this.leftTalons[0].getDeviceID());
        }

        for (int i = 1; i < this.rightTalons.length; i++) {
            this.rightTalons[i].changeControlMode(TalonControlMode.Follower);
            this.rightTalons[i].set(this.rightTalons[0].getDeviceID());
        }
    }

    /**
     * Sets the two master talons to a certain mode
     * 
     * @param controlMode
     *            TalonControlMode to be used
     */
    private void setMasterToMode(TalonControlMode controlMode) {
        this.leftTalons[0].changeControlMode(controlMode);
        this.rightTalons[0].changeControlMode(controlMode);
    }

    /**
     * Configures the PID values of the two master talons Sets the nominal and
     * peak output voltages and sets the sensor and output reversal flags
     */
    private void configPID() {
        this.leftTalons[0].setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
        this.leftTalons[0].reverseSensor(LEFT_REVERSED);
        this.leftTalons[0].configNominalOutputVoltage(0, 0);
        this.leftTalons[0].configPeakOutputVoltage(12, -12);

        this.rightTalons[0].setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
        this.rightTalons[0].reverseSensor(RIGHT_REVERSED);
        this.rightTalons[0].configNominalOutputVoltage(0, 0);
        this.rightTalons[0].configPeakOutputVoltage(12, -12);

        this.leftTalons[0].setProfile(0);
        this.leftTalons[0].setF(F_CONSTANT);
        this.leftTalons[0].setP(P_CONSTANT);
        this.leftTalons[0].setI(I_CONSTANT);
        this.leftTalons[0].setD(D_CONSTANT);
        this.leftTalons[0].setIZone(10000);
        this.leftTalons[0].setAllowableClosedLoopErr(ALLOWABLE_ERROR);

        this.rightTalons[0].setProfile(0);
        this.rightTalons[0].setF(F_CONSTANT);
        this.rightTalons[0].setP(P_CONSTANT);
        this.rightTalons[0].setI(I_CONSTANT);
        this.rightTalons[0].setD(D_CONSTANT);
        this.rightTalons[0].setIZone(10000);
        this.rightTalons[0].setAllowableClosedLoopErr(ALLOWABLE_ERROR);

        setMasterToMode(TalonControlMode.PercentVbus);
        setReverse();
    }

    public void setReverse() {

        this.leftTalons[0].set(0.0);
        this.leftTalons[0].reverseOutput(LEFT_REVERSED);
        this.leftTalons[0].setInverted(LEFT_REVERSED);

        this.rightTalons[0].set(0.0);
        this.rightTalons[0].reverseOutput(RIGHT_REVERSED);
        this.rightTalons[0].setInverted(RIGHT_REVERSED);
    }

    /**
     * Directly sets the input value of the motors. Use with caution because it
     * doesn't automatically check the control mode
     * 
     * @param left
     *            Left power
     * @param right
     *            Right power
     */
    private void set(double left, double right) {
        this.leftTalons[0].set(left);
        this.rightTalons[0].set(right);
    }

    /**
     * Disables the closed-loop system and allows direct power setting.
     */
    public void disable() {
        setMasterToMode(TalonControlMode.Disabled);
    }

    public void disableControl() {
        this.leftTalons[0].disableControl();
        this.rightTalons[0].disableControl();
    }

    public void enable() {
        setMasterToMode(TalonControlMode.PercentVbus);
    }

    /**
     * Sets talon powers with a specific mode
     * 
     * @param left
     *            Left power
     * @param right
     *            Right power
     * @param controlMode
     *            TalonControlMode to be used
     */
    public void drive(double left, double right, TalonControlMode controlMode) {
        if (this.leftTalons[0].getControlMode() != controlMode || this.rightTalons[0].getControlMode() != controlMode) {
            setMasterToMode(controlMode);
        }

        set(left, right);
    }

    public void auto(double pow, double turn) {
        set(pow + turn, pow - turn);
    }

    /**
     * Resets current position of the encoders. Since SRX Mag encoders are used
     * in relative mode, this allows us to simplify autons by resetting the home
     * position of the robot.
     */
    public void resetPosition() {
        this.leftTalons[0].setPosition(0);
        this.rightTalons[0].setPosition(0);
    }

    private double max(double[] a) {
        double max = a[0];
        for (double b : a) {
            if (b > max)
                max = b;
        }
        return max;
    }

    public double getLeftPosition() {
        return this.leftTalons[0].getPosition();
    }

    public double getRightPosition() {
        return this.rightTalons[0].getPosition();
    }

    public int getLeftEncPosition() {
        return -this.leftTalons[0].getEncPosition();
    }

    public int getRightEncPosition() {
        return this.rightTalons[0].getEncPosition();
    }

    public double getLeftSpeed() {
        return this.leftTalons[0].getSpeed();
    }

    public double getRightSpeed() {
        return this.rightTalons[0].getSpeed();
    }

    public double getLeftTarget() {
        return this.leftTalons[0].get();
    }

    public double getRightTarget() {
        return this.rightTalons[0].get();
    }

    public double getLeftError() {
        return this.leftTalons[0].getError();
    }

    public double getRightError() {
        return this.rightTalons[0].getError();
    }

    public void shiftUp() {
        if (this.shifter.get() != Value.kReverse) {
            this.shifter.set(Value.kReverse);
        }
    }

    public void shiftDown() {
        if (safeToShift()) {
            this.shifter.set(Value.kForward);
        }
    }

    public void shiftUpOmnis() {
        if (this.shiftOmnis.get() != Value.kForward) {
            this.shiftOmnis.set(Value.kForward);
        }
    }

    public void shiftDownOmnis() {
        if (this.shiftOmnis.get() != Value.kReverse) {
            this.shiftOmnis.set(Value.kReverse);
        }
    }

    private boolean safeToShift() {
        // return Math.max(Math.abs(leftTalons[0].getEncVelocity()),
        // Math.abs(rightTalons[0].getEncVelocity())) < MAX_SAFE_SHIFT_SPEED;
        return true;
    }

    public PigeonImu getImu() {
        return this.imu;
    }

    public void setLeft(double power, TalonControlMode controlMode) {
        if (this.leftTalons[0].getControlMode() != controlMode) {
            this.leftTalons[0].changeControlMode(controlMode);
        }

        this.leftTalons[0].set(power);
    }

    public void setRight(double power, TalonControlMode controlMode) {
        if (this.rightTalons[0].getControlMode() != controlMode) {
            this.rightTalons[0].changeControlMode(controlMode);
        }

        this.rightTalons[0].set(power);
    }

    public void stop() {
        drive(0, 0, TalonControlMode.PercentVbus);
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new RawDriveCommand());
    }

    public static double angleToDistance(double angle) {
        return TURNING_CONSTANT * angle * Math.PI * DRIVE_BASE_WIDTH / 360;
    }

    public static double distanceToRotation(double distance) {
        return distance / (Math.PI * WHEEL_DIAMETER * DRIVE_RATIO) * DRIVE_CONSTANT;
    }

    public void configMM() {
        leftTalons[0].setMotionMagicAcceleration(500);
        rightTalons[0].setMotionMagicAcceleration(500);

        leftTalons[0].setMotionMagicCruiseVelocity(800); // TODO: Is this in
                                                         // rpm?
        rightTalons[0].setMotionMagicCruiseVelocity(800);
    }

}
