package org.usfirst.frc.team1923.robot.subsystems;

import org.usfirst.frc.team1923.robot.RobotMap;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class ShooterSubsystem extends Subsystem {

    private final double P_CONSTANT = 0;
    private final double I_CONSTANT = 0;
    private final double D_CONSTANT = 0;
    private final double F_CONSTANT = 0.7 * 1023 / 18000 / 60 / 10 * 4096;
    private CANTalon shooter;
    private CANTalon indexer;
    private double speed;

    public final double allowableError = 100;

    public ShooterSubsystem() {
        this.shooter = new CANTalon(RobotMap.SHOOTER_PORT);
        this.indexer = new CANTalon(RobotMap.INDEXER_PORT);

        this.shooter.configPeakOutputVoltage(12, -12);
        this.shooter.configNominalOutputVoltage(0, 0);

        this.shooter.setP(P_CONSTANT);
        this.shooter.setI(I_CONSTANT);
        this.shooter.setD(D_CONSTANT);
        this.shooter.setF(F_CONSTANT);

        this.shooter.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
        this.shooter.setCurrentLimit(40);
        // this.shooter.SetVelocityMeasurementPeriod(VelocityMeasurementPeriod.Period_1Ms);
        this.shooter.reverseSensor(false);
        this.shooter.reverseOutput(false);

    }

    public void set(double power) {
        shooter.changeControlMode(TalonControlMode.PercentVbus);
        shooter.set(power);
        speed = shooter.getSpeed();
        SmartDashboard.putNumber("Shooter Encoder Speed", speed);
    }

    public void setSetpoint(double setpoint) {
        shooter.changeControlMode(TalonControlMode.Speed);
        speed = shooter.getSpeed();
        SmartDashboard.putNumber("Shooter Encoder Speed", speed);
        shooter.setSetpoint(setpoint);
    }

    public void index(double speed) {
        indexer.set(speed);
    }

    @Override
    public void initDefaultCommand() {
        // setDefaultCommand(new ShooterCalibrateCommand());
    }

    public double getSpeed() {
        speed = shooter.getSpeed();
        SmartDashboard.putNumber("Shooter Encoder Speed", speed);
        return speed;
    }

    public double getError() {
        return shooter.getError();
    }
}
