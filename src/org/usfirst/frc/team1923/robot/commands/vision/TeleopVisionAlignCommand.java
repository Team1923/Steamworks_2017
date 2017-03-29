package org.usfirst.frc.team1923.robot.commands.vision;

import org.usfirst.frc.team1923.robot.Robot;
import org.usfirst.frc.team1923.robot.RobotMap;

// import com.sun.webkit.Timer;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This commands aligns the Robot with the peg or the feeder (depending on
 * boolean passed by constructor)
 * 
 * @author Abhinav
 */
public class TeleopVisionAlignCommand extends Command {

    public double power, turn;
    boolean feeder = false;
    public double dist;
    // private Timer time;

    public TeleopVisionAlignCommand() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.visionSubSys);
        requires(Robot.driveSubSys);
    }

    public TeleopVisionAlignCommand(boolean feeder) {
        this.feeder = feeder;
        this.dist = (!feeder ? RobotMap.PEG_DIST : RobotMap.FEEDER_DIST);
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
        if (Robot.visionSubSys.dist >= dist) {
            if (Robot.visionSubSys.turn < -1) {
                power = 0;
                turn = 0;
                Robot.visionSubSys.found = false;
            } else {
                if (Robot.visionSubSys.dist >= 30)
                    power = 0.4;
                else power = 0.2;
                // power=0;
                Robot.visionSubSys.found = true;
                turn = Robot.visionSubSys.turn;
            }

            Robot.driveSubSys.auto(power, turn);
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return Robot.visionSubSys.dist <= dist;
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
    }
}
