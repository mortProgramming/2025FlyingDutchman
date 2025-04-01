// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.mort11.commands.actions.drivetrain.auto;

import static org.mort11.config.constants.PhysicalConstants.Drivetrain.IMU_TO_ROBOT_FRONT_ANGLE;

import org.mort11.Utility;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Command;

import org.mort11.subsystems.swerve.Drivetrain;

/** An example command that uses an example subsystem. */
public class Rotate extends Command {
  private Drivetrain drivetrain;

  private double wantedTheta;

  public Rotate(double wantedTheta) {
    // Use addRequirements() here to declare subsystem dependencies.
    drivetrain =  Drivetrain.getInstance();

    this.wantedTheta = wantedTheta;

    addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    drivetrain.getRotateController().reset(drivetrain.getRotation2d().getDegrees());

    drivetrain.calculateRotateController(wantedTheta + IMU_TO_ROBOT_FRONT_ANGLE);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    drivetrain.setDrive(
        ChassisSpeeds.fromFieldRelativeSpeeds(
            0,
            0, 
            -drivetrain.calculateRotateController(wantedTheta + IMU_TO_ROBOT_FRONT_ANGLE),
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
    return
      drivetrain.getRotateController().atSetpoint();
    // return false;
  }
}
