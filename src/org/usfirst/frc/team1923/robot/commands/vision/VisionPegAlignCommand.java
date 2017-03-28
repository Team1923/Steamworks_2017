package org.usfirst.frc.team1923.robot.commands.vision;

import org.usfirst.frc.team1923.robot.Robot;
import org.usfirst.frc.team1923.robot.RobotMap;

// import com.sun.webkit.Timer;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class VisionPegAlignCommand extends Command {

    public double power, turn;
    // public boolean found;
    public boolean aligned;
    // private Timer time;

    public VisionPegAlignCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(Robot.visionSubSys);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        // time = Timer.getTimer();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        // TODO: Change power value to account for distance
        // TODO: Take into account values from ultrasonic sensors
        // new VisionAlignCommand(); TODO: Change to only run when needed to not
        // waste processor cycles
        Robot.visionSubSys.refresh();
        if (Robot.visionSubSys.turn < -1) {
            power = 0;
            turn = 0;
            Robot.visionSubSys.found = false;
        } else {
            if (Robot.visionSubSys.dist >= 30)
                power = 0.45;
            else power = 0.2;
            // power=0;
            Robot.visionSubSys.found = true;
            turn = Robot.visionSubSys.turn;
        }

        SmartDashboard.putNumber("Power", power);

        // Testing
        // System.out.println("Power: " + power + " Turn: " + turn);

        Robot.driveSubSys.auto(power, turn);
        if (Robot.visionSubSys.dist <= RobotMap.PEG_DIST)
            aligned = true;
        else aligned = false;

        SmartDashboard.putBoolean("Found: ", Robot.visionSubSys.found);
        SmartDashboard.putBoolean("Aligned and Ready to Drop: ", aligned);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        if (Robot.visionSubSys.dist < RobotMap.PEG_DIST)
            return true;
        else return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.driveSubSys.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        System.out.println("Interrupted!!!!!!!");
        end();
    }
}
