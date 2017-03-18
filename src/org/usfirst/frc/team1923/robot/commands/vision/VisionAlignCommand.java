package org.usfirst.frc.team1923.robot.commands.vision;

import org.usfirst.frc.team1923.robot.Robot;
import org.usfirst.frc.team1923.robot.RobotMap;

// import com.sun.webkit.Timer;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class VisionAlignCommand extends Command {

    /**
     * This command aligns the robot to the peg
     */
    public VisionAlignCommand() {
        requires(Robot.visionSubSys);
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void execute() {
        // TODO: Change power value to account for distance
        // TODO: Take into account values from ultrasonic sensors
        // new VisionAlignCommand(); TODO: Change to only run when needed to not
        // waste processor cycles
        boolean found;
        double power;
        double turn;

        Robot.visionSubSys.refresh();

        if (Robot.visionSubSys.getTurn() < -1) {
            power = 0;
            turn = 0;
            found = false;
        } else {
            power = Robot.visionSubSys.getDistance() >= 30 ? 0.45 : 0.2;
            found = true;
            turn = Robot.visionSubSys.getTurn();
        }

        Robot.driveSubSys.auto(power, turn);
        boolean aligned = Robot.visionSubSys.getDistance() <= RobotMap.MAX_DIST;

        SmartDashboard.putBoolean("Found", found);
        SmartDashboard.putBoolean("Aligned and Ready to Drop", aligned);
    }

    @Override
    protected boolean isFinished() {
        return Robot.visionSubSys.getDistance() < RobotMap.MAX_DIST;
    }

    @Override
    protected void end() {
        Robot.driveSubSys.stop();
    }

    @Override
    protected void interrupted() {

    }

}
