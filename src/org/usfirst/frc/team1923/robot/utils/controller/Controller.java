package org.usfirst.frc.team1923.robot.utils.controller;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;

/**
 * Base controller class that provides a framework for XboxController and PS4Controller.
 *
 * @author AJ Granowski
 * @author 4624 Owatonna Robotics
 * @author Aravind
 * @author Tim Magoun
 */
public class Controller extends Joystick {

    private static final double DEFAULT_TRIGGER_DEADZONE = 0.01;
    private static final double DEFAULT_TRIGGER_SENSITIVITY = 0.6;

    private static final int LEFT_TRIGGER_AXIS_ID = 2;
    private static final int RIGHT_TRIGGER_AXIS_ID = 3;

    protected final int port;
    protected final Joystick controller;

    public Controller(final int port) {
        super(port);

        this.port = port;
        this.controller = new Joystick(this.port);
    }

    public enum Hand {
        LEFT, RIGHT
    }

    /**
     * This is the relation of direction and number for getPOV() used in the
     * DirectionalPad class.
     */
    public enum DPad {
        UP(0), UP_RIGHT(45), RIGHT(90), DOWN_RIGHT(135), DOWN(180), DOWN_LEFT(225), LEFT(270), UP_LEFT(315);

        private int value;

        DPad(final int value) {
            this.value = value;
        }

        /**
         * Convert integers to DPad values
         *
         * @param angle
         * @return DPad with matching angle
         */
        public static DPad getEnum(int angle) {
            angle = Math.abs(angle);
            angle %= 360;
            angle = Math.round(angle / 45) * 45; // May have rounding errors.

            DPad[] all = DPad.values();

            for (int i = 0; i < all.length; i++) {
                if (all[i].value == angle) {
                    return all[i];
                }
            }

            System.out.println("[Controller.DPad.getEnum()] Angle supplied (" + angle + ") has no related DPad direction");
            return DPad.UP;
        }
    }

    /**
     * This class is used to represent a trigger on the controller.
     */
    public static class Trigger extends Button {

        private final Joystick parent;
        private final Hand hand;

        private double deadZone;
        private double sensitivity;

        Trigger(final Joystick joystick, final Hand hand) {
            this.parent = joystick;
            this.hand = hand;
            this.deadZone = DEFAULT_TRIGGER_DEADZONE;
            this.sensitivity = DEFAULT_TRIGGER_SENSITIVITY;
        }

        @Override
        public boolean get() {
            return getX() > sensitivity;
        }

        /**
         * @return Trigger hand See which side of the controller this trigger is
         */
        public Hand getHand() {
            return hand;
        }

        /**
         * 0 = Not pressed 1 = Completely pressed
         *
         * @return How far its pressed
         */
        public double getX() {
            final double rawInput;

            if (hand == Hand.LEFT) {
                rawInput = parent.getRawAxis(LEFT_TRIGGER_AXIS_ID);
            } else {
                rawInput = parent.getRawAxis(RIGHT_TRIGGER_AXIS_ID);
            }

            return createDeadZone(rawInput, deadZone);
        }

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
     * This is an object which is essentially just 8 buttons.
     */
    public static class DirectionalPad extends Button {

        private final Joystick parent;

        public final Button up;
        public final Button upRight;
        public final Button right;
        public final Button downRight;
        public final Button down;
        public final Button downLeft;
        public final Button left;
        public final Button upLeft;

        DirectionalPad(final Joystick parent) {
            this.parent = parent;
            this.up = new DPadButton(this, DPad.UP);
            this.upRight = new DPadButton(this, DPad.UP_RIGHT);
            this.right = new DPadButton(this, DPad.RIGHT);
            this.downRight = new DPadButton(this, DPad.DOWN_RIGHT);
            this.down = new DPadButton(this, DPad.DOWN);
            this.downLeft = new DPadButton(this, DPad.DOWN_LEFT);
            this.left = new DPadButton(this, DPad.LEFT);
            this.upLeft = new DPadButton(this, DPad.UP_LEFT);
        }

        /**
         * This class is used to represent each of the 8 values a DPad has as a
         * button.
         */
        public static class DPadButton extends Button {
            private final DPad direction;
            private final DirectionalPad parent;

            DPadButton(final DirectionalPad parent, final DPad dPadDirection) {
                this.direction = dPadDirection;
                this.parent = parent;
            }

            @Override
            public boolean get() {
                return parent.getAngle() == direction.value;
            }
        }

        private int angle() {
            return parent.getPOV();
        }

        @Override
        public boolean get() {
            return angle() != -1;
        }

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
         * Just like getAngle(), but returns a direction instead of an angle
         *
         * @return A DPad direction
         */
        public DPad getDirection() {
            return DPad.getEnum(angle());
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

        adjusted = Math.abs(input) - deadZoneSizeClamp; // Subtract the deadzone from the magnitude
        adjusted = adjusted < 0 ? 0 : adjusted; // if the new input is negative, make it zero
        adjusted = adjusted / (1 - deadZoneSizeClamp); // Adjust the adjustment so it can max at 1

        return negative * adjusted;
    }

    @Override
    public int getPort() {
        return port;
    }

    public Joystick getJoystick() {
        return controller;
    }

    public void setRumble(Hand hand, double intensity) {
        final float amount = new Float(intensity);

        if (hand == Hand.LEFT) {
            controller.setRumble(RumbleType.kLeftRumble, amount);
        } else {
            controller.setRumble(RumbleType.kRightRumble, amount);
        }
    }

    public void setRumble(double intensity) {
        final float amount = new Float(intensity);

        controller.setRumble(RumbleType.kLeftRumble, amount);
        controller.setRumble(RumbleType.kRightRumble, amount);
    }

}
