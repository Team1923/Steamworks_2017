package org.usfirst.frc.team1923.robot.utils; // Change to whatever package you would like

/* Imports */
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/*
 * Original Authors: AJ Granowski & 4624 Owatonna Robotics
 * Link to original: https://github.com/owatonnarobotics/XboxController
 * Modified by: Aravind 
 * Date: 2016-01-15
 * Modified by: Tim
 * Date: 2017-01-28
 * Changed to PS4 Controller
 */

public class PS4Controller extends Joystick {

	/* Default Values */
	private static final double DEFAULT_TRIGGER_DEADZONE = 0.01; // Jiggle room
																	// for the
																	// triggers
	private static final double DEFAULT_TRIGGER_SENSITIVITY = 0.6; // If the
																	// trigger
																	// is beyond
																	// this
																	// limit,
																	// say it
																	// has been
																	// pressed

	/* Button Mappings */
	private static final int SQUARE_BUTTON_ID = 1;
	private static final int TRIANGLE_BUTTON_ID = 2;
	private static final int CIRCLE_BUTTON_ID = 3;
	private static final int CROSS_BUTTON_ID = 4;
	private static final int LB_BUTTON_ID = 5; // Also called L1
	private static final int RB_BUTTON_ID = 6; // Also called R1
	private static final int SHARE_BUTTON_ID = 7;
	private static final int OPTIONS_BUTTON_ID = 8;
	private static final int LEFT_CLICK_ID = 9;
	private static final int RIGHT_CLICK_ID = 10;

	/* Axis Mappings */
	private static final int LEFT_TRIGGER_AXIS_ID = 2; // Also called L2
	private static final int RIGHT_TRIGGER_AXIS_ID = 3; // Also called R2

	private static final int LEFT_STICK_X_AXIS_ID = 4;
	private static final int LEFT_STICK_Y_AXIS_ID = 5;
	private static final int RIGHT_STICK_X_AXIS_ID = 6;
	private static final int RIGHT_STICK_Y_AXIS_ID = 7;

	/* Instance Values */
	private final int port;
	private final Joystick stick;

	public final Trigger lt;
	public final Trigger rt;
	public final DirectionalPad dPad;
	public final Button square;
	public final Button triangle;
	public final Button circle;
	public final Button cross;
	public final Button lb;
	public final Button rb;
	public final Button share;
	public final Button options;
	public final Button rightClick;
	public final Button leftClick;

	/*
	 * @param: port
	 */
	public PS4Controller(final int port) {
		super(port); // Extends Joystick...

		/* Initialize */
		this.port = port;
		this.stick = new Joystick(this.port); // Joystick referenced by
												// everything

		this.dPad = new DirectionalPad(this.stick);
		this.lt = new Trigger(this.stick, HAND.LEFT);
		this.rt = new Trigger(this.stick, HAND.RIGHT);
		this.square = new JoystickButton(this.stick, SQUARE_BUTTON_ID);
		this.triangle = new JoystickButton(this.stick, TRIANGLE_BUTTON_ID);
		this.circle = new JoystickButton(this.stick, CIRCLE_BUTTON_ID);
		this.cross = new JoystickButton(this.stick, CROSS_BUTTON_ID);
		this.lb = new JoystickButton(this.stick, LB_BUTTON_ID);
		this.rb = new JoystickButton(this.stick, RB_BUTTON_ID);
		this.share = new JoystickButton(this.stick, SHARE_BUTTON_ID);
		this.options = new JoystickButton(this.stick, OPTIONS_BUTTON_ID);
		this.rightClick = new JoystickButton(this.stick, RIGHT_CLICK_ID);
		this.leftClick = new JoystickButton(this.stick, LEFT_CLICK_ID);
	}

	/**
	 * Rather than use an integer (which might not be what we expect) we use an
	 * enum which has a set amount of possibilities.
	 */
	public static enum HAND {
		LEFT, RIGHT
	}

	/**
	 * This is the relation of direction and number for .getPOV() used in the
	 * DirectionalPad class.
	 */
	public static enum DPAD {
		UP(0), UP_RIGHT(45), RIGHT(90), DOWN_RIGHT(135), DOWN(180), DOWN_LEFT(225), LEFT(270), UP_LEFT(315);

		/* Instance Value */
		private int value;

