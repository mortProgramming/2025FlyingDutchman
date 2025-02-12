package org.mort11.config;


import org.mort11.commands.actions.drivetrain.Angle2AprilTag;
import org.mort11.commands.actions.drivetrain.Drive;
import org.mort11.commands.actions.drivetrain.ToTag;
import org.mort11.commands.actions.endeffector.Climb;
import org.mort11.commands.actions.endeffector.Elevate;
import org.mort11.commands.actions.endeffector.MoveAlgaeArm;
import org.mort11.commands.actions.endeffector.MoveElevator;
import org.mort11.commands.actions.endeffector.MoveTikiTorchArm;
import org.mort11.commands.actions.endeffector.SetAlgaeArm;
import org.mort11.commands.actions.endeffector.SetAlgaeRoller;
import org.mort11.commands.actions.endeffector.SetTikiTorchArm;
import org.mort11.commands.actions.endeffector.SetTikiTorchRoller;
import static org.mort11.config.Inputs.joystick;
import static org.mort11.config.Inputs.xboxController;
import static org.mort11.config.constants.PhysicalConstants.Drivetrain.IMU_TO_ROBOT_FRONT_ANGLE;
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


  public static void init() {
		drivetrain = Drivetrain.getInstance();
    tikiTorch = TikiTorchArm.getInstance();
    elevator = Elevator.getInstance();
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

      // xboxController.a().whileTrue(new Climb(false));
      // xboxController.b().whileTrue(new Climb(true));

      // xboxController.pov(0).toggleOnTrue(Elevate.l1());
      // xboxController.pov(0).toggleOnFalse(Elevate.rest());
      // xboxController.pov(270).whileTrue(Elevate.l2());
      // xboxController.pov(180).whileTrue(Elevate.l3());
      // xboxController.pov(90).whileTrue(Elevate.l4());

      xboxController.rightTrigger().whileTrue(SetTikiTorchRoller.outtake());
      xboxController.rightTrigger().whileFalse(SetTikiTorchRoller.nothing());

      xboxController.rightBumper().whileTrue(SetTikiTorchRoller.intake());
      xboxController.rightBumper().whileFalse(SetTikiTorchRoller.nothing());

      xboxController.leftBumper().whileTrue(SetAlgaeRoller.intake());
      xboxController.leftBumper().whileFalse(SetAlgaeRoller.nothing());

      xboxController.leftTrigger().whileTrue(SetAlgaeRoller.outtake());
      xboxController.leftTrigger().whileFalse(SetAlgaeRoller.nothing());

      // xboxController.y().toggleOnTrue(SetTikiTorchArm.intake());
      // xboxController.y().toggleOnFalse(SetTikiTorchArm.l4());

      // xboxController.x().toggleOnTrue(SetAlgaeArm.l23Intake());
      // xboxController.x().toggleOnFalse(SetAlgaeArm.rest());

      // xboxController.axisLessThan(1, -0.5).whileTrue(new MoveTikiTorchArm(-5));
      // xboxController.axisGreaterThan(1, 0.5).whileTrue(new MoveTikiTorchArm(5));

      // xboxController.axisLessThan(5, -0.5).whileTrue(new MoveElevator(-0.2));
      // xboxController.axisGreaterThan(5, 0.5).whileTrue(new MoveElevator(0.2));
      xboxController.a().whileTrue(new MoveTikiTorchArm(-0.2));
      xboxController.a().whileFalse(new MoveTikiTorchArm(0));

      xboxController.b().whileTrue(new MoveTikiTorchArm(0.2));
      xboxController.b().whileFalse(new MoveTikiTorchArm(0));

      xboxController.x().whileTrue(new MoveAlgaeArm(-0.2));
      xboxController.x().whileFalse(new MoveAlgaeArm(0));

      xboxController.y().whileTrue(new MoveAlgaeArm(0.2));
      xboxController.y().whileFalse(new MoveAlgaeArm(0));

      xboxController.pov(0).whileTrue(new MoveElevator(0.2));
      xboxController.pov(0).whileFalse(new MoveElevator(0));
      xboxController.pov(180).whileTrue(new MoveElevator(-0.2));
      xboxController.pov(180).whileFalse(new MoveElevator(0));

    }

  public static Boolean isBlue () {
		return DriverStation.getAlliance().isPresent() ? DriverStation.getAlliance().get() == Alliance.Blue : true;
	}
}
