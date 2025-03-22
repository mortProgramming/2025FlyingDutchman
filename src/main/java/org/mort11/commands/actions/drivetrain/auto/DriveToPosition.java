// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.mort11.commands.actions.drivetrain.auto;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.trajectory.TrapezoidProfile.Constraints;
import edu.wpi.first.wpilibj2.command.Command;

import static org.mort11.config.constants.PIDConstants.Drivetrain.ANGLE_CONSTRAINTS;
import static org.mort11.config.constants.PIDConstants.Drivetrain.POS_CONSTRAINTS;
import static org.mort11.config.constants.PhysicalConstants.Drivetrain.IMU_TO_ROBOT_FRONT_ANGLE;

import org.mort11.Utility;
import org.mort11.subsystems.Vision;
import org.mort11.subsystems.swerve.Drivetrain;

/** An example command that uses an example subsystem. */
public class DriveToPosition extends Command {
  private Drivetrain drivetrain;
  private Vision vision;

  private double wantedX;
  private double wantedY;
  private double wantedTheta;

  private double maxSpeed, maxRotate, maxAcceleration;

  public DriveToPosition(double wantedX, double wantedY, double wantedTheta) {
    // Use addRequirements() here to declare subsystem dependencies.
    drivetrain =  Drivetrain.getInstance();
    vision = Vision.getInstance();

    this.wantedX = wantedX;
    this.wantedY = wantedY;
    this.wantedTheta = wantedTheta;

    this.maxSpeed = POS_CONSTRAINTS.maxVelocity;
    this.maxRotate = ANGLE_CONSTRAINTS.maxVelocity;
    this.maxAcceleration = POS_CONSTRAINTS.maxAcceleration;

    addRequirements(drivetrain, vision);
  }

  public DriveToPosition(double wantedX, double wantedY, double wantedTheta, double maxSpeed) {
    // Use addRequirements() here to declare subsystem dependencies.
    drivetrain =  Drivetrain.getInstance();
    vision = Vision.getInstance();

    this.wantedX = wantedX;
    this.wantedY = wantedY;
    this.wantedTheta = wantedTheta;

    this.maxSpeed = maxSpeed;
    this.maxRotate = ANGLE_CONSTRAINTS.maxVelocity;
    this.maxAcceleration = POS_CONSTRAINTS.maxAcceleration;

    addRequirements(drivetrain, vision);
  }

  public DriveToPosition(double wantedX, double wantedY, double wantedTheta, double maxSpeed, double maxRotate) {
    // Use addRequirements() here to declare subsystem dependencies.
    drivetrain =  Drivetrain.getInstance();
    vision = Vision.getInstance();

    this.wantedX = wantedX;
    this.wantedY = wantedY;
    this.wantedTheta = wantedTheta;

    this.maxSpeed = maxSpeed;
    this.maxRotate = maxRotate;
    this.maxAcceleration = POS_CONSTRAINTS.maxAcceleration;

    addRequirements(drivetrain, vision);
  }

  public DriveToPosition(double wantedX, double wantedY, double wantedTheta, double maxSpeed, double maxRotate, double maxAcceleration) {
    // Use addRequirements() here to declare subsystem dependencies.
    drivetrain =  Drivetrain.getInstance();
    vision = Vision.getInstance();

    this.wantedX = wantedX;
    this.wantedY = wantedY;
    this.wantedTheta = wantedTheta;

    this.maxSpeed = maxSpeed;
    this.maxRotate = maxRotate;
    this.maxAcceleration = maxAcceleration;

    addRequirements(drivetrain, vision);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    drivetrain.getXController().reset(drivetrain.getPose().getX());
	  drivetrain.getYController().reset(drivetrain.getPose().getY());
	  drivetrain.getRotateController().reset(drivetrain.getRotation2d().getDegrees());

    drivetrain.getYController().calculate(drivetrain.getPose().getY(), wantedY);
    drivetrain.getXController().calculate(drivetrain.getPose().getX(), wantedX);
    drivetrain.calculateRotateController(wantedTheta + IMU_TO_ROBOT_FRONT_ANGLE);

    drivetrain.getXController().setConstraints(new Constraints(maxSpeed, maxAcceleration));
    drivetrain.getYController().setConstraints(new Constraints(maxSpeed, maxAcceleration));
    drivetrain.getRotateController().setConstraints(new Constraints(maxRotate, ANGLE_CONSTRAINTS.maxAcceleration));
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

      drivetrain.setDrive(
        ChassisSpeeds.fromFieldRelativeSpeeds(
          drivetrain.getYController().calculate(drivetrain.getPose().getY(), wantedY),
          -drivetrain.getXController().calculate(drivetrain.getPose().getX(), wantedX), 
          // -Utility.clamp(drivetrain.calculateRotateController(wantedTheta + IMU_TO_ROBOT_FRONT_ANGLE), 3),
          -drivetrain.calculateRotateController(wantedTheta + IMU_TO_ROBOT_FRONT_ANGLE),
          drivetrain.getRotation2d()
        )
      );

      if(vision.hasTag()) {
        drivetrain.setRobotCameraPosition(vision.getRobotPosition());
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
