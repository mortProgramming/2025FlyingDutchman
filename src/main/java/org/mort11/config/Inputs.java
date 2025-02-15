package org.mort11.config;

import static org.mort11.config.constants.PhysicalConstants.Drivetrain.DRIVEBASE_RADIUS_METERS;
import static org.mort11.config.constants.PortConstants.Controller.*;
import org.mort11.subsystems.Drivetrain;

import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.CommandPS5Controller;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class Inputs {

    public static CommandJoystick joystick;
	// private static CommandJoystick throttle;
	public static CommandXboxController testingController;

    public static CommandXboxController compController;

    public static CommandPS5Controller driveController;

	public static Drivetrain drivetrain;

    public static void init() {
		joystick = new CommandJoystick(JOYSTICK);
        testingController = new CommandXboxController(TESTING_CONTROLLER);
        compController = new CommandXboxController(COMP_CONTROLLER);
        driveController = new CommandPS5Controller(DRIVE_CONTROLLER);

        joystick.setXChannel(JOYSTICK_X_CHANNEL);
        joystick.setYChannel(JOYSTICK_Y_CHANNEL);
        joystick.setTwistChannel(JOYSTICK_TWIST_CHANNEL);
        joystick.setThrottleChannel(THROTTLE_CHANNEL);

        // throttle.setThrottleChannel(THROTTLE_CHANNEL);

		drivetrain = Drivetrain.getInstance();
    }
    
      /**
     * scaled deadband - removing a value of the region around the zero value and scaling the rest to fit
     * 
     * @param value the total region that has a region near 0 that is a margin of error that needs to be corrected
     * @param deadband the margin of error around a zero point which is considered to be zero. 
     * @return the total region that has had the margin of error 0ed
     */
    public static double deadband(double value, double deadband) {

        if (Math.abs(value) > deadband) {
            //deadband is the region defined as +- deviation equal to 0

            if (value > 0.0) {
                return (value - deadband) / (1.0 - deadband);
            } else {
                return (value + deadband) / (1.0 - deadband);
            }
        }

        else {
            return 0.0;
        }
    }

     public static double modifyAxis1(double value, double throttleValue) {
        value = deadband(value, DEAD_BAND);

        return value * (throttleValue * (MAX_THROTTLE - MIN_THROTTLE) + MIN_THROTTLE);
    }

    public static double modifyAxis2(double value, double throttleValue) {
        value = deadband(value, DEAD_BAND);

        value = Math.copySign(value * value, value);

        return value * (throttleValue * (MAX_THROTTLE - MIN_THROTTLE) + MIN_THROTTLE);
        // return value * throttleValue;
    }

    public static double modifyAxisTwist(double value, double throttleValue) {
        value = deadband(value, DEAD_BAND);

        value = Math.copySign(value, value);

        return value * (throttleValue * (MAX_ROTATE - MIN_ROTATE) + MIN_ROTATE);
        // return value * throttleValue;
    }

    public static double getThrottle() {
        return 1 - (joystick.getThrottle() + 1 ) / 2;
        // return joystick.getThrottle();
    }

    /**
     * 
     * @return
     */
    public static double getJoystickX() {
		return modifyAxis1(-joystick.getX(), getThrottle()) * drivetrain.getMaxSpeedMeters();
	}

    /**
     * 
     * @return
     */
	public static double getJoystickY() {
		return modifyAxis1(joystick.getY(), getThrottle()) * drivetrain.getMaxSpeedMeters();
	}

    /**
     * 
     * @return
     */
	public static double getJoystickTwist() {
		return modifyAxisTwist(joystick.getTwist(), getThrottle())
			    * drivetrain.getMaxSpeedMeters() / 
                DRIVEBASE_RADIUS_METERS;
    }
    
    public static double getLeftControllerXSwerve() {
        return -modifyAxis1(driveController.getLeftX(), 1)  * drivetrain.getMaxSpeedMeters();
    }

    public static double getLeftControllerYSwerve() {
        return modifyAxis1(driveController.getLeftY(), 1) * drivetrain.getMaxSpeedMeters();
    }
    
    public static double getRightControllerXSwerve(){
        return modifyAxis1(driveController.getRawAxis(2), 1) * drivetrain.getMaxSpeedMeters() / 
                DRIVEBASE_RADIUS_METERS;
    }
}
 