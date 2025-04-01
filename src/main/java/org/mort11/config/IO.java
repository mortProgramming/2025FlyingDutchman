package org.mort11.config;

import org.mort11.commands.actions.drivetrain.ResetPosition;
import org.mort11.commands.actions.drivetrain.SetRobotOrientation;
import org.mort11.commands.actions.drivetrain.auto.DriveToPosition;
import org.mort11.commands.actions.drivetrain.auto.Rotate;
import org.mort11.commands.actions.drivetrain.auto.badlimelight.destination.DriveToIntake;
import org.mort11.commands.actions.drivetrain.auto.badlimelight.MoveToReef;
import org.mort11.commands.actions.drivetrain.auto.badlimelight.destination.DriveTeleopToReef;
import org.mort11.commands.actions.drivetrain.auto.badlimelight.destination.DriveToReef;
import org.mort11.commands.actions.drivetrain.auto.badlimelight.near.DriveFastNearToReef;
import org.mort11.commands.actions.drivetrain.teleop.Angle2AprilTag;
import org.mort11.commands.actions.drivetrain.teleop.DriveAtAngle;
import org.mort11.commands.actions.drivetrain.teleop.DriveSetSpeed;
import org.mort11.commands.actions.drivetrain.teleop.DriveTagAngled;
import org.mort11.commands.actions.drivetrain.teleop.SnapToIntake;
import org.mort11.commands.actions.drivetrain.teleop.SnapToReef;
import org.mort11.commands.actions.endeff.Initiate;
import org.mort11.commands.actions.endeff.pid.SetEndeffector;
import org.mort11.commands.actions.endeff.pid.SetTikiTorchArm;
import org.mort11.commands.actions.endeff.velocity.Climb;
import org.mort11.commands.actions.endeff.velocity.VelocityAlgaeArm;
import org.mort11.commands.actions.endeff.velocity.VelocityAlgaeRoller;
import org.mort11.commands.actions.endeff.velocity.VelocityElevator;
import org.mort11.commands.actions.endeff.velocity.VelocityTikiTorchArm;
import org.mort11.commands.actions.endeff.velocity.VelocityTikiTorchRoller;
import org.mort11.commands.actions.lights.LightsCommand;

import static org.mort11.config.Inputs.testingController;
import static org.mort11.config.Inputs.operatorController;
import static org.mort11.config.Inputs.driveController;

import org.mort11.subsystems.AlgaeArm;
import org.mort11.subsystems.AlgaeRoller;
import org.mort11.subsystems.Climber;
import org.mort11.subsystems.swerve.Drivetrain;
import org.mort11.subsystems.Elevator;
import org.mort11.subsystems.Lights;
import org.mort11.subsystems.TikiTorchArm;
import org.mort11.subsystems.TikiTorchRoller;
import org.mort11.subsystems.Vision;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.InstantCommand;

public class IO {

  private static AlgaeArm algaeArm;
  private static AlgaeRoller algaeRoller;
  private static Climber climber;
	private static Drivetrain drivetrain;
  private static Elevator elevator;
  private static Lights lights;
  private static TikiTorchArm tikiTorchArm;
  private static TikiTorchRoller tikiTorchRoller;
  private static Vision vision;

