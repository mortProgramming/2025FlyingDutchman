// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.mort11.commands.actions.drivetrain.auto;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.trajectory.TrapezoidProfile.Constraints;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;

import static org.mort11.config.constants.PIDConstants.Drivetrain.ANGLE_CONSTRAINTS;
import static org.mort11.config.constants.PIDConstants.Drivetrain.POS_CONSTRAINTS;
import static org.mort11.config.constants.PhysicalConstants.Drivetrain.IMU_TO_ROBOT_FRONT_ANGLE;
import static org.mort11.config.constants.PhysicalConstants.Vision.CAMERA_CENTER_OFFSET;

import org.mort11.Utility;
import org.mort11.config.constants.PortConstants;
import org.mort11.subsystems.Vision;
import org.mort11.subsystems.swerve.Drivetrain;

/** An example command that uses an example subsystem. */
public class DriveNearToCenterReef extends Command {
  private Drivetrain drivetrain;
  private Vision vision;

  private Timer timer;

  public DriveNearToCenterReef() {
    // Use addRequirements() here to declare subsystem dependencies.
    drivetrain =  Drivetrain.getInstance();
    vision = Vision.getInstance();

    timer = new Timer();

    addRequirements(drivetrain, vision);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    timer.reset();
    timer.start();

    drivetrain.getXController().reset(vision.getRelativeRobotPosition().getX());
	  drivetrain.getYController().reset(vision.getRelativeRobotPosition().getY());
	  drivetrain.getRotateController().reset(vision.getRelativeRobotPosition().getRotation().getDegrees());
    // drivetrain.getRotateController().reset(vision.getPicturePosition()[0]);

    drivetrain.getYController().calculate(vision.getRelativeRobotPosition().getY(), -0.3);
    drivetrain.getXController().calculate(vision.getRelativeRobotPosition().getX(), CAMERA_CENTER_OFFSET);
    drivetrain.getRotateController().calculate(vision.getPicturePosition()[0], 0);

    drivetrain.getXController().setConstraints(new Constraints(3, 6));
    drivetrain.getYController().setConstraints(new Constraints(3, 6));
    drivetrain.getRotateController().setConstraints(new Constraints(100, ANGLE_CONSTRAINTS.maxAcceleration));
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    System.out.println(vision.getRelativeRobotPosition().toString());

    if(vision.hasTag()) {

      drivetrain.setDrive(
        new ChassisSpeeds(
          -Utility.clamp(drivetrain.getYController().calculate(vision.getRelativeRobotPosition().getY(), -0.3), 3),
          Utility.clamp(drivetrain.getXController().calculate(vision.getRelativeRobotPosition().getX(), CAMERA_CENTER_OFFSET), 3),
          // -Utility.clamp(drivetrain.getRotateController().calculate(vision.getPicturePosition()[0], 0), 6)
          Utility.clamp(drivetrain.getRotateController().calculate(vision.getRelativeRobotPosition().getRotation().getDegrees(), 0), 6)
        )
      );
    }
  
    else {
      drivetrain.getXController().reset(vision.getRelativeRobotPosition().getX());
      drivetrain.getYController().reset(vision.getRelativeRobotPosition().getY());
      drivetrain.getRotateController().reset(vision.getRelativeRobotPosition().getRotation().getDegrees());
      // drivetrain.getRotateController().reset(vision.getPicturePosition()[0]);
  
      drivetrain.setDrive(
        ChassisSpeeds.fromFieldRelativeSpeeds(
          0, 0, 0,
          drivetrain.getRotation2d()
        )
      );
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
      // return drivetrain.getXController().atSetpoint() 
      // && drivetrain.getYController().atSetpoint() 
      // && drivetrain.getRotateController().atSetpoint();
        // return drivetrain.getXController().getPositionError() < 0.03 &&
        // drivetrain.getYController().getPositionError() < 0.03 &&
        // drivetrain.getRotateController().getPositionError() < 3;

        // return isAtGoal;

        // return timer.get() > 1.3 &&
        // drivetrain.getSpeed().vxMetersPerSecond < 0.01 &&
        // drivetrain.getSpeed().vxMetersPerSecond < 0.01 &&
        // drivetrain.getSpeed().omegaRadiansPerSecond < 0.3;

        return timer.get() > 1.3;
  }
}