		/**
		 * Constructor
		 * 
		 * @param value
		 */
		DPAD(final int value) {
			this.value = value;
		}

		/**
		 * Convert integers to DPAD values
		 * 
		 * @param value
		 * @return DPAD with matching angle
		 */
		public static DPAD getEnum(int angle) {
			angle = Math.abs(angle);
			angle %= 360;
			angle = Math.round(angle / 45) * 45; // May have rounding errors.
													// Due to rounding errors.

			DPAD[] all = DPAD.values();

			for (int i = 0; i < all.length; i++) {
				if (all[i].value == angle) {
					return all[i];
				}
			}
			// I don't know what to do here
			// throw new UnsupportedOperationException("Integer supplied (" +
			// angle + ") is not a possible value of this enum.");
			System.out.println(
					"[XboxController.DPAD.getEnum()] Angle supplied (" + angle + ") has no related DPad direction");
			return DPAD.UP;
		}
	}

	/**
	 * This class is used to represent one of the two Triggers on an Xbox360
	 * controller.
	 */
	public static class Trigger extends Button {

		/* Instance Values */
		private final Joystick parent;
		private final HAND hand;

		private double deadZone;
		private double sensitivity;

		/**
		 * Constructor
		 * 
		 * @param joystick
		 * @param hand
		 */
		Trigger(final Joystick joystick, final HAND hand) {

			/* Initialize */
			this.parent = joystick;
			this.hand = hand;
			this.deadZone = DEFAULT_TRIGGER_DEADZONE;
			this.sensitivity = DEFAULT_TRIGGER_SENSITIVITY;
		}

		/* Extended Methods */
		@Override
		public boolean get() {
			return getX() > sensitivity;
		}

		/* Get Methods */
		/**
		 * getHand
		 * 
		 * @return Trigger hand
		 * 
		 *         See which side of the controller this trigger is
		 */
		public HAND getHand() {
			return hand;
		}

		/**
		 * 0 = Not pressed 1 = Completely pressed
		 * 
		 * @return How far its pressed
		 */
		public double getX() {
			final double rawInput;

			if (hand == HAND.LEFT) {
				rawInput = parent.getRawAxis(LEFT_TRIGGER_AXIS_ID);
			} else {
				rawInput = parent.getRawAxis(RIGHT_TRIGGER_AXIS_ID);
			}

			return createDeadZone(rawInput, deadZone);
		}

		/* Set Methods */
		/**
		 * Set the deadzone of this trigger
		 * 
		 * @param number
		 */
		public void setTriggerDeadZone(double number) {
			this.deadZone = number;
		}

		/**
		 * How far you need to press this trigger to activate a button press
		 * 
		 * @param number
		 */
		public void setTriggerSensitivity(double number) {
			this.sensitivity = number;
		}
	}

	/**
	 * This is a weird object which is essentially just 8 buttons.
	 */
	public static class DirectionalPad extends Button {

		/* Instance Values */
		private final Joystick parent;

		public final Button up;
		public final Button upRight;
		public final Button right;
		public final Button downRight;
		public final Button down;
		public final Button downLeft;
		public final Button left;
		public final Button upLeft;

		/**
		 * Constructor
		 * 
		 * @param parent
		 */
		DirectionalPad(final Joystick parent) {

			/* Initialize */
			this.parent = parent;
			this.up = new DPadButton(this, DPAD.UP);
			this.upRight = new DPadButton(this, DPAD.UP_RIGHT);
			this.right = new DPadButton(this, DPAD.RIGHT);
			this.downRight = new DPadButton(this, DPAD.DOWN_RIGHT);
			this.down = new DPadButton(this, DPAD.DOWN);
			this.downLeft = new DPadButton(this, DPAD.DOWN_LEFT);
			this.left = new DPadButton(this, DPAD.LEFT);
			this.upLeft = new DPadButton(this, DPAD.UP_LEFT);
		}

		/**
		 * This class is used to represent each of the 8 values a dPad has as a
		 * button.
		 */
		public static class DPadButton extends Button {

			/* Instance Values */
			private final DPAD direction;
			private final DirectionalPad parent;

			/**
			 * Constructor
			 * 
			 * @param parent
			 * @param dPad
			 */
			DPadButton(final DirectionalPad parent, final DPAD dPadDirection) {

				/* Initialize */
				this.direction = dPadDirection;
				this.parent = parent;
			}

