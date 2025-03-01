package org.mort11.subsystems.swerve;

import org.mort11.config.IO;
import org.mort11.library.hardware.imu.IMU;
import static org.mort11.library.hardware.imu.IMUTypeEnum.PIGEON2;
import static org.mort11.config.constants.PIDConstants.Drivetrain.*;
import static org.mort11.config.constants.PhysicalConstants.Drivetrain.*;
import static org.mort11.config.constants.PortConstants.Drivetrain.*;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drivetrain extends SubsystemBase {
  private static Drivetrain drivetrain;

  private SwerveDrive swerveDrive;
  private IMU imu;

  private ChassisSpeeds speeds;
  private double fieldOrientationOffset;

  private ProfiledPIDController xToPosController, yToPosController, rotateToAngleController;

  private SwerveDriveOdometry odometer;

  private Field2d field;

  private Drivetrain() {
    swerveDrive = new SwerveDrive();
    
    speeds = new ChassisSpeeds(0, 0, 0);

    imu = new IMU(PIGEON2, IMU_ID);
    imu.setCanivore(CANIVORE_NAME);

    // xToPosController = new ProfiledPIDController(
	// 		POS_KP, POS_KI, POS_KD
	// 	);
	// 	yToPosController = new ProfiledPIDController(
	// 		POS_KP, POS_KI, POS_KD
	// 	);
    // 	rotateToAngleController = new ProfiledPIDController(
	// 		ANGLE_KP, ANGLE_KI, ANGLE_KD
	// 	);

	    xToPosController = new ProfiledPIDController(
			POS_KP, POS_KI, POS_KD, POS_CONSTRAINTS
		);
		yToPosController = new ProfiledPIDController(
			POS_KP, POS_KI, POS_KD, POS_CONSTRAINTS
		);
    	rotateToAngleController = new ProfiledPIDController(
			ANGLE_KP, ANGLE_KI, ANGLE_KD, ANGLE_CONSTRAINTS
		);

		rotateToAngleController.enableContinuousInput(-180, 180);

		fieldOrientationOffset = 0;

		field = new Field2d();

    odometer = new SwerveDriveOdometry(
      swerveDrive.getKinematics(), 
      Rotation2d.fromDegrees(0), 
      swerveDrive.getModulePositions());
  }

  @Override
  public void periodic() {
    // if (IO.isBlue()) {
		// 	speeds = new ChassisSpeeds(
		// 		speeds.vyMetersPerSecond, -speeds.vxMetersPerSecond,
		// 		speeds.omegaRadiansPerSecond
		// 	);
		// }
		// else {
		// 	speeds = new ChassisSpeeds(
		// 		-speeds.vyMetersPerSecond, speeds.vxMetersPerSecond,
		// 		speeds.omegaRadiansPerSecond
		// 	);
		// }

    speeds = new ChassisSpeeds(
				speeds.vxMetersPerSecond, speeds.vyMetersPerSecond,
				speeds.omegaRadiansPerSecond
			);

		swerveDrive.setVelocity(speeds);

   odometer.update(getAbsoluteRotation(), swerveDrive.getModulePositions());

   SmartDashboard.putNumber("XPose", odometer.getPoseMeters().getX());
    SmartDashboard.putNumber("YPose", odometer.getPoseMeters().getY());
    SmartDashboard.putNumber("Odometry Rot", odometer.getPoseMeters().getRotation().getDegrees());

    SmartDashboard.putNumber("Yaw", Math.toDegrees(imu.getRotation3d().getZ()));
    SmartDashboard.putNumber("Pitch", Math.toDegrees(imu.getRotation3d().getY()));
    SmartDashboard.putNumber("Roll", Math.toDegrees(imu.getRotation3d().getX()));

	field.setRobotPose(getPose());
	SmartDashboard.putData(field);
  }

  public void setDrive(ChassisSpeeds speeds) {
    this.speeds = speeds;
  }

  public void setDrivePathPlanner(ChassisSpeeds speeds) {
    this.speeds = new ChassisSpeeds(
				speeds.vxMetersPerSecond, speeds.vyMetersPerSecond,
				speeds.omegaRadiansPerSecond
			).times(1.15);
  }

  public void setDriveWithMax(ChassisSpeeds speeds, double max) {
    this.speeds = new ChassisSpeeds(
        clamp(speeds.vxMetersPerSecond, max),
        clamp(speeds.vyMetersPerSecond, max),
        speeds.omegaRadiansPerSecond
    );
  }

public static double clamp(double value, double max) {
	if(value > max) {
		return max;
	}
	else if(value < -max) {
		return -max;
	}

	return value;
}

	public void setFieldOffset(double fieldOrientationOffset) {
		this.fieldOrientationOffset = getAbsoluteRotation().getDegrees() + fieldOrientationOffset;
	}

  	public void setRobotPosition(Pose2d pose) {
    	odometer.resetPose(pose);
	}

	public void setRobotPosition(double x, double y, double rotationDegrees) {
		odometer.resetPose(new Pose2d(x, y, Rotation2d.fromDegrees(rotationDegrees)));
	}



	public ChassisSpeeds getChassisSpeeds() {
        return speeds;
    }

	public double calculateRotateController(double wantedDegrees) {
		return rotateToAngleController.calculate(
			getRotation2d().getDegrees(), 
			wantedDegrees
		);
	}

	public double calculateChangeRotateController(double wantedPosition) {
		return rotateToAngleController.calculate(
			getRotation2d().getDegrees(), 
			getRotation2d().getDegrees() + wantedPosition
		);
	}
	
	public SwerveDrive getSwerveDrive() {
		return swerveDrive;
	}

	public Rotation2d getRotation2d() {
		return getAbsoluteRotation().minus(Rotation2d.fromDegrees(fieldOrientationOffset));
	}

	public Rotation2d getAbsoluteRotation() {
		return imu.getRotation2d();
	}

	public Pose2d getPose() {
		return odometer.getPoseMeters();
	}

	public ChassisSpeeds getSpeed() {
		return speeds;
	}

	public ProfiledPIDController getXController() {
		return xToPosController;
	}

	public ProfiledPIDController getYController() {
		return yToPosController;
	}

	public ProfiledPIDController getRotateController() {
		return rotateToAngleController;
	}

	public Field2d getField() {
		return field;
	}

  public static Drivetrain getInstance() {
		if (drivetrain == null) {
			drivetrain = new Drivetrain();
		}
		return drivetrain;
	}
}
