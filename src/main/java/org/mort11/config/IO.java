package org.mort11.config;

import org.mort11.commands.actions.Initiate;
import org.mort11.commands.actions.drivetrain.ResetPosition;
import org.mort11.commands.actions.drivetrain.SetRobotOrientation;
import org.mort11.commands.actions.drivetrain.auto.DriveToPosition;
import org.mort11.commands.actions.drivetrain.teleop.DriveSetSpeed;
import org.mort11.commands.actions.endeffector.pid.SetEndeffector;
import org.mort11.commands.actions.endeffector.pid.SetTikiTorchArm;

import org.mort11.commands.actions.endeffector.velocity.Climb;
import org.mort11.commands.actions.endeffector.velocity.VelocityAlgaeRoller;
import org.mort11.commands.actions.endeffector.velocity.VelocityTikiTorchRoller;
import org.mort11.commands.actions.endeffector.velocity.VelocityAlgaeArm;
import org.mort11.commands.actions.endeffector.velocity.VelocityElevator;
import org.mort11.commands.actions.endeffector.velocity.VelocityTikiTorchArm;

import static org.mort11.config.Inputs.testingController;
import static org.mort11.config.Inputs.compController;
import static org.mort11.config.Inputs.driveController;
import static org.mort11.config.constants.PhysicalConstants.Drivetrain.IMU_TO_ROBOT_FRONT_ANGLE;

