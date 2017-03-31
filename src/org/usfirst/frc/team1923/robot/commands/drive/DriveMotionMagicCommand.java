package org.usfirst.frc.team1923.robot.commands.drive;

import org.usfirst.frc.team1923.robot.Robot;
import org.usfirst.frc.team1923.robot.subsystems.DrivetrainSubsystem;

import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveMotionMagicCommand extends Command {

    private double dist;
    private double target;

    public DriveMotionMagicCommand(double dist) {
        this.dist = dist;
        requires(Robot.driveSubSys);
        SmartDashboard.putNumber("Dist to run", 0);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        this.dist = SmartDashboard.getNumber("Dist to run", 0);
        Robot.driveSubSys.configMM();
        Robot.driveSubSys.resetPosition();

        System.out.println("Starting sleep");
        try {
            Thread.sleep(100);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Ending sleep");

        target = DrivetrainSubsystem.distanceToRotation(this.dist);
        System.out.println(target);
        Robot.driveSubSys.drive(target, target, TalonControlMode.MotionMagic);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        System.out.println("Left Error: " + Robot.driveSubSys.getLeftError() + ", Right Error: " + Robot.driveSubSys.getRightError());

        Robot.driveSubSys.drive(target, target, TalonControlMode.MotionMagic);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        System.out.println(Robot.driveSubSys.getLeftPosition());
        Robot.driveSubSys.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        this.end();
    }
}
