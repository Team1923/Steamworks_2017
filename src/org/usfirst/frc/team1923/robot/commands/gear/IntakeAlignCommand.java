package org.usfirst.frc.team1923.robot.commands.gear;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team1923.robot.Robot;
import org.usfirst.frc.team1923.robot.utils.PIDController;

public class IntakeAlignCommand extends Command {

    // TODO: Tune PID constants
    private final double P_CONST = 0.0070;
    private final double I_CONST = 0.0018;
    private final double D_CONST = 0.0000;
    private final double I_ZONE = 5;

    private final double TARGET = 10;
    private final double TOLERANCE = 0.5;
    private final double POWER = 11;

    private PIDController leftController;
    private PIDController rightController;
    private Drivetrain leftDrive;
    private Drivetrain rightDrive;
    private Ultrasonic leftUltrasonic;
    private Ultrasonic rightUltrasonic;

    public IntakeAlignCommand() {
        requires(Robot.driveSubSys);
        requires(Robot.visionSubSys);

        this.leftDrive = new Drivetrain(Side.LEFT);
        this.rightDrive = new Drivetrain(Side.RIGHT);

        this.leftUltrasonic = Robot.visionSubSys.getUltrasonic();
        this.rightUltrasonic = Robot.visionSubSys.getUltrasonic();

        this.leftController = new PIDController(P_CONST, I_CONST, D_CONST, this.leftUltrasonic, this.leftDrive);
        this.leftController.setContinuous(true);
        this.leftController.setAbsoluteTolerance(TOLERANCE);
        this.leftController.setOutputRange(-1, 1);
        this.leftController.setSetpoint(TARGET);
        this.leftController.setIZone(I_ZONE);

        this.rightController = new PIDController(P_CONST, I_CONST, D_CONST, this.rightUltrasonic, this.rightDrive);
        this.rightController.setContinuous(true);
        this.rightController.setAbsoluteTolerance(TOLERANCE);
        this.rightController.setOutputRange(-1, 1);
        this.rightController.setSetpoint(TARGET);
        this.rightController.setIZone(I_ZONE);

        this.setTimeout(10);
    }

    @Override
    protected void initialize() {
        this.leftController.enable();
        this.rightController.enable();
    }

    @Override
    protected void execute() {

    }

    @Override
    protected boolean isFinished() {
        return (this.leftController.onTarget() && this.rightController.onTarget()) || this.isTimedOut();
    }

    @Override
    protected void end() {
        this.leftController.disable();
        this.rightController.disable();
    }

    @Override
    protected void interrupted() {
        this.end();
    }


    public class Drivetrain implements PIDOutput {

        private Side side;

        public Drivetrain(Side side) {
            this.side = side;
        }

        @Override
        public void pidWrite(double output) {
            if (this.side == Side.LEFT) {
                Robot.driveSubSys.setLeft(output * POWER, CANTalon.TalonControlMode.Voltage);
            } else {
                Robot.driveSubSys.setRight(output * POWER, CANTalon.TalonControlMode.Voltage);
            }
        }

    }

    public enum Side {
        LEFT, RIGHT
    }

}