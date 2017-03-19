package org.usfirst.frc.team1923.robot;

import org.usfirst.frc.team1923.robot.commands.driveCommands.DriveDistanceCommand;
import org.usfirst.frc.team1923.robot.commands.driveCommands.ResetEncoderCommand;
import org.usfirst.frc.team1923.robot.commands.driveCommands.ShiftCommand;
import org.usfirst.frc.team1923.robot.commands.driveCommands.ShiftOmniCommand;
import org.usfirst.frc.team1923.robot.commands.driveCommands.TurnAngleCommand;
import org.usfirst.frc.team1923.robot.commands.gearCommands.GearCommand;
import org.usfirst.frc.team1923.robot.commands.gearCommands.GearSetHomeCommand;
import org.usfirst.frc.team1923.robot.commands.gearCommands.SlideCommand;
import org.usfirst.frc.team1923.robot.commands.visionCommands.*;
import org.usfirst.frc.team1923.robot.utils.PS4Controller;
import org.usfirst.frc.team1923.robot.utils.XboxController;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	//// CREATING BUTTONS
	// One type of button is a joystick button which is any button on a
	//// joystick.
	// You create one by telling it which joystick it's on and which button
	// number it is.
	// Joystick stick = new Joystick(port);
	// Button button = new JoystickButton(stick, buttonNumber);

	// There are a few additional built in buttons you can use. Additionally,
	// by subclassing Button you can create custom triggers and bind those to
	// commands the same as any other Button.

	//// TRIGGERING COMMANDS WITH BUTTONS
	// Once you have a button, it's trivial to bind it to a button in one of
	// three ways:

	// Start the command when the button is pressed and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenPressed(new ExampleCommand());

	// Run the command while the button is being held down and interrupt it once
	// the button is released.
	// button.whileHeld(new ExampleCommand());

	// Start the command when the button is released and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenReleased(new ExampleCommand());

	public PS4Controller driver;
	public XboxController op;

	public OI() {

		// Creates two ps4 controllers
		driver = new PS4Controller(RobotMap.DRIVER_CONTROLLER_PORT);
		driver.lt.setTriggerSensitivity(0.5);
		driver.rt.setTriggerSensitivity(0.5);

		op = new XboxController(RobotMap.OP_CONTROLLER_PORT);

		driver.lb.whenActive(new ShiftCommand(true));
		driver.rb.whenActive(new ShiftCommand(false));

		driver.lt.whenActive(new ShiftOmniCommand(true));
		driver.rt.whenActive(new ShiftOmniCommand(false));


		op.x.whenActive(new SlideCommand());
		op.y.whenActive(new GearCommand());

		op.b.whenActive(new GearSetHomeCommand());

		driver.cross.whenActive(new ResetEncoderCommand());
		
		//Vision Commands
		Command pegAlign = new TeleopVisionPegAlignCommand();
		driver.square.whileHeld(pegAlign);
		Command feederAlign = new TeleopVisionPegAlignCommand();
		driver.square.whileHeld(feederAlign);
		Command refresh = new VisionProcessing();
		driver.circle.whileHeld(refresh);

		//		driver.dPad.down.whenActive(new TurnAngleCommand(90));
		//		driver.dPad.up.whenActive(new TurnAngleCommand(180));
		//		driver.dPad.left.whenActive(new TurnAngleCommand(360));
		//		driver.dPad.right.whenActive(new TurnAngleCommand(720));

		driver.dPad.down.whenActive(new DriveDistanceCommand(50, 50));
		driver.dPad.up.whenActive(new DriveDistanceCommand(100, 100));
		driver.dPad.left.whenActive(new DriveDistanceCommand(200, 200));
		driver.dPad.right.whenActive(new DriveDistanceCommand(300,300));

	}
}
