// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.mort11.commands.actions.drivetrain.auto.betterlimelight;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.trajectory.TrapezoidProfile.Constraints;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;

import static org.mort11.config.constants.PIDConstants.Drivetrain.ANGLE_CONSTRAINTS;
import static org.mort11.config.constants.PIDConstants.Drivetrain.POS_CONSTRAINTS;
import static org.mort11.config.constants.PhysicalConstants.Drivetrain.IMU_TO_ROBOT_FRONT_ANGLE;
import static org.mort11.config.constants.PhysicalConstants.Vision.CAMERA_RIGHT_OFFSET;
import static org.mort11.config.constants.PhysicalConstants.Vision.CAMERA_LEFT_OFFSET;

import org.mort11.Utility;
import org.mort11.config.IO;
import org.mort11.config.constants.PortConstants;
import org.mort11.config.constants.FieldConstants.Reef.ReefPost;
import static org.mort11.config.constants.FieldConstants.Reef.*;

import org.mort11.subsystems.Vision;
import org.mort11.subsystems.swerve.Drivetrain;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;

/** An example command that uses an example subsystem. */
public class MoveToPost extends Command {
  private Drivetrain drivetrain;
  private Vision vision;

  private ReefPost post;

  private Pose2d tagPose;

  private boolean tagSeen;

  public MoveToPost(ReefPost post) {
    // Use addRequirements() here to declare subsystem dependencies.
    drivetrain =  Drivetrain.getInstance();
    vision = Vision.getInstance();

    this.post = post;

    tagPose = new Pose2d(0, 0, new Rotation2d());

    addRequirements(drivetrain, vision);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    drivetrain.getXController().reset(drivetrain.getPose().getX());
	  drivetrain.getYController().reset(drivetrain.getPose().getY());
	  drivetrain.getRotateController().reset(drivetrain.getRotation2d().getDegrees());

    drivetrain.getXController().setConstraints(new Constraints(0.5, POS_CONSTRAINTS.maxAcceleration));
    drivetrain.getYController().setConstraints(new Constraints(0.5, POS_CONSTRAINTS.maxAcceleration));
    drivetrain.getRotateController().setConstraints(new Constraints(40, ANGLE_CONSTRAINTS.maxAcceleration));
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    System.out.println(vision.getRobotPosition().toString());
    System.out.println(tagPose.toString());

    if (vision.hasTag()) {
        tagSeen = true;
    }

    if(tagSeen) {
      drivetrain.setDrive(
        new ChassisSpeeds (
            -drivetrain.getXController().calculate(
              drivetrain.getExtraPose().getX(), modifyPose(post, vision.getRelativeRobotPosition()).getX()
            ),
            // -drivetrain.getYController().calculate(
            //   drivetrain.getExtraPose().getY(), modifyPose(post, vision.getRelativeRobotPosition()).getY()
            // ),
            0,
            // -Utility.clamp(drivetrain.calculateRotateController(reefPose.getRotation().getDegrees() + IMU_TO_ROBOT_FRONT_ANGLE - 180), 3),
            0
        )
      );
    }

    else {
      drivetrain.setDrive(
        ChassisSpeeds.fromFieldRelativeSpeeds(
            -drivetrain.getXController().calculate(drivetrain.getPose().getX(), getPostPose(IO.isBlue(), post).getX()),
            0,
            // -Utility.clamp(drivetrain.calculateRotateController(reefPose.getRotation().getDegrees() + IMU_TO_ROBOT_FRONT_ANGLE - 180), 3),
            0,
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
     return drivetrain.getXController().atSetpoint() 
      && drivetrain.getYController().atSetpoint();
    //   && timer.get() > 0.25;
    // return false;
  }

  public Pose2d modifyPose(ReefPost post, Pose2d pose) {
    double sideValue =  isRight(post) ? CAMERA_RIGHT_OFFSET : CAMERA_LEFT_OFFSET;

    return new Pose2d(
      (pose.getX() + 
        sideValue * Math.cos(vision.getRelativeRobotPosition().getRotation().getRadians())
      ),
      (pose.getY() + 
        sideValue * Math.sin(vision.getRelativeRobotPosition().getRotation().getRadians())
      ), 
      Rotation2d.fromDegrees(vision.getRelativeRobotPosition().getRotation().getDegrees() - 180)
    );
  }
}
