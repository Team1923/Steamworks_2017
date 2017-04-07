package org.usfirst.frc.team1923.robot.commands.drive;

import org.usfirst.frc.team1923.robot.Robot;
import org.usfirst.frc.team1923.robot.RobotMap;
import org.usfirst.frc.team1923.robot.subsystems.DrivetrainSubsystem;

import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveDistanceCommand extends Command {

    private double left, right;
    private double leftTarget, rightTarget;
    private double oldleft, oldright;

    /**
     * Drives distance with external PID with a set timeout
     * 
     * @param left
     *            Left target in inches
     * @param right
     *            Right target in inches
     * @param time
     *            Timeout in seconds
     */
    public DriveDistanceCommand(double left, double right, double time) {
        requires(Robot.driveSubSys);
        this.left = left;
        this.right = right;

        this.setTimeout(Math.abs(time));
    }

    /**
     * Drives distance with a calculated timeout
     * 
     * @param left
     *            Left target in inches
     * @param right
     *            Right target in inches
     */
    public DriveDistanceCommand(double left, double right) {
        this(left, right, Math.max(Math.abs(left), Math.abs(right)) * 0.05 + 2);
    }

    /**
     * Drives straight with automatic timeout calculation
     * 
     * @param distance
     *            Distance in inches
     */
    public DriveDistanceCommand(double distance) {
        this(distance, distance);
    }

    @Override
    protected void initialize() {
        if (RobotMap.DEBUG) {
            System.out.println("Initialized DriveDistanceCommand.");
        }

        this.oldleft = Robot.driveSubSys.getLeftPosition();
        this.oldright = Robot.driveSubSys.getRightPosition();

        this.leftTarget = DrivetrainSubsystem.distanceToRotation(left) + Robot.driveSubSys.getLeftPosition();
        this.rightTarget = DrivetrainSubsystem.distanceToRotation(right) + Robot.driveSubSys.getRightPosition();

        if (RobotMap.DEBUG) {
            SmartDashboard.putNumber("Left Target", this.leftTarget);
            SmartDashboard.putNumber("Right target", this.rightTarget);
        }
    }

    @Override
    protected void execute() {
        Robot.driveSubSys.drive(this.leftTarget, this.rightTarget, TalonControlMode.Position);
    }

    @Override
    protected boolean isFinished() {
        if (RobotMap.DEBUG) {
            System.out.println("Left Error: " + Robot.driveSubSys.getLeftError() + ", Right Error: " + Robot.driveSubSys.getRightError());
        }
        return isTimedOut() || ((Math.abs(Robot.driveSubSys.getLeftError()) < Robot.driveSubSys.ALLOWABLE_ERROR)
                && (Math.abs(Robot.driveSubSys.getRightError()) < Robot.driveSubSys.ALLOWABLE_ERROR)
                && Robot.driveSubSys.getLeftPosition() != this.oldleft && Robot.driveSubSys.getRightPosition() != this.oldright);
    }

    @Override
    protected void end() {
        if (RobotMap.DEBUG) {
            if (isTimedOut()) {
                System.out.println("TIMED OUT");
            }

            System.out.println("END END END");
        }
        Robot.driveSubSys.stop();
    }

    @Override
    protected void interrupted() {
        if (RobotMap.DEBUG) {
            System.out.println("INTERRUPT");
        }
        end();
    }

}
