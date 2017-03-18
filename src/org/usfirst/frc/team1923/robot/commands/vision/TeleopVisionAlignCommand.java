package org.usfirst.frc.team1923.robot.commands.vision;

import org.usfirst.frc.team1923.robot.Robot;
import org.usfirst.frc.team1923.robot.RobotMap;

// import com.sun.webkit.Timer;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TeleopVisionAlignCommand extends Command {

    /**
     * This command continuously aligns the robot towards the target
     */
    public TeleopVisionAlignCommand() {
        requires(Robot.visionSubSys);
        requires(Robot.driveSubSys);
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void execute() {
        // TODO: Change power value to account for distance
        // TODO: Take into account values from ultrasonic sensors
        // TODO: Change to only run when needed to not waste processor cycles
        Robot.visionSubSys.refresh();

        if (Robot.visionSubSys.getDistance() >= RobotMap.MAX_DIST) {
            double turn;
            double power;

            if (Robot.visionSubSys.getTurn() < -1) {
                power = 0;
                turn = 0;
                Robot.visionSubSys.setFound(false);
            } else {
                power = Robot.visionSubSys.getDistance() >= 30 ? 0.4 : 0.2;
                turn = Robot.visionSubSys.getTurn();
                Robot.visionSubSys.setFound(true);
            }

            Robot.driveSubSys.auto(power, turn);
        } else {
            boolean aligned = Robot.visionSubSys.isFound() && Robot.visionSubSys.getDistance() <= RobotMap.MAX_DIST;
            SmartDashboard.putBoolean("Found: ", Robot.visionSubSys.isFound());
            SmartDashboard.putBoolean("Aligned and Ready to Drop: ", aligned);
        }
    }

    @Override
    protected boolean isFinished() {
        return Robot.visionSubSys.getDistance() <= RobotMap.MAX_DIST;
    }

    @Override
    protected void end() {
        Robot.driveSubSys.stop();
    }

    @Override
    protected void interrupted() {

    }

}
