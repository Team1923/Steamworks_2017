package org.usfirst.frc.team1923.robot.triggers;

import org.usfirst.frc.team1923.robot.Robot;

import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 * Trigger to detect if the climber mechanism is overloaded and have hit the top
 */
public class OverCurrentTrigger extends Trigger {

	public boolean get() {
		if (Robot.climbSubSys != null) {
			double maxCurrent = Robot.climbSubSys.getMaxCurrent();
			double targetCurrent = Robot.climbSubSys.OVER_CURRENT_RATIO * Robot.climbSubSys.getVoltage();

			return maxCurrent > targetCurrent;
		} else {
			return false;
		}
	}
}
