package org.usfirst.frc.team1923.robot.triggers;

import org.usfirst.frc.team1923.robot.Robot;

import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 * Trigger to detect if the climber mechanism is overloaded and have hit the top
 */
public class OverCurrentTrigger extends Trigger {

	public boolean get() {
		return Robot.climbSubSys.getMaxCurrent() > Robot.climbSubSys.OVER_CURRENT_RATIO
				* Robot.climbSubSys.getVoltage();
	}
}
