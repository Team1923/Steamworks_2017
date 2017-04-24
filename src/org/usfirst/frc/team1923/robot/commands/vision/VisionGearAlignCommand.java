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

public class VisionGearAlignCommand extends Command {

    private boolean feeder;
    private double stoppingDist;

    public VisionGearAlignCommand() {
        this(false);
    }

    public VisionGearAlignCommand(boolean feeder) {
        this.feeder = feeder;
        this.stoppingDist = !this.feeder ? RobotMap.PEG_DIST : RobotMap.FEEDER_DIST;
        requires(Robot.visionSubSys);
        requires(Robot.driveSubSys);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        // TODO: Change power value to account for distance
        Robot.visionSubSys.refreshGear();

        double power, turn;

        if (Robot.visionSubSys.getGearTurn() < -1) {
            power = 0;
            turn = 0;

            Robot.visionSubSys.setGearFound(false);
        } else {
            power = Robot.visionSubSys.getDistance() >= 30 ? 0.45 : 0.20;
            turn = Robot.visionSubSys.getGearTurn();

            Robot.visionSubSys.setGearFound(true);
        }

        Robot.driveSubSys.auto(power, turn);

        SmartDashboard.putNumber("Gear Power", power);
        SmartDashboard.putBoolean("Gear Found: ", Robot.visionSubSys.isGearFound());
    }

    @Override
    protected boolean isFinished() {
        return Robot.visionSubSys.getDistance() <= this.stoppingDist;
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