  public static void init() {
    algaeArm = AlgaeArm.getInstance();
    algaeRoller = AlgaeRoller.getInstance();
    climber = Climber.getInstance();
		drivetrain = Drivetrain.getInstance();
    elevator = Elevator.getInstance();
    lights = Lights.getInstance();
    tikiTorchArm = TikiTorchArm.getInstance();
    tikiTorchRoller = TikiTorchRoller.getInstance();
    vision = Vision.getInstance();
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

    // driveController.button(5).whileTrue(new SnapToIntake(
    //   Inputs::getLeftControllerXSwerve, Inputs::getLeftControllerYSwerve
    // ));
    // driveController.button(6).whileTrue(new SnapToReef(
    //   Inputs::getLeftControllerXSwerve, Inputs::getLeftControllerYSwerve
    // ));

    // driveController.button(5).whileTrue(new Angle2AprilTag(Inputs::getLeftControllerXSwerve, Inputs::getLeftControllerYSwerve));
    //driveController.button(5).whileTrue(new Angle2AprilTag(0));
    // driveController.button(6).whileTrue(new DriveNearToReef(true));
    // driveController.button(5).whileTrue(new DriveNearToReef(false));
    driveController.button(6).whileTrue(new DriveTeleopToReef(true));
    driveController.button(5).whileTrue(new DriveTeleopToReef(false));
    // driveController.button(6).whileTrue(new MoveToReef(true));
    // driveController.button(9).whileTrue(new MoveToReef(false));
    driveController.button(10).whileTrue(new SnapToReef(Inputs::getLeftControllerXSwerve, Inputs::getLeftControllerYSwerve));
    // driveController.button(9).whileTrue(new DriveToIntake(false));
    driveController.button(9).whileTrue(new DriveToIntake());


    //TODO Xbox Controller Commands

      operatorController.axisGreaterThan(3, 0.25).whileTrue(VelocityTikiTorchRoller.intake());
      operatorController.axisGreaterThan(3, 0.25).whileFalse(VelocityTikiTorchRoller.rest());

      operatorController.rightBumper().whileTrue(VelocityTikiTorchRoller.outtake());
      operatorController.rightBumper().whileFalse(VelocityTikiTorchRoller.rest());

      operatorController.leftBumper().whileTrue(VelocityAlgaeRoller.intake());
      operatorController.leftBumper().whileFalse(VelocityAlgaeRoller.nothing());

      operatorController.axisGreaterThan(2, 0.25).whileTrue(VelocityAlgaeRoller.outtake());
      operatorController.axisGreaterThan(2, 0.25).whileFalse(VelocityAlgaeRoller.nothing());

      //auto endeffectorector

      operatorController.pov(90).onTrue(SetEndeffector.rest());
      operatorController.pov(270).onTrue(SetEndeffector.l2());
      operatorController.pov(180).onTrue(SetEndeffector.l3());
      operatorController.pov(0).onTrue(SetEndeffector.l4());
      operatorController.back().onTrue(SetEndeffector.lowAlgae());
      operatorController.start().onTrue(SetEndeffector.highAlgae());
      operatorController.a().onTrue(SetEndeffector.floor());
      operatorController.b().onTrue(SetEndeffector.intake());
      operatorController.y().onTrue(SetEndeffector.barge());

      // operatorController.y().onTrue(SetTikiTorchArm.score());
      operatorController.x().onTrue(SetTikiTorchArm.score());

      operatorController.button(10).whileTrue(new Initiate());


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

      // testingController.pov(90).toggleOnTrue(new Climb(true));
      // testingController.pov(270).toggleOnTrue(new Climb(false));

      testingController.axisGreaterThan(3, 0.25).whileTrue(VelocityTikiTorchRoller.outtake());
      testingController.axisGreaterThan(3, 0.25).whileFalse(VelocityTikiTorchRoller.rest());

      testingController.rightBumper().whileTrue(VelocityTikiTorchRoller.intake());
      testingController.rightBumper().whileFalse(VelocityTikiTorchRoller.rest());

      testingController.leftBumper().whileTrue(VelocityAlgaeRoller.intake());
      testingController.leftBumper().whileFalse(VelocityAlgaeRoller.nothing());

      testingController.axisGreaterThan(2, 0.25).whileTrue(VelocityAlgaeRoller.outtake());
      testingController.axisGreaterThan(2, 0.25).whileFalse(VelocityAlgaeRoller.nothing());

      testingController.start().whileTrue(new Initiate());

      testingController.back().whileTrue(new InstantCommand(() -> drivetrain.setRobotPosition(
      new Pose2d(7, 4, Rotation2d.fromDegrees(180))
    )));

    lights.setDefaultCommand(new LightsCommand());
    }

  public static Boolean isBlue () {
		return DriverStation.getAlliance().isPresent() ? DriverStation.getAlliance().get() == Alliance.Blue : true;
	}
}