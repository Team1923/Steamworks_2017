package org.usfirst.frc.team1923.robot.subsystems;

import org.usfirst.frc.team1923.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

public class GearSubsystem extends Subsystem {

    public DoubleSolenoid slider;
    public DoubleSolenoid gear;

    public boolean gearShifted;
    public boolean slideShifted;

    public GearSubsystem() {
        this.slider = new DoubleSolenoid(RobotMap.PCM_MODULE_NUM, RobotMap.SLIDE_FORWARD_PORT, RobotMap.SLIDE_BACKWARD_PORT);
        this.gear = new DoubleSolenoid(RobotMap.PCM_MODULE_NUM, RobotMap.MECH_FORWARD_PORT, RobotMap.MECH_BACKWARD_PORT);

        goHome();
    }

    @Override
    public void initDefaultCommand() {

    }

    public void slideShift() {
        if (this.slideShifted) {
            slideReverse();
        } else {
            slideForward();
        }
    }

    public void slideReverse() {
        if (this.slider.get() != Value.kForward) {
            this.slider.set(Value.kForward);
        }

        this.slideShifted = false;
    }

    public void slideForward() {
        if (this.slider.get() != Value.kReverse) {
            this.slider.set(Value.kReverse);
        }

        this.slideShifted = true;
    }

    public void gearShift() {
        if (this.gearShifted) {
            gearClose();
        } else {
            gearOpen();
        }
    }

    public void gearClose() {
        if (this.gear.get() != Value.kForward) {
            this.gear.set(Value.kForward);
        }

        this.gearShifted = false;
    }

    public void gearOpen() {
        if (this.gear.get() != Value.kReverse) {
            this.gear.set(Value.kReverse);
        }

        this.gearShifted = true;
    }

    public void goHome() {
        slideReverse();
        gearClose();
    }

}
