// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.mort11.commands.actions.drivetrain.auto.badlimelight;

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
import org.mort11.config.constants.PortConstants;
import org.mort11.subsystems.Vision;
import org.mort11.subsystems.swerve.Drivetrain;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;

/** An example command that uses an example subsystem. */
public class MoveToReef extends Command {
  private Drivetrain drivetrain;
  private Vision vision;

  private Timer timer;

  private boolean isRight;

  private Pose2d tagPose, reefPose;

  public MoveToReef(boolean isRight) {
    // Use addRequirements() here to declare subsystem dependencies.
    drivetrain =  Drivetrain.getInstance();
    vision = Vision.getInstance();

    timer = new Timer();

    this.isRight = isRight;

    tagPose = vision.getFieldTagPose(vision.getId());

    addRequirements(drivetrain, vision);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

    timer.reset();
    timer.start();

    drivetrain.getXController().reset(drivetrain.getPose().getX());
	  drivetrain.getYController().reset(drivetrain.getPose().getY());
	  drivetrain.getRotateController().reset(drivetrain.getRotation2d().getDegrees());

    // drivetrain.getYController().calculate(vision.getRelativeRobotPosition().getY(), -0.5);
    // drivetrain.getXController().calculate(vision.getRelativeRobotPosition().getX(), isRight ? CAMERA_RIGHT_OFFSET : CAMERA_LEFT_OFFSET);
    // drivetrain.getRotateController().calculate(vision.getPicturePosition()[0], 0);

    drivetrain.getXController().setConstraints(new Constraints(0.5, POS_CONSTRAINTS.maxAcceleration));
    drivetrain.getYController().setConstraints(new Constraints(0.5, POS_CONSTRAINTS.maxAcceleration));
    drivetrain.getRotateController().setConstraints(new Constraints(40, ANGLE_CONSTRAINTS.maxAcceleration));

    tagPose = new Pose2d(0, 0, new Rotation2d());
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    System.out.println(vision.getRobotPosition().toString());
    System.out.println(tagPose.toString());

    double sideValue = isRight ? CAMERA_RIGHT_OFFSET : CAMERA_LEFT_OFFSET;

    if (vision.hasTag()) {
        tagPose = vision.getFieldTagPose(vision.getId());
    }

    reefPose = new Pose2d(
      (tagPose.getX() + 
        sideValue * Math.cos(tagPose.getRotation().getRadians())
      ),
      (tagPose.getY() + 
        sideValue * Math.sin(tagPose.getRotation().getRadians())
      ), 
      Rotation2d.fromDegrees(tagPose.getRotation().getDegrees() - 180)
    );
    // reefPose = tagPose;


    if(
        vision.hasTag()
    ) {
        drivetrain.setRobotCameraPosition(vision.getRobotPosition());
    }

    drivetrain.setDrive(
        ChassisSpeeds.fromFieldRelativeSpeeds(
            // drivetrain.getXController().calculate(drivetrain.getPose().getX(), reefPose.getX() - 1), 
            // drivetrain.getYController().calculate(drivetrain.getPose().getY(), reefPose.getY()),
            -drivetrain.getXController().calculate(drivetrain.getPose().getX(), reefPose.getX()),
            // -drivetrain.getXController().calculate(drivetrain.getPose().getX(), reefPose.getX()), 
            0,
            // -Utility.clamp(drivetrain.calculateRotateController(reefPose.getRotation().getDegrees() + IMU_TO_ROBOT_FRONT_ANGLE - 180), 3),
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
    //  return drivetrain.getXController().atSetpoint() 
    //   && drivetrain.getYController().atSetpoint() 
    //   && drivetrain.getRotateController().atSetpoint();
    //   && timer.get() > 0.25;
    return false;
  }
}
