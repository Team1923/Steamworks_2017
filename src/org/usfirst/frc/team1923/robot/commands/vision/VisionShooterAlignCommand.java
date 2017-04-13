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

public class VisionShooterAlignCommand extends Command {

	public double turn;

	public VisionShooterAlignCommand() {
		requires(Robot.visionSubSys);
		requires(Robot.driveSubSys);
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		// TODO: Change power value to account for distance

		Robot.visionSubSys.refreshShooter();
		turn = Robot.visionSubSys.shooterTurn;
		Robot.driveSubSys.auto(0, turn);

	}

	@Override
	protected boolean isFinished() {
		return Robot.visionSubSys.shooterCenterx < RobotMap.IMG_WIDTH / 2 + 2
				&& Robot.visionSubSys.shooterCenterx > RobotMap.IMG_WIDTH / 2 - 2;
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
