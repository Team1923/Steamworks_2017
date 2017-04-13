package org.usfirst.frc.team1923.robot.commands.vision;

import org.usfirst.frc.team1923.robot.Robot;
import org.usfirst.frc.team1923.robot.RobotMap;

// import com.sun.webkit.Timer;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This commands aligns the Robot with the peg or the feeder (depending on
 * boolean passed by constructor)
 * 
 * @author Abhinav
 */

public class VisionAlignCommand extends Command {

    public double power, turn;
    boolean feeder = false, aligned;
    public double stoppingDist;

    public VisionAlignCommand() {
        this(false);
    }

    public VisionAlignCommand(boolean feeder) {
        this.feeder = feeder;
        this.stoppingDist = (!feeder ? RobotMap.PEG_DIST : RobotMap.FEEDER_DIST);
        requires(Robot.visionSubSys);
        requires(Robot.driveSubSys);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        // TODO: Change power value to account for distance

        Robot.visionSubSys.refresh();

        if (Robot.visionSubSys.turn < -1) {
            power = 0;
            turn = 0;
            Robot.visionSubSys.found = false;
        } else {
            if (Robot.visionSubSys.dist >= 30)
                power = 0.45;
            else power = 0.2;

            Robot.visionSubSys.found = true;
            turn = Robot.visionSubSys.turn;
        }

        SmartDashboard.putNumber("Power", power);

        Robot.driveSubSys.auto(power, turn);

        if (Robot.visionSubSys.dist <= RobotMap.PEG_DIST)
            aligned = true;
        else aligned = false;

        SmartDashboard.putBoolean("Found: ", Robot.visionSubSys.found);
        SmartDashboard.putBoolean("Aligned and Ready to Drop: ", aligned);
    }

    @Override
    protected boolean isFinished() {
        return Robot.visionSubSys.dist <= stoppingDist;
    }

    @Override
    protected void end() {
        Robot.driveSubSys.stop();
    }

    @Override
    protected void interrupted() {
        end();
    }
}
