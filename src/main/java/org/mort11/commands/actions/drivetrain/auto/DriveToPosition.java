// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.mort11.commands.actions.drivetrain.auto;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Command;

import static org.mort11.config.constants.PhysicalConstants.Drivetrain.IMU_TO_ROBOT_FRONT_ANGLE;

import org.mort11.Utility;
import org.mort11.subsystems.swerve.Drivetrain;

/** An example command that uses an example subsystem. */
public class DriveToPosition extends Command {
  private Drivetrain drivetrain;

  private double wantedX;
  private double wantedY;
  private double wantedTheta;

  private double maxSpeed, maxRotate;

  private double iterations;

  public DriveToPosition(double wantedX, double wantedY, double wantedTheta) {
    // Use addRequirements() here to declare subsystem dependencies.
    drivetrain =  Drivetrain.getInstance();

    this.wantedX = wantedX;
    this.wantedY = wantedY;
    this.wantedTheta = wantedTheta;

    this.maxSpeed = 1;
    this.maxRotate = 0.5;

    iterations = 0;

    addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    drivetrain.getXController().reset(drivetrain.getPose().getX());
	  drivetrain.getYController().reset(drivetrain.getPose().getY());
	  drivetrain.getRotateController().reset(drivetrain.getRotation2d().getDegrees());
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

      // drivetrain.setDriveWithMax(
      //   ChassisSpeeds.fromFieldRelativeSpeeds(
      //     drivetrain.getYController().calculate(drivetrain.getPose().getY(), wantedY),
      //     -drivetrain.getXController().calculate(drivetrain.getPose().getX(), wantedX), 
      //     -Utility.clamp(drivetrain.calculateRotateController(wantedTheta + IMU_TO_ROBOT_FRONT_ANGLE), maxRotate),
      //     drivetrain.getRotation2d()
      //   ),
      //   maxSpeed
      // );

      drivetrain.setDrive(
        ChassisSpeeds.fromFieldRelativeSpeeds(
          drivetrain.getYController().calculate(drivetrain.getPose().getY(), wantedY),
          -drivetrain.getXController().calculate(drivetrain.getPose().getX(), wantedX), 
          -Utility.clamp(drivetrain.calculateRotateController(wantedTheta + IMU_TO_ROBOT_FRONT_ANGLE), 3),
          drivetrain.getRotation2d()
        )
      );

      iterations += 1;
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
    if (iterations > 10) {
      return 
      drivetrain.getXController().atSetpoint() &&
      drivetrain.getYController().atSetpoint() &&
      drivetrain.getRotateController().atSetpoint();
    }
      return false;
  }
}
