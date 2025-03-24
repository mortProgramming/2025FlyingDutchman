package org.mort11.library.subsystems.swerve.pathfollower;

import java.util.function.Consumer;
import java.util.function.Supplier;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;

public class FollowerConfig {

    public Supplier<Pose2d> poseSupplier;
    public Supplier<ChassisSpeeds> speedSupplier;
    public Consumer<ChassisSpeeds> speedConsumer;

    public int precision;
    
    public FollowerConfig(Supplier<Pose2d> poseSupplier, Supplier<ChassisSpeeds> speedSupplier, Consumer<ChassisSpeeds> speedConsumer, int precision) {
        this.poseSupplier = poseSupplier;
        this.speedSupplier = speedSupplier;
        this.speedConsumer = speedConsumer;
    }
}
