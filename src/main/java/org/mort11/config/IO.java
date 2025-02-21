package org.mort11.config;

import org.mort11.commands.actions.Initiate;
import org.mort11.commands.actions.drivetrain.Angle2AprilTag;
import org.mort11.commands.actions.drivetrain.Drive;
import org.mort11.commands.actions.drivetrain.DriveSetSpeed;
import org.mort11.commands.actions.drivetrain.ToTag;

import org.mort11.commands.actions.endeffector.pid.Elevate;
import org.mort11.commands.actions.endeffector.pid.SetAlgaeArm;
import org.mort11.commands.actions.endeffector.pid.SetEndeffector;
import org.mort11.commands.actions.endeffector.pid.SetTikiTorchArm;
import org.mort11.commands.actions.endeffector.pid.ToggleAlgaeArm;
import org.mort11.commands.actions.endeffector.pid.ToggleTiki;

import org.mort11.commands.actions.endeffector.velocity.Climb;
import org.mort11.commands.actions.endeffector.velocity.VelocityAlgaeRoller;
import org.mort11.commands.actions.endeffector.velocity.VelocityTikiTorchRoller;
import org.mort11.library.commands.FlipOrFlop;
import org.mort11.commands.actions.endeffector.velocity.VelocityAlgaeArm;
import org.mort11.commands.actions.endeffector.velocity.VelocityElevator;
import org.mort11.commands.actions.endeffector.velocity.VelocityTikiTorchArm;

import static org.mort11.config.Inputs.joystick;
import static org.mort11.config.Inputs.testingController;
import static org.mort11.config.Inputs.compController;
import static org.mort11.config.Inputs.driveController;
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

		// drivetrain.setDefaultCommand(
		// 	new Drive(Inputs::getJoystickX, Inputs::getJoystickY, Inputs::getJoystickTwist)
    // );
      drivetrain.setDefaultCommand(
          new DriveSetSpeed(
            Inputs::getLeftControllerXSwerve, Inputs::getLeftControllerYSwerve, Inputs::getRightControllerXSwerve,
            1
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

    // driveController.triangle().whileTrue(drivetrain.setGyroscopeZero(IMU_TO_ROBOT_FRONT_ANGLE));
    driveController.triangle().whileTrue(new InstantCommand(() -> drivetrain.setFieldOffset(IMU_TO_ROBOT_FRONT_ANGLE), drivetrain));

    testingController.start().whileTrue(new InstantCommand(() -> drivetrain.getSwerveDrive().resetPosition(
      new Pose2d(0, 0, Rotation2d.fromDegrees(0))
    )));

    driveController.pov(90).whileTrue(new Climb(true));

    driveController.pov(270).whileTrue(new Climb(false));

      // joystick.trigger().whileTrue(new Angle2AprilTag(0));
      // driveController.axisGreaterThan(3, 0.7).whileTrue(new ToTag(0));


    //TODO Xbox Controller Commands

      // compController.pov(90).whileTrue(Elevate.rest());
      // compController.pov(270).whileTrue(Elevate.l2());
      // compController.pov(180).whileTrue(Elevate.l3());
      // compController.pov(0).whileTrue(Elevate.l4());
      // compController.start().whileTrue(Elevate.highAlgae());
      // compController.back().whileTrue(Elevate.lowAlgae());

      // compController.x().onTrue(new ToggleTiki(() -> compController.x().getAsBoolean()));

      // compController.b().whileTrue(SetTikiTorchArm.algaeClear());

      // compController.y().onTrue(new ToggleAlgaeArm(() -> compController.y().getAsBoolean()));

      // compController.a().whileTrue(SetAlgaeArm.floor());



      compController.axisGreaterThan(3, 0.25).whileTrue(VelocityTikiTorchRoller.outtake());
      compController.axisGreaterThan(3, 0.25).whileFalse(VelocityTikiTorchRoller.nothing());

      compController.rightBumper().whileTrue(VelocityTikiTorchRoller.intake());
      compController.rightBumper().whileFalse(VelocityTikiTorchRoller.nothing());

      compController.leftBumper().whileTrue(VelocityAlgaeRoller.intake());
      compController.leftBumper().whileFalse(VelocityAlgaeRoller.nothing());

      compController.axisGreaterThan(2, 0.25).whileTrue(VelocityAlgaeRoller.outtake());
      compController.axisGreaterThan(2, 0.25).whileFalse(VelocityAlgaeRoller.nothing());

      //auto endeffector

      compController.pov(90).whileTrue(SetEndeffector.rest());
      compController.pov(270).whileTrue(SetEndeffector.l2());
      compController.pov(180).whileTrue(SetEndeffector.l3());
      compController.pov(0).whileTrue(SetEndeffector.l4());
      compController.back().whileTrue(SetEndeffector.lowAlgae());
      compController.start().whileTrue(SetEndeffector.highAlgae());
      compController.a().whileTrue(SetEndeffector.floor());
      compController.b().whileTrue(SetEndeffector.intake());


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

    }

  public static Boolean isBlue () {
		return DriverStation.getAlliance().isPresent() ? DriverStation.getAlliance().get() == Alliance.Blue : true;
	}
}
