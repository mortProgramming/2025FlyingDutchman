package org.mort11.library.subsystems.swerve.autons;

import org.mort11.library.subsystems.swerve.swervedrives.OrientedSwerveDrive;
import org.mort11.library.subsystems.swerve.swervedrives.SwerveDrive;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj.Timer;

public class BasicTimedDrive extends Command{

  public OrientedSwerveDrive orientedSwerveDrive;
  public SwerveDrive swerveDrive;
  public Timer timer;
  public double time;

  public double x;
  public double y;
  public double omega;
  public boolean fieldOriented;
  
  public BasicTimedDrive(Subsystem subsystem, SwerveDrive swerveDrive, double time, double x, double y, double omega) {
    timer  = new Timer();

    this.time = time;
    this.x = x;
    this.y = y;
    this.omega = omega;
    this.fieldOriented = false;

    addRequirements(subsystem);
  }

  public BasicTimedDrive(Subsystem subsystem, OrientedSwerveDrive orientedSwerveDrive, double time, double x, double y, double omega, boolean fieldOriented) {

    if (fieldOriented) {
      this.orientedSwerveDrive = orientedSwerveDrive;
    } else {
      this.swerveDrive = orientedSwerveDrive;
    }
  
    timer  = new Timer();

    this.time = time;
    this.x = x;
    this.y = y;
    this.omega = omega;
    this.fieldOriented = fieldOriented;

    addRequirements(subsystem);
  }

  /**
   * Called when the command is initially scheduled.
   */
  @Override
  public void initialize() {
    timer.reset();
    timer.start();
    
  }

  /**
   * Called every time the scheduler runs while the command is scheduled.
   */
  @Override
  public void execute() {

    if (fieldOriented) {
			orientedSwerveDrive.setOrientedVelocity(new ChassisSpeeds(
        x, y, omega
      ));
		} else {
			swerveDrive.setVelocity(new ChassisSpeeds(
        x, y, omega
      ));
		}
	}

  @Override
  public void end(boolean interrupted) {
    if (fieldOriented) {
			orientedSwerveDrive.setOrientedVelocity(new ChassisSpeeds(
        0, 0, 0
      ));
		} else {
			swerveDrive.setVelocity(new ChassisSpeeds(
        0, 0, 0
      ));
		}
  }

  /**
   * Returns true when the command should end.
   */
  @Override
  public boolean isFinished() {
    return timer.get() > time;
  }
}
