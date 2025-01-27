package org.mort11.library.subsystems.swerve.swervedrives;

import org.mort11.library.hardware.encoder.EncoderTypeEnum;
import org.mort11.library.hardware.motor.MotorTypeEnum;
import org.mort11.library.subsystems.swerve.ModuleConfigEnum;
import org.mort11.library.subsystems.swerve.SwerveModule;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public class SwerveDrive {
    public SwerveModule frontLeftModule;
    public SwerveModule frontRightModule;
    public SwerveModule backLeftModule;
    public SwerveModule backRightModule;

    public ChassisSpeeds velocity;

    public SwerveDriveKinematics kinematics;

    public double descritizedValue;

    public SlewRateLimiter tangentRateLimiter;
    public SlewRateLimiter rotationRateLimiter;

    public double tangentRateLimiterLimit;
    public double rotationRateLimiterLimit;

    public ShuffleboardTab tab;
    
    public SwerveDrive (
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
            double robotWidth

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
            )
        );
    }
    
    public SwerveDrive (
            SwerveModule frontLeftModule, SwerveModule frontRightModule, 
            SwerveModule backLeftModule, SwerveModule backRightModule, 
            SwerveDriveKinematics kinematics
        ) {
            this.frontLeftModule = frontLeftModule;
            this.frontRightModule = frontRightModule;
            this.backLeftModule = backLeftModule;
            this.backRightModule = backRightModule;

            this.kinematics = kinematics;

        velocity = new ChassisSpeeds(0, 0, 0);

        descritizedValue = 0.02;

        tangentRateLimiterLimit = 1000000;
        rotationRateLimiterLimit = 1000000;

        tangentRateLimiter = new SlewRateLimiter(tangentRateLimiterLimit);
        rotationRateLimiter = new SlewRateLimiter(rotationRateLimiterLimit);

        tab = Shuffleboard.getTab("Drivetrain");
        makeShuffleboardTab();
    }

    public void makeShuffleboardTab() {
        makeShuffleboardModuleLayout("Front Left Module", 0);
        makeShuffleboardModuleLayout("Front Right Module", 1);
        makeShuffleboardModuleLayout("Back Left Module", 2);
        makeShuffleboardModuleLayout("Back Right Module", 3);

        ShuffleboardLayout layout = tab.getLayout("Overall", BuiltInLayouts.kList);
        layout.withSize(2, 4).withPosition(0, 5);
        layout.addNumber("X Velocity", () -> velocity.vxMetersPerSecond);
        layout.addNumber("Y Velocity", () -> velocity.vyMetersPerSecond);
        layout.addNumber("Rotational Velocity", () -> velocity.omegaRadiansPerSecond);
    }

    public void makeShuffleboardModuleLayout(String layoutName, int moduleNumber) {
        ShuffleboardLayout layout = tab.getLayout(layoutName, BuiltInLayouts.kList);
        layout.withSize(2, 4).withPosition(moduleNumber * 2, 0);
        layout.addNumber("Current Velocity RPM", () -> getModule(moduleNumber).getDriveVelocityRPM());
        layout.addNumber("Current Velocity MPerS", () -> getModule(moduleNumber).state.speedMetersPerSecond);
        layout.addNumber("Current Position", () -> to360(getModule(moduleNumber).getEncoderPosition().getDegrees()));
        layout.addNumber("Wanted Position", () -> to360(getModule(moduleNumber).state.angle.getDegrees()));
    }

    public void setVelocity(ChassisSpeeds velocity) {
        this.velocity = velocity;

        SwerveModuleState[] states = kinematics.toSwerveModuleStates(velocity);
		SwerveDriveKinematics.desaturateWheelSpeeds(states, getModule(0).maxSpeed);
        setStates(states);
    }

    public void setVelocity (double x, double y, double omega) {
        this.velocity = new ChassisSpeeds(x, y, omega);

        SwerveModuleState[] states = kinematics.toSwerveModuleStates(velocity);
		SwerveDriveKinematics.desaturateWheelSpeeds(states, getModule(0).maxSpeed);
        setStates(states);
    }

    public void setDescitizedVelocity(ChassisSpeeds velocity) {
        this.velocity = velocity;

        velocity = ChassisSpeeds.discretize(velocity, descritizedValue);
        SwerveModuleState[] states = kinematics.toSwerveModuleStates(velocity);
		SwerveDriveKinematics.desaturateWheelSpeeds(states, getModule(0).maxSpeed);
        setStates(states);
    }

    public void setLimitedVelocity(ChassisSpeeds velocity) {
        this.velocity = velocity;
        
        double tangentalVelocity = Math.hypot(velocity.vxMetersPerSecond, velocity.vyMetersPerSecond);
        double directionRadians = Math.atan(velocity.vyMetersPerSecond / velocity.vxMetersPerSecond);
        if (velocity.vxMetersPerSecond < 0) {
            directionRadians += Math.PI;
        }
        double limitedTangentalVelocity = tangentRateLimiter.calculate(tangentalVelocity);

        velocity = new ChassisSpeeds(
            limitedTangentalVelocity * Math.cos(directionRadians), 
            limitedTangentalVelocity * Math.sin(directionRadians), 
            rotationRateLimiter.calculate(velocity.omegaRadiansPerSecond)
        );
        velocity = ChassisSpeeds.discretize(velocity, descritizedValue);
        SwerveModuleState[] states = kinematics.toSwerveModuleStates(velocity);
		SwerveDriveKinematics.desaturateWheelSpeeds(states, getModule(0).maxSpeed);
        setStates(states);
    }

    public void setStates(SwerveModuleState[] states) {
        for (int i = 0; i < 4; i++) {
            getModule(i).setModuleState(states[i]);
        }
    }

    public void setDriveSpeeds(double[] driveSpeeds) {
        for (int i = 0; i < 4; i++) {
            getModule(i).setDriveSpeedMeters(driveSpeeds[i]);
        }
    }

    public void setSteerPositions(Rotation2d[] rotations) {
        for (int i = 0; i < 4; i++) {
            getModule(i).setPosition(rotations[i]);
        }
    }

    public void setDescritizedValue(double value) {
        descritizedValue = value;
    }

    public void setRateLimits(double tangentRateLimiterLimit, double rotationRateLimiterLimit) {
        this.tangentRateLimiterLimit = tangentRateLimiterLimit;
        this.rotationRateLimiterLimit = rotationRateLimiterLimit;

        tangentRateLimiter = new SlewRateLimiter(tangentRateLimiterLimit);
        rotationRateLimiter = new SlewRateLimiter(rotationRateLimiterLimit);
    }

    public void setOffsets(double[] offsets) {
        for (int i = 0; i < 4; i++) {
            getModule(i).setOffset(offsets[i]);
        }
    }

    public void setOffsets(
            double frontLeftOffset, double frontRightOffset, double backLeftOffset, double backRightOffset
        ) {
        frontLeftModule.setOffset(frontLeftOffset);
        frontRightModule.setOffset(frontRightOffset);
        backLeftModule.setOffset(backLeftOffset);
        backRightModule.setOffset(backRightOffset);
    }

    public double to360(double in) {
        if (in < 360 && in >= 0) {
            return in;
        }

        else if (in < 0) {
            return to360(in += 360);
        }

        else {
            return to360(in -= 360);
        }
    }



    public SwerveDriveKinematics getKinematics() {
        return kinematics;
    }

    public SwerveModule getModule(int num) {
        switch (num) {
            case 0:
                return frontLeftModule;
            case 1:
                return frontRightModule;
            case 2:
                return backLeftModule;
            case 3:
                return backRightModule;
            default:
                return frontLeftModule;

        }
    }

    public SwerveModulePosition[] getModulePositions() {
        return new SwerveModulePosition[] {
            frontLeftModule.getPosition(), 
            frontRightModule.getPosition(), 
            backLeftModule.getPosition(), 
            backRightModule.getPosition()
        };
    }

    public void setCanivore(String canivore) {
        frontLeftModule.driveMotor.setCanivore(canivore);
        frontRightModule.driveMotor.setCanivore(canivore);
        backLeftModule.driveMotor.setCanivore(canivore);
        backRightModule.driveMotor.setCanivore(canivore);

        frontLeftModule.steerMotor.setCanivore(canivore);
        frontRightModule.steerMotor.setCanivore(canivore);
        backLeftModule.steerMotor.setCanivore(canivore);
        backRightModule.steerMotor.setCanivore(canivore);

        frontLeftModule.encoder.setCanivore(canivore);
        frontRightModule.encoder.setCanivore(canivore);
        backLeftModule.encoder.setCanivore(canivore);
        backRightModule.encoder.setCanivore(canivore);
    }

    public static void setCanivore(SwerveModule module, String canivore) {
        module.driveMotor.setCanivore(canivore);
        module.steerMotor.setCanivore(canivore);
        module.encoder.setCanivore(canivore);
    }
}
