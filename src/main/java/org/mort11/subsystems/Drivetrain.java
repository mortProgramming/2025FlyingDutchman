// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.mort11.subsystems;

import static org.mort11.MORTlib.hardware.encoder.EncoderTypeEnum.*;
import static org.mort11.MORTlib.hardware.imu.IMUTypeEnum.*;
import static org.mort11.MORTlib.hardware.motor.MotorTypeEnum.*;
import static org.mort11.MORTlib.subsystems.swerve.ModuleConfigEnum.*;
import static org.mort11.config.constants.PhysicalConstants.Drivetrain.*;
import static org.mort11.config.constants.PortConstants.Drivetrain.*;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.mort11.MORTlib.hardware.imu.IMU;
import org.mort11.MORTlib.subsystems.swerve.SwerveModule;
import org.mort11.MORTlib.subsystems.swerve.swervedrives.OdometeredSwerveDrive;
import org.mort11.config.IO;

public class Drivetrain extends SubsystemBase {
  private static Drivetrain drivetrain;

  private OdometeredSwerveDrive swerveDrive;

  private SwerveModule frontLeftModule;
  private SwerveModule frontRightModule;
  private SwerveModule backLeftModule;
  private SwerveModule backRightModule;

  private SwerveDriveKinematics kinematics;

  private ChassisSpeeds speeds;

  private IMU imu;

  private Drivetrain() {
    configureSwerve();
    
    speeds = new ChassisSpeeds(0, 0, 0);
  }

  public void configureSwerve () {
    frontLeftModule = new SwerveModule(
      KRAKEN, FRONT_LEFT_DRIVE_MOTOR, 
      KRAKEN, FRONT_LEFT_STEER_MOTOR, 
      CANCODER, FRONT_LEFT_ENCODER, 
      MK4i_L3
    );

    frontRightModule = new SwerveModule(
      KRAKEN, FRONT_RIGHT_DRIVE_MOTOR, 
      KRAKEN, FRONT_RIGHT_STEER_MOTOR, 
      CANCODER, FRONT_RIGHT_ENCODER, 
      MK4i_L3
    );

    backLeftModule = new SwerveModule(
      KRAKEN, BACK_LEFT_DRIVE_MOTOR, 
      KRAKEN, BACK_LEFT_STEER_MOTOR, 
      CANCODER, BACK_LEFT_ENCODER, 
      MK4i_L3
    );

    backRightModule = new SwerveModule(
      KRAKEN, BACK_RIGHT_DRIVE_MOTOR, 
      KRAKEN, BACK_RIGHT_STEER_MOTOR, 
      CANCODER, BACK_RIGHT_ENCODER, 
      MK4i_L3
    );

    // frontLeftModule.steerMotor.setDirectionFlip(true);
    // frontRightModule.steerMotor.setDirectionFlip(true);
    // backLeftModule.steerMotor.setDirectionFlip(true);
    // backRightModule.steerMotor.setDirectionFlip(true);

    kinematics = new SwerveDriveKinematics(
      // Front left
			new Translation2d(DRIVETRAIN_TRACKWIDTH_METERS / 2.0, DRIVETRAIN_WHEELBASE_METERS / 2.0),
			// Front right
			new Translation2d(DRIVETRAIN_TRACKWIDTH_METERS / 2.0, -DRIVETRAIN_WHEELBASE_METERS / 2.0),
			// Back left
			new Translation2d(-DRIVETRAIN_TRACKWIDTH_METERS / 2.0, DRIVETRAIN_WHEELBASE_METERS / 2.0),
			// Back right
			new Translation2d(-DRIVETRAIN_TRACKWIDTH_METERS / 2.0, -DRIVETRAIN_WHEELBASE_METERS / 2.0)
    );

    imu = new IMU(NAVX);

    swerveDrive = new OdometeredSwerveDrive(
      frontLeftModule, frontRightModule, 
      backLeftModule, backRightModule, 
      kinematics, imu
    );

    swerveDrive.setOffsets(FRONT_LEFT_OFFSET, FRONT_RIGHT_OFFSET, BACK_LEFT_OFFSET, BACK_RIGHT_OFFSET);

    swerveDrive.setCanivore(CANIVORE_NAME);
  }

  @Override
  public void periodic() {
    if (IO.getIsBlue()) {
			speeds = new ChassisSpeeds(
				speeds.vyMetersPerSecond, -speeds.vxMetersPerSecond,
				speeds.omegaRadiansPerSecond
			);
		}
		else {
			speeds = new ChassisSpeeds(
				-speeds.vyMetersPerSecond, speeds.vxMetersPerSecond,
				speeds.omegaRadiansPerSecond
			);
		}

		swerveDrive.setOrientedVelocity(speeds);

    swerveDrive.update();

    SmartDashboard.putNumber("XPose", swerveDrive.getPosition().getX());
    SmartDashboard.putNumber("YPose", swerveDrive.getPosition().getY());

    SmartDashboard.putNumber("Yaw", Math.toDegrees(swerveDrive.getRobotRotations().getZ()));
    SmartDashboard.putNumber("Pitch", Math.toDegrees(swerveDrive.getRobotRotations().getY()));
    SmartDashboard.putNumber("Roll", Math.toDegrees(swerveDrive.getRobotRotations().getX()));
  }

  public void setDrive(ChassisSpeeds speeds) {
    this.speeds = speeds;
  }

  public Command setGyroscopeZero(double angle) {
		return new InstantCommand(() -> swerveDrive.zeroIMU(angle));
	}



	public ChassisSpeeds getChassisSpeeds() {
        return speeds;
    }

	public double getMaxSpeedMeters() {
		return frontLeftModule.maxSpeed;
	}
	
	public OdometeredSwerveDrive getSwerveDrive() {
		return swerveDrive;
	}

	public SwerveDriveKinematics getDriveKinematics() {
		return kinematics;
	}

	public Rotation2d getRotation2d() {
		return imu.getRotation2d();
	}

  public static Drivetrain getInstance() {
		if (drivetrain == null) {
			drivetrain = new Drivetrain();
		}
		return drivetrain;
	}
}
