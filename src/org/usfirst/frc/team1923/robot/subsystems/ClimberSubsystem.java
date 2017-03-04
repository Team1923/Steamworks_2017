package org.usfirst.frc.team1923.robot.subsystems;

import org.usfirst.frc.team1923.robot.RobotMap;
import org.usfirst.frc.team1923.robot.commands.climbCommands.ClimbCommand;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ClimberSubsystem extends Subsystem {

	private final double P_CONSTANT = 0;
	private final double I_CONSTANT = 0;
	private final double D_CONSTANT = 0;
	public final double OVER_CURRENT_RATIO = 5; // Amps / Volt
	public final double CLIMB_POWER = 0.8;
	// TODO: Find motor direction
	private CANTalon leftClimb, rightClimb;

	/**
	 * Creates an instance of the Climber subsystem with two talons
	 * 
	 * Creates the climber subsystem in order to add protection features to
	 * slowly disable the climber once it reaches the top.
	 */
	public ClimberSubsystem() {

		leftClimb = new CANTalon(RobotMap.LEFT_CLIMB_PORT);
		rightClimb = new CANTalon(RobotMap.RIGHT_CLIMB_PORT);
		// Sets up the follower relationship
		rightClimb.changeControlMode(TalonControlMode.Follower);
		rightClimb.reverseOutput(true); // Because the motors are against
										// eachother
		rightClimb.set(leftClimb.getDeviceID());

		leftClimb.configPeakOutputVoltage(12, -12);
		leftClimb.configNominalOutputVoltage(0, 0);

		leftClimb.setPID(P_CONSTANT, I_CONSTANT, D_CONSTANT);

		// action when
		// trigger
		// reached
	}

	public void set(double power) {
		leftClimb.set(power);

	}

	public double getCurrent() {
		return leftClimb.getOutputCurrent();
	}

	public double getMaxCurrent() {
		return Math.max(leftClimb.getOutputCurrent(), rightClimb.getOutputCurrent());
	}

	public double getVoltage() {
		return leftClimb.getOutputVoltage();
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
		setDefaultCommand(new ClimbCommand());
	}
}
