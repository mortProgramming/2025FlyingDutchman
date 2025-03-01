// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.mort11.commands.actions.drivetrain;

import static org.mort11.config.constants.PhysicalConstants.Drivetrain.IMU_TO_ROBOT_FRONT_ANGLE;

import java.util.function.DoubleSupplier;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Command;
import org.mort11.subsystems.swerve.Drivetrain;

/** An example command that uses an example subsystem. */
public class SetRobotOrientation extends Command {
  private Drivetrain drivetrain;

  private double orientationDegrees;

  public SetRobotOrientation(double orientationDegrees) {
    // Use addRequirements() here to declare subsystem dependencies.
    drivetrain =  Drivetrain.getInstance();

    this.orientationDegrees = orientationDegrees;

    addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    drivetrain.setFieldOffset(orientationDegrees + IMU_TO_ROBOT_FRONT_ANGLE);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
