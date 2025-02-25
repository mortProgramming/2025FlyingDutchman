package org.mort11.library.subsystems.swerve.swervedrives;

import org.mort11.library.hardware.encoder.EncoderTypeEnum;
import org.mort11.library.hardware.imu.IMU;
import org.mort11.library.hardware.imu.IMUTypeEnum;
import org.mort11.library.hardware.motor.MotorTypeEnum;
import org.mort11.library.subsystems.swerve.ModuleConfigEnum;
import org.mort11.library.subsystems.swerve.Odometer;
import org.mort11.library.subsystems.swerve.SwerveModule;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.trajectory.TrapezoidProfile.Constraints;

public class OdometeredSwerveDrive extends OrientedSwerveDrive {
    public Odometer odometer;

    public ProfiledPIDController xController, yController, rotationController;
    
    public OdometeredSwerveDrive (
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
    
    public OdometeredSwerveDrive (
            SwerveModule frontLeftModule, SwerveModule frontRightModule, 
            SwerveModule backLeftModule, SwerveModule backRightModule, 
            SwerveDriveKinematics kinematics, IMU imu
        ) {
        super(frontLeftModule, frontRightModule, 
            backLeftModule, backRightModule, 
            kinematics, imu
        );

        odometer = new Odometer(getKinematics(), getModulePositions());
    }

    public void setProfiledPIDValues (
            double translationalP, double translationalI, double translationalD, double translationalV, double translationalA,
            double rotationalP, double rotationalI, double rotationalD, double rotationalV, double rotationalA
        ) {
        xController = new ProfiledPIDController(translationalP, translationalI, translationalD, new Constraints(translationalV, translationalA));
        yController = new ProfiledPIDController(translationalP, translationalI, translationalD, new Constraints(translationalV, translationalA));
        rotationController = new ProfiledPIDController(rotationalP, rotationalI, rotationalD, new Constraints(rotationalV, rotationalA));
        rotationController.enableContinuousInput(-180, 180);
    }

    public void moveToPosition(Pose2d position) {
        setOrientedVelocity(new ChassisSpeeds(
            xController.calculate(getPosition().getX(), position.getX()),
            yController.calculate(getPosition().getY(), position.getY()),
            rotationController.calculate(getFieldRelativeAngle2d().getDegrees(), position.getRotation().getDegrees())
        ));
    }

    public void resetPosition(Pose2d position) {
        odometer.resetPosition(getFieldRelativeAngle2d(), getModulePositions(), position);
    }

    public void setMaxCamError(double error) {
        odometer.setMaxCamError(error);
    }

    public Pose2d getPosition() {
        return odometer.getPosition();
    }

    public void update() {
        odometer.update(getFieldRelativeAngle2d(), getModulePositions());
    }

    public void update(
            Pose2d camPose, double timeStamp
        ) {
        odometer.update(
            getFieldRelativeAngle2d(), getModulePositions(),
            camPose, timeStamp
        );
    }


}
