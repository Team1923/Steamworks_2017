package org.usfirst.frc.team1923.robot.commands.auton;

import org.usfirst.frc.team1923.robot.Robot;
import org.usfirst.frc.team1923.robot.RobotMap;
import org.usfirst.frc.team1923.robot.commands.drive.DriveTimeCommand;
import org.usfirst.frc.team1923.robot.commands.drive.ShiftCommand;
import org.usfirst.frc.team1923.robot.commands.gear.AutonGearCommand;
import org.usfirst.frc.team1923.robot.commands.gear.SlideCommand;
import org.usfirst.frc.team1923.robot.commands.shooter.BallFeedCommand;
import org.usfirst.frc.team1923.robot.commands.shooter.ShooterSpinUpCommand;
import org.usfirst.frc.team1923.robot.commands.shooter.VisionShooterSpinUpCommand;
import org.usfirst.frc.team1923.robot.commands.vision.VisionGearAlignCommand;
import org.usfirst.frc.team1923.robot.commands.vision.VisionShooterAlignCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class GearVisionShootAuton extends CommandGroup {

	public GearVisionShootAuton() {
		// Drops of gear on center peg
		Robot.visionSubSys.refreshGear();
		// Start spinning up the shooter so we don't have to wait for it to spin
		// up from the beginning
		addParallel(new ShooterSpinUpCommand(RobotMap.SHOOTER_CENTER_SETPOINT_PEG));
		addSequential(new ShiftCommand(false));
		addSequential(new WaitCommand(0.2));

		addSequential(new VisionGearAlignCommand());// Aligns Gear
		addSequential(new WaitCommand(0.2));

		addSequential(new SlideCommand(true));
		addSequential(new WaitCommand(0.4));
		addSequential(new AutonGearCommand(true));
		addSequential(new WaitCommand(0.4));
		addSequential(new DriveTimeCommand(-0.5, 1));

		addParallel(new VisionShooterAlignCommand());
		addSequential(new VisionShooterSpinUpCommand());
		addSequential(new BallFeedCommand(true));

	}
}
