// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.mort11.commands.actions.drivetrain.auto;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.trajectory.TrapezoidProfile.Constraints;
import edu.wpi.first.wpilibj2.command.Command;

import static org.mort11.config.constants.PIDConstants.Drivetrain.ANGLE_CONSTRAINTS;
import static org.mort11.config.constants.PIDConstants.Drivetrain.POS_CONSTRAINTS;
import static org.mort11.config.constants.PhysicalConstants.Drivetrain.IMU_TO_ROBOT_FRONT_ANGLE;

import org.mort11.Utility;
import org.mort11.config.constants.PortConstants;
import org.mort11.subsystems.Vision;
import org.mort11.subsystems.swerve.Drivetrain;

/** An example command that uses an example subsystem. */
public class DriveToReef extends Command {
  private Drivetrain drivetrain;
  private Vision vision;

  private boolean isRight;

  public DriveToReef(boolean isRight) {
    // Use addRequirements() here to declare subsystem dependencies.
    drivetrain =  Drivetrain.getInstance();
    vision = Vision.getInstance();

    this.isRight = isRight;

    addRequirements(drivetrain, vision);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    drivetrain.getXController().reset(vision.getRelativeRobotPosition().getX());
	  drivetrain.getYController().reset(vision.getRelativeRobotPosition().getY());
	  drivetrain.getRotateController().reset(vision.getRelativeRobotPosition().getRotation().getDegrees());

    drivetrain.getXController().setConstraints(new Constraints(2, POS_CONSTRAINTS.maxAcceleration));
    drivetrain.getYController().setConstraints(new Constraints(2, POS_CONSTRAINTS.maxAcceleration));
    drivetrain.getRotateController().setConstraints(new Constraints(40, ANGLE_CONSTRAINTS.maxAcceleration));
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    System.out.println(vision.getRelativeRobotPosition().toString());

    // if(vision.hasTag() && drivetrain.getRotateController().atSetpoint()) {
    //   drivetrain.setDrive(
    //     new ChassisSpeeds(
    //       -Utility.clamp(drivetrain.getYController().calculate(vision.getRelativeRobotPosition().getY(), -1), 1),
    //       Utility.clamp(drivetrain.getXController().calculate(vision.getRelativeRobotPosition().getX(), 0), 1),
    //       0
    //     )
    //   );
    // }

    if(vision.hasTag()) {
      drivetrain.setDrive(
        new ChassisSpeeds(
          -Utility.clamp(drivetrain.getYController().calculate(vision.getRelativeRobotPosition().getY(), -1), 2),
          Utility.clamp(drivetrain.getXController().calculate(vision.getRelativeRobotPosition().getX(), 0), 2),
          Utility.clamp(drivetrain.getRotateController().calculate(vision.getRelativeRobotPosition().getRotation().getDegrees(), 0), 6)
        )
      );
    }

    else {
      drivetrain.getXController().reset(vision.getRelativeRobotPosition().getX());
	  drivetrain.getYController().reset(vision.getRelativeRobotPosition().getY());
	  drivetrain.getRotateController().reset(vision.getRelativeRobotPosition().getRotation().getDegrees());

      drivetrain.setDrive(
        ChassisSpeeds.fromFieldRelativeSpeeds(
          0, 0, 0,
          drivetrain.getRotation2d()
        )
      );
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drivetrain.setDrive(
      new ChassisSpeeds(0, 0, 0)
    );
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
      return false;
  }
}
