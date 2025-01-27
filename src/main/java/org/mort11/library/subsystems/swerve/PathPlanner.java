package org.mort11.library.subsystems.swerve;

import org.mort11.library.subsystems.swerve.swervedrives.OdometeredSwerveDrive;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.config.ModuleConfig;
import com.pathplanner.lib.config.PIDConstants;
import com.pathplanner.lib.config.RobotConfig;
import com.pathplanner.lib.controllers.PPHolonomicDriveController;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class PathPlanner {
    public static void configure (
            Subsystem drivetrain, OdometeredSwerveDrive swerveDrive, 
            PIDConstants translationalConstants, PIDConstants rotationalConstants, 
            double wheelCOF, double driveMotorCurrentLimit, double massKG, double MOI
        ) {

        AutoBuilder.configure(
            () -> swerveDrive.getPosition(),  //get current robot position on the field
            (Pose2d startPose) -> swerveDrive.resetPosition(startPose), //reset odometry to a given pose. WILL ONLY RUN IF AUTON HAS A SET POSE, DOES NOTHING OTHERWISE. 
            () -> swerveDrive.velocity, //get the current ROBOT RELATIVE SPEEDS
            (ChassisSpeeds robotRelativeOutput) -> swerveDrive.setVelocity(robotRelativeOutput), //makes the robot move given ROBOT RELATIVE CHASSISSPEEDS
            new PPHolonomicDriveController(
                translationalConstants,
                rotationalConstants
            ),
            new RobotConfig(
                massKG,
                MOI,
                new ModuleConfig(
                    swerveDrive.getModule(0).getModuleConfig().WHEEL_DIAMETER,
                    swerveDrive.getModule(0).maxSpeed,
                    wheelCOF,
                    DCMotor.getKrakenX60(1),
                    driveMotorCurrentLimit,
                    1
                ),
                swerveDrive.kinematics.getModules()[0].getX() * 2
            ),
            () -> (DriverStation.getAlliance().isPresent() ? DriverStation.getAlliance().get() == Alliance.Red : false), //method for checking current alliance. Path flips if alliance is red
            drivetrain
        );
    }
}
