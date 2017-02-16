package org.usfirst.frc.team1923.robot.subsystems;

import org.usfirst.frc.team1923.robot.Robot;
import org.usfirst.frc.team1923.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class GearSubsystem extends Subsystem {

	public DoubleSolenoid slider;
	public DoubleSolenoid gear;

	public boolean gearIsShifted;
	public boolean slideIsShifted;

	public GearSubsystem() {
		slider = new DoubleSolenoid(RobotMap.PCM_MODULE_NUM, RobotMap.SLIDE_FORWARD_PORT, RobotMap.SLIDE_BACKWARD_PORT);
		gear = new DoubleSolenoid(RobotMap.PCM_MODULE_NUM, RobotMap.MECH_FORWARD_PORT, RobotMap.MECH_BACKWARD_PORT);
		slider.set(Value.kReverse);
		gear.set(Value.kForward);
	}

	public void initDefaultCommand() {
	}

	public void slideShift() {
		slideIsShifted = (slider.get() == Value.kForward);
		if (slideIsShifted)
			slider.set(Value.kReverse);
		else
			slider.set(Value.kForward);
		slideIsShifted = !slideIsShifted;
	}

	public void slideForward() {
		if (slider.get() != Value.kForward) {
			slider.set(Value.kForward);
		}
		slideIsShifted = true;
	}

	public void slideReverse() {
		if (slider.get() != Value.kReverse) {
			slider.set(Value.kReverse);
		}
		slideIsShifted = false;
	}

	public void gearShift() {
		gearIsShifted = (Robot.gearSubSys.gear.get() == Value.kForward);
		if (gearIsShifted)
			Robot.gearSubSys.gear.set(Value.kReverse);
		else
			Robot.gearSubSys.gear.set(Value.kForward);
		gearIsShifted = !gearIsShifted;
	}

	public void gearOpen() {
		if (gear.get() != Value.kForward) {
			gear.set(Value.kForward);
		}
		gearIsShifted = true;
	}

	public void gearClose() {
		if (gear.get() != Value.kReverse) {
			gear.set(Value.kReverse);
		}
		gearIsShifted = false;
	}
}