			/* Extended Methods */
			@Override
			public boolean get() {
				return parent.getAngle() == direction.value;
			}
		}

		private int angle() {
			return parent.getPOV();
		}

		/* Extended Methods */
		@Override
		public boolean get() {
			return angle() != -1;
		}

		/* Get Methods */
		/**
		 * UP 0; UP_RIGHT 45; RIGHT 90; DOWN_RIGHT 135; DOWN 180; DOWN_LEFT 225;
		 * LEFT 270; UP_LEFT 315;
		 * 
		 * @return A number between 0 and 315 indicating direction
		 */
		public int getAngle() {
			return angle();
		}

		/**
		 * Just like getAngle, but returns a direction instead of an angle
		 * 
		 * @return A DPAD direction
		 */
		public DPAD getDirection() {
			return DPAD.getEnum(angle());
		}
	}

	/**
	 * Creates a deadzone, but without clipping the lower values. turns this
	 * |--1--2--3--4--5--| into this ______|-1-2-3-4-5-|
	 * 
	 * @param input
	 * @param deadZoneSize
	 * @return adjusted_input
	 */
	private static double createDeadZone(double input, double deadZoneSize) {
		final double negative;
		double deadZoneSizeClamp = deadZoneSize;
		double adjusted;

		if (deadZoneSizeClamp < 0 || deadZoneSizeClamp >= 1) {
			deadZoneSizeClamp = 0; // Prevent any weird errors
		}

		negative = input < 0 ? -1 : 1;

		adjusted = Math.abs(input) - deadZoneSizeClamp; // Subtract the deadzone
														// from the magnitude
		adjusted = adjusted < 0 ? 0 : adjusted; // if the new input is negative,
												// make it zero
		adjusted = adjusted / (1 - deadZoneSizeClamp); // Adjust the adjustment
														// so it can max at 1

		return negative * adjusted;
	}

	/* Get Methods */
	/**
	 * @return The port of this XboxController
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @return The Joystick of this XboxController
	 */
	public Joystick getJoystick() {
		return stick;
	}

	/**
	 * Adjusted y values based on deadzone
	 * 
	 * @return the adjusted y value
	 */
	public double getLeftY() {
		double val = this.getRawAxis(LEFT_STICK_Y_AXIS_ID);
		return Math.abs(val) > DEFAULT_TRIGGER_DEADZONE ? val : 0;
	}

	/**
	 * Adjusted y values based on deadzone
	 * 
	 * @return the adjusted y value
	 */
	public double getRightY() {
		double val = this.getRawAxis(RIGHT_STICK_Y_AXIS_ID);
		return Math.abs(val) > DEFAULT_TRIGGER_DEADZONE ? val : 0;
	}

	/**
	 * Adjusted x values based on deadzone
	 * 
	 * @return the adjusted x value
	 */
	public double getLeftX() {
		double val = this.getRawAxis(LEFT_STICK_X_AXIS_ID);
		return Math.abs(val) > DEFAULT_TRIGGER_DEADZONE ? val : 0;
	}

	/**
	 * Adjusted x values based on deadzone
	 * 
	 * @return the adjusted x value
	 */
	public double getRightX() {
		double val = this.getRawAxis(RIGHT_STICK_X_AXIS_ID);
		return Math.abs(val) > DEFAULT_TRIGGER_DEADZONE ? val : 0;
	}

	/* Set Methods */
	/**
	 * Make the controller vibrate
	 * 
	 * @param hand
	 *            The side of the controller to rumble
	 * @param intensity
	 *            How strong the rumble is
	 */
	public void setRumble(HAND hand, double intensity) {
		final float amount = new Float(intensity);

		if (hand == HAND.LEFT) {
			stick.setRumble(RumbleType.kLeftRumble, amount);
		} else {
			stick.setRumble(RumbleType.kRightRumble, amount);
		}
	}

	/**
	 * Make the controller vibrate
	 * 
	 * @param intensity
	 *            How strong the rumble is
	 */
	public void setRumble(double intensity) {
		final float amount = new Float(intensity);

		stick.setRumble(RumbleType.kLeftRumble, amount);
		stick.setRumble(RumbleType.kRightRumble, amount);
	}
}
