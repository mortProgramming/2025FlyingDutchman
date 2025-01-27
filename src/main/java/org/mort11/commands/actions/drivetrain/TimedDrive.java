package org.mort11.commands.actions.drivetrain;

import org.mort11.library.subsystems.swerve.autons.BasicTimedDrive;
import org.mort11.subsystems.Drivetrain;

public class TimedDrive extends BasicTimedDrive {

  public TimedDrive(double time, double x, double y, double omega) {
    super(Drivetrain.getInstance(), Drivetrain.getInstance().getSwerveDrive(), time, x, y, omega);
  }

  public TimedDrive(double time, double x, double y, double omega, boolean fieldOriented) {
    super(Drivetrain.getInstance(), Drivetrain.getInstance().getSwerveDrive(), time, x, y, omega, fieldOriented);
  }
}
