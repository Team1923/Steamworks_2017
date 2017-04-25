package org.usfirst.frc.team1923.robot.subsystems;

import org.usfirst.frc.team1923.robot.RobotMap;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ShooterSubsystem extends Subsystem {

    public static final double ALLOWABLE_ERROR = 100;

    private final double P_CONSTANT = 0;
    private final double I_CONSTANT = 0;
    private final double D_CONSTANT = 0;
    private final double F_CONSTANT = 0.7 * 1023 / 18000 / 60 / 10 * 4096;

    private CANTalon shooter;
    private CANTalon indexer;
    private double speed;

    public ShooterSubsystem() {
        this.shooter = new CANTalon(RobotMap.SHOOTER_PORT);
        this.indexer = new CANTalon(RobotMap.INDEXER_PORT);

        this.shooter.configPeakOutputVoltage(12, -12);
        this.shooter.configNominalOutputVoltage(0, 0);

        this.shooter.setP(this.P_CONSTANT);
        this.shooter.setI(this.I_CONSTANT);
        this.shooter.setD(this.D_CONSTANT);
        this.shooter.setF(this.F_CONSTANT);

        this.shooter.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
        this.shooter.setCurrentLimit(40);
        // this.shooter.SetVelocityMeasurementPeriod(VelocityMeasurementPeriod.Period_1Ms);
        this.shooter.reverseSensor(false);
        this.shooter.reverseOutput(false);
    }

    public void set(double power) {
        this.shooter.changeControlMode(TalonControlMode.PercentVbus);
        this.shooter.set(power);
        this.speed = this.shooter.getSpeed();
        SmartDashboard.putNumber("Shooter Encoder Speed", this.speed);
    }

    public void setSetpoint(double setpoint) {
        this.shooter.changeControlMode(TalonControlMode.Speed);
        this.speed = this.shooter.getSpeed();
        SmartDashboard.putNumber("Shooter Encoder Speed", this.speed);
        this.shooter.setSetpoint(setpoint);
    }

    public void index(double speed) {
        this.indexer.set(speed);
    }

    @Override
    public void initDefaultCommand() {
        // setDefaultCommand(new ShooterCalibrateCommand());
    }

    public double getSpeed() {
        this.speed = this.shooter.getSpeed();
        SmartDashboard.putNumber("Shooter Encoder Speed", this.speed);
        return this.speed;
    }

    public double getError() {
        return this.shooter.getError();
    }

}
