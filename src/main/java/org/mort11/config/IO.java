package org.mort11.config;

import org.mort11.commands.actions.drivetrain.Angle2AprilTag;
import org.mort11.commands.actions.drivetrain.Drive;
import org.mort11.commands.actions.drivetrain.ToTag;
import org.mort11.commands.actions.endeffector.move.MoveElevator;
import org.mort11.commands.actions.endeffector.move.MoveTikiTorchArm;
import org.mort11.commands.actions.endeffector.pid.Elevate;
import org.mort11.commands.actions.endeffector.pid.SetAlgaeArm;
import org.mort11.commands.actions.endeffector.pid.SetTikiTorchArm;
import org.mort11.commands.actions.endeffector.velocity.Climb;
import org.mort11.commands.actions.endeffector.velocity.VelocityAlgaeRoller;
import org.mort11.commands.actions.endeffector.velocity.VelocityTikiTorchRoller;
import org.mort11.commands.actions.endeffector.velocity.VelocityAlgaeArm;
import org.mort11.commands.actions.endeffector.velocity.VelocityElevator;
import org.mort11.commands.actions.endeffector.velocity.VelocityTikiTorchArm;

import static org.mort11.config.Inputs.joystick;
import static org.mort11.config.Inputs.testingController;
import static org.mort11.config.Inputs.compController;
import static org.mort11.config.constants.PhysicalConstants.Drivetrain.IMU_TO_ROBOT_FRONT_ANGLE;

import org.mort11.subsystems.Climber;
import org.mort11.subsystems.Drivetrain;
import org.mort11.subsystems.Elevator;
import org.mort11.subsystems.TikiTorchArm;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.InstantCommand;

public class IO {

	private static Drivetrain drivetrain;
  private static Elevator elevator;
  private static TikiTorchArm tikiTorch;
  private static Climber climber;


  public static void init() {
		drivetrain = Drivetrain.getInstance();
    tikiTorch = TikiTorchArm.getInstance();
    elevator = Elevator.getInstance();
    climber = Climber.getInstance();
  }

  public static void configure() {
    init();
    Inputs.init();

    //TODO Joystick Commands

		drivetrain.setDefaultCommand(
			new Drive(Inputs::getJoystickX, Inputs::getJoystickY, Inputs::getJoystickTwist)
    );
      // drivetrain.setDefaultCommand(
      //     new Drive(Inputs::getLeftControllerXSwerve, Inputs::getLeftControllerYSwerve, Inputs::getRightControllerXSwerve)
      // );
    joystick.trigger().whileTrue(drivetrain.setGyroscopeZero(IMU_TO_ROBOT_FRONT_ANGLE));

    joystick.button(1).whileTrue(new InstantCommand(() -> drivetrain.getSwerveDrive().resetPosition(
      new Pose2d(0, 0, Rotation2d.fromDegrees(0))
    )));

      joystick.trigger().whileTrue(new Angle2AprilTag(0));
      joystick.button(4).whileTrue(new ToTag(0));


    //TODO Xbox Controller Commands

      // compController.a().whileTrue(new Climb(false));
      // compController.b().whileTrue(new Climb(true));

      // compController.pov(0).toggleOnTrue(Elevate.l1());
      // compController.pov(0).toggleOnFalse(Elevate.rest());
      compController.pov(0).whileTrue(Elevate.rest());
      compController.pov(270).whileTrue(Elevate.l2());
      compController.pov(180).whileTrue(Elevate.l3());
      compController.pov(90).whileTrue(Elevate.l4());

      compController.y().whileTrue(SetTikiTorchArm.l4());
      compController.y().whileFalse(SetTikiTorchArm.intake());
      
      compController.x().whileTrue(SetAlgaeArm.l23Intake());
      compController.x().whileFalse(SetAlgaeArm.rest());


      //TESTING XBOXCONTROLLER SETTINGS

      testingController.rightTrigger().whileTrue(VelocityTikiTorchRoller.outtake());
      testingController.rightTrigger().whileFalse(VelocityTikiTorchRoller.nothing());

      testingController.rightBumper().whileTrue(VelocityTikiTorchRoller.intake());
      testingController.rightBumper().whileFalse(VelocityTikiTorchRoller.nothing());

      testingController.leftBumper().whileTrue(VelocityAlgaeRoller.intake());
      testingController.leftBumper().whileFalse(VelocityAlgaeRoller.nothing());

      testingController.leftTrigger().whileTrue(VelocityAlgaeRoller.outtake());
      testingController.leftTrigger().whileFalse(VelocityAlgaeRoller.nothing());

      testingController.a().whileTrue(new VelocityTikiTorchArm(-0.2));
      testingController.a().whileFalse(new VelocityTikiTorchArm(0));

      testingController.b().whileTrue(new VelocityTikiTorchArm(0.2));
      testingController.b().whileFalse(new VelocityTikiTorchArm(0));

      testingController.x().whileTrue(new VelocityAlgaeArm(-0.2));
      testingController.x().whileFalse(new VelocityAlgaeArm(0));

      testingController.y().whileTrue(new VelocityAlgaeArm(0.2));
      testingController.y().whileFalse(new VelocityAlgaeArm(0));

      testingController.pov(0).whileTrue(new VelocityElevator(-0.2));
      testingController.pov(0).whileFalse(new VelocityElevator(0));
      testingController.pov(180).whileTrue(new VelocityElevator(0.2));
      testingController.pov(180).whileFalse(new VelocityElevator(0));

      testingController.pov(90).toggleOnTrue(new Climb(true));
      testingController.pov(270).toggleOnTrue(new Climb(false));

    }

  public static Boolean isBlue () {
		return DriverStation.getAlliance().isPresent() ? DriverStation.getAlliance().get() == Alliance.Blue : true;
	}
}
