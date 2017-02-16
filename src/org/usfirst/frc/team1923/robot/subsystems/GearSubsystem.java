package org.usfirst.frc.team1923.robot.subsystems;

import org.usfirst.frc.team1923.robot.Robot;
import org.usfirst.frc.team1923.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
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
		slider = new DoubleSolenoid(RobotMap.PCM_MODULE_NUM, RobotMap.SLIDE_FORWARD_PORT,
				RobotMap.SLIDE_BACKWARD_PORT);
		gear = new DoubleSolenoid(RobotMap.PCM_MODULE_NUM, RobotMap.MECH_FORWARD_PORT, RobotMap.MECH_BACKWARD_PORT);
		slider.set(DoubleSolenoid.Value.kReverse);
		gear.set(DoubleSolenoid.Value.kForward);
	}

	public void initDefaultCommand() {
	}

	public void slideShift()
	{
    	slideIsShifted = (Robot.gearSubSys.slider.get() == DoubleSolenoid.Value.kForward);
    	if(slideIsShifted)
    		Robot.gearSubSys.slider.set(DoubleSolenoid.Value.kReverse);
    	else
    		Robot.gearSubSys.slider.set(DoubleSolenoid.Value.kForward);
    	slideIsShifted = !slideIsShifted;
	}
	
	public void gearShift()
	{
    	gearIsShifted = (Robot.gearSubSys.gear.get() == DoubleSolenoid.Value.kForward);
    	if(gearIsShifted)
    		Robot.gearSubSys.gear.set(DoubleSolenoid.Value.kReverse);
    	else
    		Robot.gearSubSys.gear.set(DoubleSolenoid.Value.kForward);
    	gearIsShifted = !gearIsShifted;
	}

}