import org.mort11.subsystems.Climber;
import org.mort11.subsystems.swerve.Drivetrain;
import org.mort11.subsystems.Elevator;
import org.mort11.subsystems.TikiTorchArm;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.XboxController;
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

		// drivetrain.setDefaultCommand(
		// 	new Drive(Inputs::getJoystickX, Inputs::getJoystickY, Inputs::getJoystickTwist)
    // );
      drivetrain.setDefaultCommand(
          new DriveSetSpeed(
            Inputs::getLeftControllerXSwerve, Inputs::getLeftControllerYSwerve, Inputs::getRightControllerXSwerve,
            0.15
          )
      );

    driveController.cross().onTrue(new DriveSetSpeed(
      Inputs::getLeftControllerXSwerve, Inputs::getLeftControllerYSwerve, Inputs::getRightControllerXSwerve, 
      0.15
    ));

    driveController.circle().onTrue(new DriveSetSpeed(
      Inputs::getLeftControllerXSwerve, Inputs::getLeftControllerYSwerve, Inputs::getRightControllerXSwerve, 
      0.3
    ));

    driveController.square().onTrue(new DriveSetSpeed(
      Inputs::getLeftControllerXSwerve, Inputs::getLeftControllerYSwerve, Inputs::getRightControllerXSwerve, 
      0.5
    ));

    driveController.triangle().onTrue(new DriveSetSpeed(
      Inputs::getLeftControllerXSwerve, Inputs::getLeftControllerYSwerve, Inputs::getRightControllerXSwerve, 
      0.75
    ));

    driveController.axisGreaterThan(4, 0.25).onTrue(new DriveSetSpeed(
      Inputs::getLeftControllerXSwerve, Inputs::getLeftControllerYSwerve, Inputs::getRightControllerXSwerve, 
      0.4
    ));

    driveController.axisLessThan(4, 0.25).onFalse(new DriveSetSpeed(
      Inputs::getLeftControllerXSwerve, Inputs::getLeftControllerYSwerve, Inputs::getRightControllerXSwerve, 
      0.15
    ));

    driveController.pov(0).whileTrue(new SetRobotOrientation(0));

    driveController.pov(180).whileTrue(new ResetPosition(0, 0, 0));

    driveController.pov(90).whileTrue(new Climb(true));

    driveController.pov(270).whileTrue(new Climb(false));

    driveController.button(5).whileTrue(new DriveToPosition(1, 1, 0));

    // //rest
    // compController.pov(90).onTrue(new DriveSetSpeed(
    //   Inputs::getLeftControllerXSwerve, Inputs::getLeftControllerYSwerve, Inputs::getRightControllerXSwerve,
    //   0.4
    // ));
    // // l2
    //   compController.pov(270).onTrue(new DriveSetSpeed(
    //     Inputs::getLeftControllerXSwerve, Inputs::getLeftControllerYSwerve, Inputs::getRightControllerXSwerve,
    //     0.15
    //   ));
    //   //l3
    //   compController.pov(180).onTrue(new DriveSetSpeed(
    //     Inputs::getLeftControllerXSwerve, Inputs::getLeftControllerYSwerve, Inputs::getRightControllerXSwerve,
    //     0.15
    //   ));
    //   //l4
    //   compController.pov(0).onTrue(new DriveSetSpeed(
    //     Inputs::getLeftControllerXSwerve, Inputs::getLeftControllerYSwerve, Inputs::getRightControllerXSwerve,
    //     0.15
    //   ));
    //   //low algae
    //   compController.back().onTrue(new DriveSetSpeed(
    //     Inputs::getLeftControllerXSwerve, Inputs::getLeftControllerYSwerve, Inputs::getRightControllerXSwerve,
    //     0.15
    //   ));

    //   //high algae
    //   compController.start().onTrue(new DriveSetSpeed(
    //     Inputs::getLeftControllerXSwerve, Inputs::getLeftControllerYSwerve, Inputs::getRightControllerXSwerve,
    //     0.15
    //   ));
    //   //floor
    //   compController.a().onTrue(new DriveSetSpeed(
    //     Inputs::getLeftControllerXSwerve, Inputs::getLeftControllerYSwerve, Inputs::getRightControllerXSwerve,
    //     0.15
    //   ));
    //   //intake
    //   compController.b().onTrue(new DriveSetSpeed(
    //     Inputs::getLeftControllerXSwerve, Inputs::getLeftControllerYSwerve, Inputs::getRightControllerXSwerve,
    //     0.15
    //   ));

      // joystick.trigger().whileTrue(new Angle2AprilTag(0));
      // driveController.axisGreaterThan(3, 0.7).whileTrue(new ToTag(0));


    //TODO Xbox Controller Commands

      compController.axisGreaterThan(3, 0.25).whileTrue(VelocityTikiTorchRoller.intake());
      compController.axisGreaterThan(3, 0.25).whileFalse(VelocityTikiTorchRoller.nothing());

      compController.rightBumper().whileTrue(VelocityTikiTorchRoller.outtake());
      compController.rightBumper().whileFalse(VelocityTikiTorchRoller.nothing());

      compController.leftBumper().whileTrue(VelocityAlgaeRoller.intake());
      compController.leftBumper().whileFalse(VelocityAlgaeRoller.nothing());

      compController.axisGreaterThan(2, 0.25).whileTrue(VelocityAlgaeRoller.outtake());
      compController.axisGreaterThan(2, 0.25).whileFalse(VelocityAlgaeRoller.nothing());

      //auto endeffector

      compController.pov(90).onTrue(SetEndeffector.rest());
      compController.pov(270).onTrue(SetEndeffector.l2());
      compController.pov(180).onTrue(SetEndeffector.l3());
      compController.pov(0).onTrue(SetEndeffector.l4());
      compController.back().onTrue(SetEndeffector.lowAlgae());
      compController.start().onTrue(SetEndeffector.highAlgae());
      compController.a().onTrue(SetEndeffector.floor());
      compController.b().onTrue(SetEndeffector.intake());
      compController.y().onTrue(SetEndeffector.barge());

      // compController.y().onTrue(SetTikiTorchArm.score());
      compController.x().onTrue(SetTikiTorchArm.score());

      compController.button(10).whileTrue(new Initiate());


      //TESTING XBOXCONTROLLER SETTINGS

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

      testingController.axisGreaterThan(3, 0.25).whileTrue(VelocityTikiTorchRoller.outtake());
      testingController.axisGreaterThan(3, 0.25).whileFalse(VelocityTikiTorchRoller.nothing());

      testingController.rightBumper().whileTrue(VelocityTikiTorchRoller.intake());
      testingController.rightBumper().whileFalse(VelocityTikiTorchRoller.nothing());

      testingController.leftBumper().whileTrue(VelocityAlgaeRoller.intake());
      testingController.leftBumper().whileFalse(VelocityAlgaeRoller.nothing());

      testingController.axisGreaterThan(2, 0.25).whileTrue(VelocityAlgaeRoller.outtake());
      testingController.axisGreaterThan(2, 0.25).whileFalse(VelocityAlgaeRoller.nothing());

      testingController.start().whileTrue(new Initiate());

      testingController.back().whileTrue(new InstantCommand(() -> drivetrain.setRobotPosition(
      new Pose2d(7, 4, Rotation2d.fromDegrees(180))
    )));

    }

  public static Boolean isBlue () {
		return DriverStation.getAlliance().isPresent() ? DriverStation.getAlliance().get() == Alliance.Blue : true;
	}
}