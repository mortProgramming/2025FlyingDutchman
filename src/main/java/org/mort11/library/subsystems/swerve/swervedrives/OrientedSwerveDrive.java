package org.mort11.library.subsystems.swerve.swervedrives;

import org.mort11.library.hardware.encoder.EncoderTypeEnum;
import org.mort11.library.hardware.imu.IMU;
import org.mort11.library.hardware.imu.IMUTypeEnum;
import org.mort11.library.hardware.motor.MotorTypeEnum;
import org.mort11.library.subsystems.swerve.ModuleConfigEnum;
import org.mort11.library.subsystems.swerve.SwerveModule;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;

public class OrientedSwerveDrive extends SwerveDrive {

    public IMU imu;

    public double fieldOrientationOffset;
    
    public OrientedSwerveDrive (
            MotorTypeEnum frontLeftDriveMotorType, int frontLeftDriveMotorID, 
            MotorTypeEnum frontLeftSteerMotorType, int frontLeftSteerMotorID,
            EncoderTypeEnum frontLeftEncoderType, int frontLeftEncoderID,
            ModuleConfigEnum frontLeftModuleType,

            MotorTypeEnum frontRightDriveMotorType, int frontRightDriveMotorID, 
            MotorTypeEnum frontRightSteerMotorType, int frontRightSteerMotorID,
            EncoderTypeEnum frontRightEncoderType, int frontRightEncoderID,
            ModuleConfigEnum frontRightModuleType,

            MotorTypeEnum backLeftDriveMotorType, int backLeftDriveMotorID, 
            MotorTypeEnum backLeftSteerMotorType, int backLeftSteerMotorID,
            EncoderTypeEnum backLeftEncoderType, int backLeftEncoderID,
            ModuleConfigEnum backLeftModuleType,

            MotorTypeEnum backRightDriveMotorType, int backRightDriveMotorID, 
            MotorTypeEnum backRightSteerMotorType, int backRightSteerMotorID,
            EncoderTypeEnum backRightEncoderType, int backRightEncoderID,
            ModuleConfigEnum backRightModuleType,

            double robotLength,
            double robotWidth,

            IMUTypeEnum imuType, int imuID

        ) {
        this(
            new SwerveModule(
                frontLeftDriveMotorType, frontLeftDriveMotorID,
                frontLeftSteerMotorType, frontLeftSteerMotorID,
                frontLeftEncoderType, frontLeftEncoderID,
                frontLeftModuleType
            ), 
            new SwerveModule(
                frontRightDriveMotorType, frontRightDriveMotorID,
                frontRightSteerMotorType, frontRightSteerMotorID,
                frontRightEncoderType, frontRightEncoderID,
                frontRightModuleType
            ), 
            new SwerveModule(
                backLeftDriveMotorType, backLeftDriveMotorID,
                backLeftSteerMotorType, backLeftSteerMotorID,
                backLeftEncoderType, backLeftEncoderID,
                backLeftModuleType
            ), 
            new SwerveModule (
                backRightDriveMotorType, backRightDriveMotorID,
                backRightSteerMotorType, backRightSteerMotorID,
                backRightEncoderType, backRightEncoderID,
                backRightModuleType
            ), 
            new SwerveDriveKinematics(
				// Front left
				new Translation2d(robotWidth / 2.0, robotLength / 2.0),
				// Front right
				new Translation2d(robotWidth / 2.0, -robotLength / 2.0),
				// Back left
				new Translation2d(-robotWidth / 2.0, robotLength / 2.0),
				// Back right
				new Translation2d(-robotWidth / 2.0, -robotLength / 2.0)
            ),
            new IMU(imuType, imuID)
        );
    }
    
    public OrientedSwerveDrive (
            SwerveModule frontLeftModule, SwerveModule frontRightModule, 
            SwerveModule backLeftModule, SwerveModule backRightModule, 
            SwerveDriveKinematics kinematics, IMU imu
        ) {
            super(frontLeftModule, frontRightModule, 
            backLeftModule, backRightModule, 
            kinematics);

            this.imu = imu;
            fieldOrientationOffset = 0;
    }

    public void zeroIMU (double angle) {
        fieldOrientationOffset = getFieldRelativeAngle() + angle;
    }

    public double getFieldRelativeAngle () {
        return imu.getAngle() - fieldOrientationOffset;
    }

    public Rotation2d getFieldRelativeAngle2d () {
        return Rotation2d.fromDegrees(imu.getAngle() - fieldOrientationOffset);
    }

    public Rotation3d getRobotRotations () {
        return imu.getRotation3d();
    }

    public void setOrientedVelocity (ChassisSpeeds velocity) {
        velocity = ChassisSpeeds.fromFieldRelativeSpeeds(velocity, getFieldRelativeAngle2d());
        setVelocity(velocity);
    }

    @Override
    public void setCanivore(String canivore) {
        super.setCanivore(canivore);
        imu.setCanivore(canivore);
    }
}
