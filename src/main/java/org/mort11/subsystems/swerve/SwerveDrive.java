package org.mort11.subsystems.swerve;

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

import static org.mort11.config.constants.PhysicalConstants.Drivetrain.*;
import static org.mort11.config.constants.PortConstants.Drivetrain.*;

public class SwerveDrive {
    public SwerveModule frontLeftModule;
    public SwerveModule frontRightModule;
    public SwerveModule backLeftModule;
    public SwerveModule backRightModule;

    public ChassisSpeeds velocity;

    public SwerveDriveKinematics kinematics;

    public ShuffleboardTab tab;
    
    public SwerveDrive () {
        frontLeftModule = new SwerveModule(FRONT_LEFT_DRIVE_MOTOR, FRONT_LEFT_STEER_MOTOR, FRONT_LEFT_ENCODER);
        frontRightModule = new SwerveModule(FRONT_RIGHT_DRIVE_MOTOR, FRONT_RIGHT_STEER_MOTOR, FRONT_RIGHT_ENCODER);
        backLeftModule = new SwerveModule(BACK_LEFT_DRIVE_MOTOR, BACK_LEFT_STEER_MOTOR, BACK_LEFT_ENCODER);
        backRightModule = new SwerveModule(BACK_RIGHT_DRIVE_MOTOR, BACK_RIGHT_STEER_MOTOR, BACK_RIGHT_ENCODER);

        frontLeftModule.setOffset(FRONT_LEFT_OFFSET);
        frontRightModule.setOffset(FRONT_RIGHT_OFFSET);
        backLeftModule.setOffset(BACK_LEFT_OFFSET);
        backRightModule.setOffset(BACK_RIGHT_OFFSET);

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

        velocity = new ChassisSpeeds(0, 0, 0);

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
		SwerveDriveKinematics.desaturateWheelSpeeds(states, MAX_SPEED);
        setStates(states);
    }

    public void setDescitizedVelocity(ChassisSpeeds velocity) {
        this.velocity = velocity;

        velocity = ChassisSpeeds.discretize(velocity, 0.02);
        SwerveModuleState[] states = kinematics.toSwerveModuleStates(velocity);
		SwerveDriveKinematics.desaturateWheelSpeeds(states, MAX_SPEED);
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

    public double to360(double in) {
        if (in < 360 && in >= 0) {
            return in;
        }

        else if (in < 0) {
            return to360(in + 360);
        }

        else {
            return to360(in - 360);
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
}
