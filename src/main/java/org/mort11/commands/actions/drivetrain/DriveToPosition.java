// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.mort11.commands.actions.drivetrain;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Command;
import org.mort11.subsystems.Drivetrain;

/** An example command that uses an example subsystem. */
public class DriveToPosition extends Command {
  private Drivetrain drivetrain;

  private double wantedX;
  private double wantedY;
  private double wantedTheta;

  public DriveToPosition(double wantedX, double wantedY, double wantedTheta) {
    // Use addRequirements() here to declare subsystem dependencies.
    drivetrain =  Drivetrain.getInstance();

    this.wantedX = wantedX;
    this.wantedY = wantedY;
    this.wantedTheta = wantedTheta;

    addRequirements(drivetrain);
  }

  public DriveToPosition(double wantedX, double wantedY) {
    // Use addRequirements() here to declare subsystem dependencies.
    drivetrain =  Drivetrain.getInstance();

    this.wantedX = wantedX;
    this.wantedY = wantedY;
    this.wantedTheta = drivetrain.getRotation2d().getDegrees();

    addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // drivetrain.setDrive(
    //   ChassisSpeeds.fromFieldRelativeSpeeds(
    //     drivetrain.getXController().calculate(drivetrain.getPose().getX(), wantedX),
		// drivetrain.getYController().calculate(drivetrain.getPose().getY(), wantedY), 
    //     drivetrain.getRotateController().calculate(drivetrain.getRotation2d().getDegrees(), wantedTheta),
    //     drivetrain.getRotation2d()
    //   )
    // );

    drivetrain.setDrive(
      ChassisSpeeds.fromFieldRelativeSpeeds(
        drivetrain.getXController().calculate(drivetrain.getPose().getX(), wantedX),
		drivetrain.getYController().calculate(drivetrain.getPose().getY(), wantedY), 
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
    return false;
  }
}
