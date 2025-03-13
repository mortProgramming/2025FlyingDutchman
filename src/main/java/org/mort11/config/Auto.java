package org.mort11.config;

import static org.mort11.config.constants.PhysicalConstants.Drivetrain.*;
import static org.mort11.config.constants.PIDConstants.Drivetrain.*;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.commands.PathPlannerAuto;
import com.pathplanner.lib.config.ModuleConfig;
import com.pathplanner.lib.config.PIDConstants;
import com.pathplanner.lib.config.RobotConfig;
import com.pathplanner.lib.controllers.PPHolonomicDriveController;
import com.pathplanner.lib.path.PathPlannerPath;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;

import org.mort11.commands.autons.timed.BlueSideOnePiece;
import org.mort11.commands.autons.timed.RedSideOnePiece;
import org.mort11.commands.autons.timed.Taxi;
import org.mort11.library.subsystems.swerve.PathPlanner;
import org.mort11.subsystems.swerve.Drivetrain;
import org.mort11.commands.autons.odometry.Calibrate;
import org.mort11.commands.autons.odometry.blue.*;
import org.mort11.commands.autons.odometry.red.*;
import org.mort11.commands.autons.pathplanned.BasicCommands;
// import org.mort11.commands.autons.timed.CenterOnePiece;
import org.mort11.commands.autons.odometry.CenterOnePiece;

import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.util.DriveFeedforwards;


public class Auto {

	private static Drivetrain drivetrain;

	private static SendableChooser<Command> autoChooser;
	private static SendableChooser<Command> pathAutoChooser;
	
	public static void configure() {
		drivetrain = Drivetrain.getInstance();

		configureAutoBuilder();
		addAutoOptions();
	}

	public static void configureAutoBuilder() {
        AutoBuilder.configure(
            () -> drivetrain.getPose(),  //get current robot position on the field
            // () -> drivetrain.getPathPose(),  //get current robot position on the field
            (Pose2d startPose) -> drivetrain.setRobotPosition(startPose), //reset odometry to a given pose. WILL ONLY RUN IF AUTON HAS A SET POSE, DOES NOTHING OTHERWISE. 
            // (Pose2d startPose) -> drivetrain.setDriveRobotPosition(startPose), //reset odometry to a given pose. WILL ONLY RUN IF AUTON HAS A SET POSE, DOES NOTHING OTHERWISE. 
            () -> drivetrain.getChassisSpeeds(), //get the current ROBOT RELATIVE SPEEDS
            (ChassisSpeeds robotRelativeOutput, DriveFeedforwards feedForwards) -> drivetrain.setDrivePathPlanner(robotRelativeOutput), //makes the robot move given ROBOT RELATIVE CHASSISSPEEDS
            new PPHolonomicDriveController(
                new PIDConstants(AUTON_POS_KP, AUTON_POS_KI, AUTON_POS_KD),
                new PIDConstants(AUTON_ROTATION_KP, AUTON_ROTATION_KI, AUTON_ROTATION_KD)
            ),
            new RobotConfig(
                ROBOT_MASS,
                ROBOT_MOMENT_OF_INERTIA,
                new ModuleConfig(
                    WHEEL_DIAMETER / 2,
                    MAX_SPEED,
                    WHEEL_COEFFICIENT_OF_FRICTION,
                    // DCMotor.getKrakenX60(1).withReduction(5.472),
                    DCMotor.getKrakenX60(1).withReduction(1 / DRIVE_REDUCTION),
                    DRIVE_MOTOR_CURRENT_LIMIT,
                    1
                ),
                DRIVETRAIN_WHEELBASE_METERS
            ),
            // () -> (DriverStation.getAlliance().isPresent() ? DriverStation.getAlliance().get() == Alliance.Red : false), //method for checking current alliance. Path flips if alliance is red
            () -> false,
        drivetrain
        );
	}
	
	public static void addAutoOptions () {
		autoChooser = new SendableChooser<Command>();

		autoChooser.setDefaultOption("nothing", null);
		
		autoChooser.addOption("Timed Taxi", new Taxi());
		// autoChooser.addOption("Timed One Piece Blue", new BlueSideOnePiece());
        // autoChooser.addOption("Timed One Piece Red", new RedSideOnePiece());

		//ODOMETRY

        // autoChooser.addOption("Blue Right One Piece", new RightOneBlue());
        // autoChooser.addOption("Red Right One Piece", new RightOneRed());
		autoChooser.addOption("Blue Right Two Piece", new RightTwoBlue());
        autoChooser.addOption("Red Right Two Piece", new RightTwoRed());
        // autoChooser.addOption("Red Left Two Piece", new LeftTwoRed());
        autoChooser.addOption("Center One Piece", new CenterOnePiece());

        autoChooser.addOption("Path?", new PathPlannerAuto("Forward"));

        // autoChooser.addOption("Calibrate", new Calibrate());
    
		SmartDashboard.putData("Auton Chooser", autoChooser);
	}

	public static Command getPlanned(String plan) {
		BasicCommands.setCommands();

		return new PathPlannerAuto(plan);
	}

	public static Command getAutonomousCommand () {
		return autoChooser.getSelected();
	}

}
