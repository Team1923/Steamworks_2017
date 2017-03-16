package org.usfirst.frc.team1923.robot.commands.drive;

import org.usfirst.frc.team1923.robot.Robot;
import org.usfirst.frc.team1923.robot.utils.PIDController;

import com.ctre.CANTalon.TalonControlMode;
import com.ctre.PigeonImu;
import com.ctre.PigeonImu.FusionStatus;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;

public class GyroTurnCommand extends Command {

    // TODO: Tune PID constants
    private final double P_CONST = 0.0070;
    private final double I_CONST = 0.0018;
    private final double D_CONST = 0.0000;
    private final double I_ZONE = 5;

    // Absolute turning tolerance in degrees
    private final double TOLERANCE = 5;

    private ImuTarget target;
    private Drivetrain output;
    private PIDController controller;

    private double degrees;

    public GyroTurnCommand(double degrees) {
        requires(Robot.driveSubSys);

        // Ensure that the degrees is [-360, 360] for the PID controller
        this.degrees = degrees % 360;

        this.target = new ImuTarget();
        this.output = new Drivetrain();
        this.controller = new PIDController(P_CONST, I_CONST, D_CONST, this.target, this.output);
        this.controller.setContinuous(true);
        this.controller.setAbsoluteTolerance(TOLERANCE);
        this.controller.setOutputRange(-1, 1);
        this.controller.setInputRange(-360, 360);
        this.controller.setSetpoint(this.degrees);
        this.controller.setIZone(I_ZONE);

        this.setTimeout(Math.abs(this.degrees) * 0.005 + 1);
    }

    @Override
    protected void initialize() {
        this.controller.enable();
    }

    @Override
    protected void execute() {
        // Debug
        double currentAngle = this.target.pidGet();
        System.out.println("target = " + this.degrees + ", current = " + currentAngle + ", error = " + (this.degrees - currentAngle));
    }

    @Override
    protected boolean isFinished() {
        return this.controller.onTarget() || this.isTimedOut();
    }

    @Override
    protected void end() {
        this.controller.disable();

        this.controller = null;
        this.target = null;
        this.output = null;
    }

    @Override
    protected void interrupted() {
        this.end();
    }

    public class ImuTarget implements PIDSource {

        private PigeonImu imu;
        private FusionStatus fusionStatus;

        private double startingAngle;

        public ImuTarget() {
            this.imu = Robot.driveSubSys.getImu();
            this.fusionStatus = new FusionStatus();

            this.startingAngle = this.getHeading();
        }

        @Override
        public void setPIDSourceType(PIDSourceType pidSource) {

        }

        @Override
        public PIDSourceType getPIDSourceType() {
            return PIDSourceType.kDisplacement;
        }

        @Override
        public double pidGet() {
            return this.startingAngle - this.getHeading();
        }

        protected double getHeading() {
            return this.imu.GetFusedHeading(this.fusionStatus);
        }

    }

    public class Drivetrain implements PIDOutput {

        @Override
        public void pidWrite(double output) {
            Robot.driveSubSys.drive(output, -output, TalonControlMode.PercentVbus);
        }

    }

}
