package org.mort11.library.subsystems.swerve;

import org.mort11.library.subsystems.swerve.swervedrives.SwerveDrive;

import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N3;

public class Odometer {
    
    public SwerveDrivePoseEstimator swervePose;

    public double maxCamError;

    public Odometer(SwerveDriveKinematics kinematics, SwerveModulePosition[] modulePositions) {
        this(
            kinematics, Rotation2d.fromDegrees(0),
            modulePositions, new Pose2d()
        );
    }

    public Odometer(SwerveDrive swerveDrive) {
        this(
            swerveDrive.getKinematics(), Rotation2d.fromDegrees(0),
            swerveDrive.getModulePositions(), new Pose2d()
        );
    }

    public Odometer(
            SwerveDrive swerveDrive, Rotation2d rotation
        ) {
        this(
            swerveDrive.getKinematics(), rotation,
            swerveDrive.getModulePositions(), new Pose2d()
        );
    }

    public Odometer(
            SwerveDriveKinematics kinematics, Rotation2d rotation, 
            SwerveModulePosition[] modulePositions, Pose2d position
        ) {
        swervePose = new SwerveDrivePoseEstimator(
            kinematics, rotation, 
            modulePositions, position
        );
    }

    public Odometer(
            SwerveDrive swerveDrive, Rotation2d rotation, Pose2d position
        ) {
        swervePose = new SwerveDrivePoseEstimator(
            swerveDrive.getKinematics(), rotation, 
            swerveDrive.getModulePositions(), position
        );
    }
    
    public Odometer(
            SwerveDriveKinematics kinematics, Rotation2d rotation, 
            SwerveModulePosition[] modulePositions, Pose2d position,
            Matrix<N3, N1> posDeviation, Matrix<N3, N1> camDeviation
        ) {
        swervePose = new SwerveDrivePoseEstimator(
            kinematics, rotation, 
            modulePositions, position, 
            posDeviation, camDeviation
        );
    }

    public Odometer(
            SwerveDrive swerveDrive, Rotation2d rotation, Pose2d position,
            Matrix<N3, N1> posDeviation, Matrix<N3, N1> camDeviation
        ) {
        swervePose = new SwerveDrivePoseEstimator(
            swerveDrive.getKinematics(), rotation, 
            swerveDrive.getModulePositions(), position, 
            posDeviation, camDeviation
        );
    }

    public void resetPosition(Rotation2d angle, SwerveModulePosition[] modulePositions, Pose2d position) {
        swervePose.resetPosition(angle, modulePositions, position);
    }

    public void resetPosition(Rotation2d angle, SwerveDrive swerveDrive, Pose2d position) {
        swervePose.resetPosition(angle, swerveDrive.getModulePositions(), position);
    }

    public void setMaxCamError(double error) {
        maxCamError = error;
    }

    public Pose2d getPosition() {
        return swervePose.getEstimatedPosition();
    }

    public void update(Rotation2d angle, SwerveModulePosition[] modulePositions) {
        swervePose.update(angle, modulePositions);
    }

    public void update(Rotation2d angle, SwerveDrive swerveDrive) {
        swervePose.update(angle, swerveDrive.getModulePositions());
    }

    public void update(
            Rotation2d angle, SwerveDrive swerveDrive,
            Pose2d camPose, double timeStamp
        ) {
        update(
            angle, swerveDrive.getModulePositions(),
            camPose, timeStamp
        );
    }

    public void update(
            Rotation2d angle, SwerveModulePosition[] modulePositions, 
            Pose2d camPose, double timeStamp
        ) {
        swervePose.update(angle, modulePositions);

        if (
                Math.abs(getPosition().getX() - camPose.getX()) > maxCamError ||
                Math.abs(getPosition().getY() - camPose.getY()) > maxCamError
            ) {
            swervePose.addVisionMeasurement(camPose, timeStamp);
        }
    }
}
