package org.mort11.config;

import org.mort11.commands.actions.drivetrain.Drive;
import static org.mort11.config.Inputs.joystick;
import static org.mort11.config.constants.PhysicalConstants.Drivetrain.IMU_TO_ROBOT_FRONT_ANGLE;
import org.mort11.subsystems.Drivetrain;
import org.mort11.subsystems.Elevator;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.InstantCommand;

public class IO {

	private static Drivetrain drivetrain;
  private static Elevator elevator;

  public static void init() {
		drivetrain = Drivetrain.getInstance();
    elevator = Elevator.getInstance();
  }

  public static void configure() {
    init();
    Inputs.init();

		drivetrain.setDefaultCommand(
			new Drive(Inputs::getJoystickX, Inputs::getJoystickY, Inputs::getJoystickTwist)
    );
      // drivetrain.setDefaultCommand(
      //     new Drive(Inputs::getLeftControllerXSwerve, Inputs::getLeftControllerYSwerve, Inputs::getRightControllerXSwerve)
      // );

    joystick.button(0).whileTrue(drivetrain.setGyroscopeZero(IMU_TO_ROBOT_FRONT_ANGLE));

    joystick.button(1).whileTrue(new InstantCommand(() -> drivetrain.getSwerveDrive().resetPosition(
      new Pose2d(0, 0, Rotation2d.fromDegrees(0))
    )));

    //controller inputs
  }

  public static Boolean isBlue () {
		return DriverStation.getAlliance().isPresent() ? DriverStation.getAlliance().get() == Alliance.Blue : true;
	}
}
