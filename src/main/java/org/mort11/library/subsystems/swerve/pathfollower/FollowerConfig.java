package org.mort11.library.subsystems.swerve.pathfollower;

import java.util.function.Consumer;
import java.util.function.Supplier;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;

public class FollowerConfig {

    public Supplier<Pose2d> poseSupplier;
    public Supplier<ChassisSpeeds> speedSupplier;
    public Consumer<ChassisSpeeds> speedConsumer;

    public int precision;

    public double maxVelocity, maxAcceleration;

    public double longitudinal_kp, longitudinal_ki, longitudinal_kd;
    public double lateral_kp, lateral_ki, lateral_kd;
    public double rotational_kp, rotational_ki, rotational_kd;
    
    public FollowerConfig(
            Supplier<Pose2d> poseSupplier, Supplier<ChassisSpeeds> speedSupplier, 
            Consumer<ChassisSpeeds> speedConsumer, int precision, 
            double maxVelocity, double maxAcceleration,
            double longitudinal_kp, double longitudinal_ki, double longitudinal_kd,
            double lateral_kp, double lateral_ki, double lateral_kd,
            double rotational_kp, double rotational_ki, double rotational_kd
        ) {

        this.poseSupplier = poseSupplier;
        this.speedSupplier = speedSupplier;
        this.speedConsumer = speedConsumer;

        this.maxVelocity = maxVelocity;
        this.maxAcceleration = maxAcceleration;

        this.longitudinal_kp = longitudinal_kp;
        this.longitudinal_ki = longitudinal_ki;
        this.longitudinal_kd = longitudinal_kd;

        this.lateral_kp = lateral_kp;
        this.lateral_ki = lateral_ki;
        this.lateral_kd = lateral_kd;

        this.rotational_kp = rotational_kp;
        this.rotational_ki = rotational_ki;
        this.rotational_kd = rotational_kd;
    }
}
