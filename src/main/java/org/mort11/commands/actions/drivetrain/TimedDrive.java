package org.mort11.commands.actions.drivetrain;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import org.mort11.subsystems.Drivetrain;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.Timer;

public class TimedDrive extends Command{
  private Drivetrain drivetrain;
  private Timer timer;
  private double time;

  private double x;
  private double y;
  private double omega;
  private boolean fieldOriented;
  /**
   * Moves the drivetrain a certain amount of time given movement parameters.
   * @param time
   * The amount of time to drive for
   * @param x
   * The velocity in the x direction
   * @param y
   * The velocity in the y direction
   * @param omega
   * The angular velocity
   */
  public TimedDrive(double time, double x, double y, double omega) {
    drivetrain = Drivetrain.getInstance();
    
    timer  = new Timer();
    this.time = time;

    this.x = x;
    this.y = y;
    this.omega = omega;
    this.fieldOriented = false;
    addRequirements(drivetrain);
  }
  public TimedDrive(double time, double x, double y, double omega, boolean fieldOriented) {
    drivetrain = Drivetrain.getInstance();
    
    timer  = new Timer();
    this.time = time;

    this.x = x;
    this.y = y;
    this.omega = omega;
    this.fieldOriented = fieldOriented;
    addRequirements(drivetrain);
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
			drivetrain.setDrive(ChassisSpeeds.fromFieldRelativeSpeeds(
        -y, x, omega,
				drivetrain.getRotation2d())
      );
		} else {
			  drivetrain.setDrive(new ChassisSpeeds(
        -y, x, omega)
      );
		}
	}
  /**
   * Called once the command ends or is interrupted.
   */
  @Override
  public void end(boolean interrupted) {
    drivetrain.setDrive(new ChassisSpeeds(0, 0, 0));
  }

  /**
   * Returns true when the command should end.
   */
  @Override
  public boolean isFinished() {
    return timer.get() > time;
  }
}
