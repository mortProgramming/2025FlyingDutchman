// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.mort11.commands.actions.drivetrain.auto;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.trajectory.TrapezoidProfile.Constraints;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;

import static org.mort11.config.constants.PIDConstants.Drivetrain.ANGLE_CONSTRAINTS;
import static org.mort11.config.constants.PIDConstants.Drivetrain.POS_CONSTRAINTS;
import static org.mort11.config.constants.PhysicalConstants.Drivetrain.IMU_TO_ROBOT_FRONT_ANGLE;

import org.mort11.Utility;
import org.mort11.config.constants.PortConstants;
import org.mort11.subsystems.Vision;
import org.mort11.subsystems.swerve.Drivetrain;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;

/** An example command that uses an example subsystem. */
public class MoveToReef extends Command {
  private Drivetrain drivetrain;
  private Vision vision;

  private boolean isRight;

  private Pose2d tagPose;

  public MoveToReef(boolean isRight) {
    // Use addRequirements() here to declare subsystem dependencies.
    drivetrain =  Drivetrain.getInstance();
    vision = Vision.getInstance();

    this.isRight = isRight;

    tagPose = vision.getFieldTagPose(vision.getId());

    addRequirements(drivetrain, vision);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

    drivetrain.getXController().reset(vision.getRelativeRobotPosition().getX());
	  drivetrain.getYController().reset(vision.getRelativeRobotPosition().getY());
	  drivetrain.getRotateController().reset(vision.getRelativeRobotPosition().getRotation().getDegrees());
    // drivetrain.getRotateController().reset(vision.getPicturePosition()[0]);

    drivetrain.getYController().calculate(vision.getRelativeRobotPosition().getY(), -0.5);
    drivetrain.getXController().calculate(vision.getRelativeRobotPosition().getX(), isRight ? 0.25 : -0.175);
    drivetrain.getRotateController().calculate(vision.getPicturePosition()[0], 0);

    drivetrain.getXController().setConstraints(new Constraints(0.5, POS_CONSTRAINTS.maxAcceleration));
    drivetrain.getYController().setConstraints(new Constraints(0.5, POS_CONSTRAINTS.maxAcceleration));
    drivetrain.getRotateController().setConstraints(new Constraints(40, ANGLE_CONSTRAINTS.maxAcceleration));
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    System.out.println(vision.getRobotPosition().toString());
    System.out.println(tagPose.toString());

    double xValue = isRight ? 0.25 : -0.175;

    if (vision.hasTag()) {
        tagPose = vision.getFieldTagPose(vision.getId());
    }

    // Pose2d modifiedTagPose = tagPose.plus(new Transform2d(isRight ? 0.25 : -0.175, 0, Rotation2d.fromDegrees(0)));
    Pose2d modifiedTagPose = tagPose;


    if(
        Math.sqrt(
            vision.getRelativeRobotPosition().getTranslation().getX() * vision.getRelativeRobotPosition().getTranslation().getX() + 
            vision.getRelativeRobotPosition().getTranslation().getY() * vision.getRelativeRobotPosition().getTranslation().getY()
        )
        < 3
    ) {
            
        // drivetrain.setRobotPosition(vision.getRobotPosition());
        drivetrain.setRobotPosition(new Pose2d(vision.getRobotPosition().getMeasureX(), vision.getRobotPosition().getMeasureY(), vision.getRobotPosition().getRotation().rotateBy(Rotation2d.fromDegrees(180))));
    }

    drivetrain.setDrive(
        ChassisSpeeds.fromFieldRelativeSpeeds(
            // drivetrain.getXController().calculate(drivetrain.getPose().getX(), modifiedTagPose.getX() - 1), 
            // drivetrain.getYController().calculate(drivetrain.getPose().getY(), modifiedTagPose.getY()),
            0,
            drivetrain.getXController().calculate(drivetrain.getPose().getX(), modifiedTagPose.getX() - 1), 
            // -Utility.clamp(drivetrain.calculateRotateController(modifiedTagPose.getRotation().getDegrees() + IMU_TO_ROBOT_FRONT_ANGLE - 180), 3),
            0,
            drivetrain.getRotation2d()
        )
    );
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
      // return drivetrain.getXController().atSetpoint() 
      // && drivetrain.getYController().atSetpoint() 
      // && drivetrain.getRotateController().atSetpoint();
        // return drivetrain.getXController().getPositionError() < 0.03 &&
        // drivetrain.getYController().getPositionError() < 0.03 &&
        // drivetrain.getRotateController().getPositionError() < 3;
        return false;
  }
}
