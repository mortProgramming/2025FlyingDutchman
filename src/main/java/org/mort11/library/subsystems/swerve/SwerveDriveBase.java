package org.mort11.library.subsystems.swerve;

import static org.mort11.library.logger.LoggerTypeEnum.SHUFFLEBOARD;
import static org.mort11.library.logger.LoggerTypeEnum.SMARTDASHBOARD;

import org.mort11.library.hardware.encoder.EncoderTypeEnum;
import org.mort11.library.hardware.imu.IMU;
import org.mort11.library.hardware.imu.IMUTypeEnum;
import org.mort11.library.hardware.motor.MotorTypeEnum;
import org.mort11.library.logger.LoggerGroup;
import org.mort11.library.subsystems.swerve.swervedrives.OdometeredSwerveDrive;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SwerveDriveBase extends SubsystemBase {
	private static SwerveDriveBase drivetrain;

	private OdometeredSwerveDrive swerveDrive;

	private SwerveModule frontLeftModule;
	private SwerveModule frontRightModule;
	private SwerveModule backLeftModule;
	private SwerveModule backRightModule;

	private SwerveDriveKinematics driveKinematics;

	private IMU imu;

	private ChassisSpeeds speeds;

	private LoggerGroup logger;

	public SwerveDriveBase(
		double drivetrainWheelBase, double drivetrainTrackWidth,
		MotorTypeEnum driveMotorType, 
		int frontLeftDriveMotorID, int frontRightDriveMotorID,
		int backLeftDriveMotorID, int backRightDriveMotorID,
        MotorTypeEnum steerMotorType,
		int frontLeftSteerMotorID, int frontRightSteerMotorID,
		int backLeftSteerMotorID, int backRightSteerMotorID,
		EncoderTypeEnum encoderType,
		int frontLeftEncoderID, int frontRightEncoderID,
		int backLeftEncoderID, int backRightEncoderID,
		ModuleConfigEnum moduleType,
		IMUTypeEnum imuType,
		double frontLeftOffset, double frontRightOffset,
		double backLeftOffset, double backRightOffset
	) {
		this(
			drivetrainWheelBase, drivetrainTrackWidth,
			driveMotorType, 
			frontLeftDriveMotorID, frontRightDriveMotorID,
			backLeftDriveMotorID, backRightDriveMotorID,
       		steerMotorType,
			frontLeftSteerMotorID, frontRightSteerMotorID,
			backLeftSteerMotorID, backRightSteerMotorID,
			encoderType,
			frontLeftEncoderID, frontRightEncoderID,
			backLeftEncoderID, backRightEncoderID,
			moduleType, imuType, 0,
			frontLeftOffset, frontRightOffset,
			backLeftOffset, backRightOffset
		);
	}

	private SwerveDriveBase(
		double drivetrainWheelBase, double drivetrainTrackWidth,
		MotorTypeEnum driveMotorType, 
		int frontLeftDriveMotorID, int frontRightDriveMotorID,
		int backLeftDriveMotorID, int backRightDriveMotorID,
        MotorTypeEnum steerMotorType,
		int frontLeftSteerMotorID, int frontRightSteerMotorID,
		int backLeftSteerMotorID, int backRightSteerMotorID,
		EncoderTypeEnum encoderType,
		int frontLeftEncoderID, int frontRightEncoderID,
		int backLeftEncoderID, int backRightEncoderID,
		ModuleConfigEnum moduleType,
		IMUTypeEnum imuType, int imuID,
		double frontLeftOffset, double frontRightOffset,
		double backLeftOffset, double backRightOffset
	) {
		frontLeftModule = new SwerveModule(
			driveMotorType, frontLeftDriveMotorID, 
			steerMotorType, frontLeftSteerMotorID, 
			encoderType, frontLeftEncoderID, 
			moduleType
		);

		frontRightModule = new SwerveModule(
			driveMotorType, frontRightDriveMotorID, 
			steerMotorType, frontRightSteerMotorID, 
			encoderType, frontRightEncoderID, 
			moduleType
		);
			
		backLeftModule = new SwerveModule(
			driveMotorType, backLeftDriveMotorID, 
			steerMotorType, backLeftSteerMotorID, 
			encoderType, backLeftEncoderID, 
			moduleType
		);
			
		backRightModule = new SwerveModule(
			driveMotorType, backRightDriveMotorID, 
			steerMotorType, backRightSteerMotorID, 
			encoderType, backRightEncoderID, 
			moduleType
		);

		driveKinematics = new SwerveDriveKinematics(
			// Front left
			new Translation2d(drivetrainTrackWidth / 2.0, drivetrainWheelBase / 2.0),
			// Front right
			new Translation2d(drivetrainTrackWidth / 2.0, -drivetrainWheelBase / 2.0),
			// Back left
			new Translation2d(-drivetrainTrackWidth / 2.0, drivetrainWheelBase / 2.0),
			// Back right
			new Translation2d(-drivetrainTrackWidth / 2.0, -drivetrainWheelBase / 2.0)
		);

		imu = new IMU(imuType, imuID);

		swerveDrive = new OdometeredSwerveDrive(
			frontLeftModule, frontRightModule,
			backLeftModule, backRightModule,
			driveKinematics, imu
		);

		swerveDrive.setOffsets(frontLeftOffset, frontRightOffset, backLeftOffset, backRightOffset);

		speeds = new ChassisSpeeds(0.0, 0.0, 0.0);

		logger = new LoggerGroup("Drivetrain", SMARTDASHBOARD, SHUFFLEBOARD);

		logger.putDouble("XPose", () -> swerveDrive.getPosition().getX());
		logger.putDouble("YPose", () -> swerveDrive.getPosition().getY());
		logger.putDouble("Yaw", () -> Math.toDegrees(swerveDrive.getRobotRotations().getZ()));
		logger.putDouble("Pitch", () -> Math.toDegrees(swerveDrive.getRobotRotations().getY()));
		logger.putDouble("Roll", () -> Math.toDegrees(swerveDrive.getRobotRotations().getX()));
	}

	@Override
	public void periodic() {
		speeds = ChassisSpeeds.fromFieldRelativeSpeeds(
			speeds.vyMetersPerSecond,-speeds.vxMetersPerSecond,
			speeds.omegaRadiansPerSecond, 
			drivetrain.getIMURotation()
		);

		swerveDrive.setOrientedVelocity(speeds);

		swerveDrive.update();

		SmartDashboard.putNumber("Angle", getIMURotation().getDegrees());
		SmartDashboard.putNumber("Other angle", imu.getAngle());
	}

	public void setDrive(ChassisSpeeds speeds) {
		this.speeds = speeds;
	}

	public void setUnorientedDrive(ChassisSpeeds speeds) {
		this.speeds = ChassisSpeeds.fromFieldRelativeSpeeds(
		  speeds, Rotation2d.fromDegrees(getIMURotation().getDegrees())
		);
	}

	public Command setGyroscopeZero(double angle) {
		return new InstantCommand(() -> swerveDrive.zeroIMU(angle), drivetrain);
	}

	public void resetPosition(Pose2d pose) {
		swerveDrive.resetPosition(pose);
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
		return driveKinematics;
	}

	public Pose2d getPose() {
		return swerveDrive.getPosition();
	}
	
	public Rotation2d getIMURotation() {
		return swerveDrive.getFieldRelativeAngle2d();
	}

	public LoggerGroup getLogger() {
		return logger;
	}
}
