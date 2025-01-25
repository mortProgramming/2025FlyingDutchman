// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.mort11.commands.actions.drivetrain;

import java.util.function.DoubleSupplier;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Command;
import org.mort11.subsystems.Drivetrain;

/** An example command that uses an example subsystem. */
public class Drive extends Command {
  private Drivetrain drivetrain;

  private DoubleSupplier wantedX;
  private DoubleSupplier wantedY;
  private DoubleSupplier wantedTheta;

  public Drive(DoubleSupplier wantedX, DoubleSupplier wantedY, DoubleSupplier wantedTheta) {
    // Use addRequirements() here to declare subsystem dependencies.
    drivetrain =  Drivetrain.getInstance();

    this.wantedX = wantedX;
    this.wantedY = wantedY;
    this.wantedTheta = wantedTheta;

    addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    drivetrain.setDrive(
      new ChassisSpeeds(
        wantedX.getAsDouble(),
				wantedY.getAsDouble(), 
        wantedTheta.getAsDouble()
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
