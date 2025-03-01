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
import org.mort11.commands.autons.odometry.Start2F2RStation2E;
import org.mort11.commands.autons.pathplanned.BasicCommands;
import org.mort11.commands.autons.pathplanned.ScoreL4JDescoreKL;

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
            (Pose2d startPose) -> drivetrain.setRobotPosition(startPose), //reset odometry to a given pose. WILL ONLY RUN IF AUTON HAS A SET POSE, DOES NOTHING OTHERWISE. 
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
                    0.0515,
                    5,
                    WHEEL_COEFFICIENT_OF_FRICTION,
                    DCMotor.getKrakenX60(1).withReduction(5.472),
                    DRIVE_MOTOR_CURRENT_LIMIT,
                    1
                ),
                0.609
            ),
            // () -> (DriverStation.getAlliance().isPresent() ? DriverStation.getAlliance().get() == Alliance.Red : false), //method for checking current alliance. Path flips if alliance is red
            () -> false,

        drivetrain
        );

        // AutoBuilder.configure(
        //     () -> drivetrain.getOtherPose(),  //get current robot position on the field
        //     (Pose2d startPose) -> drivetrain.setRobotPosition(startPose), //reset odometry to a given pose. WILL ONLY RUN IF AUTON HAS A SET POSE, DOES NOTHING OTHERWISE. 
        //     () -> drivetrain.getChassisSpeeds(), //get the current ROBOT RELATIVE SPEEDS
        //     (ChassisSpeeds robotRelativeOutput, DriveFeedforwards feedForwards) -> drivetrain.setDrive(robotRelativeOutput), //makes the robot move given ROBOT RELATIVE CHASSISSPEEDS
        //     new PPHolonomicDriveController(
        //         new PIDConstants(AUTON_POS_KP, AUTON_POS_KI, AUTON_POS_KD),
        //         new PIDConstants(AUTON_ROTATION_KP, AUTON_ROTATION_KI, AUTON_ROTATION_KD)
        //     ),
        //     new RobotConfig(
        //         ROBOT_MASS,
        //         ROBOT_MOMENT_OF_INERTIA,
        //         new ModuleConfig(
        //             0.0515,
        //             5,
        //             WHEEL_COEFFICIENT_OF_FRICTION,
        //             DCMotor.getKrakenX60(1).withReduction(5.472),
        //             DRIVE_MOTOR_CURRENT_LIMIT,
        //             1
        //         ),
        //         0.609
        //     ),
        //     () -> (DriverStation.getAlliance().isPresent() ? DriverStation.getAlliance().get() == Alliance.Red : false), //method for checking current alliance. Path flips if alliance is red
        // drivetrain
        // );
	}
	
	public static void addAutoOptions () {
		autoChooser = new SendableChooser<Command>();
		pathAutoChooser = AutoBuilder.buildAutoChooser("Forward");

		autoChooser.setDefaultOption("nothing", null);
		
		autoChooser.addOption("Timed Taxi", new Taxi());
		autoChooser.addOption("Timed One Piece Blue", new BlueSideOnePiece());
        autoChooser.addOption("Timed One Piece Red", new RedSideOnePiece());


		//ODOMETRY

		autoChooser.addOption("Odometry Auto", new Start2F2RStation2E());
        





		//PATHPLANNED

		BasicCommands.setCommands();

		// autoChooser.addOption("ScoreL4JDescoreKL", 
		// 	new ScoreL4JDescoreKL()
		// );

		autoChooser.addOption("Pathplanner Auto Forward", new PathPlannerAuto("Forward"));
        autoChooser.addOption("ScoreL4DescoreKL Auto", new PathPlannerAuto("ScoreL4JDescoreKL"));
        autoChooser.addOption("BBarge2H", new PathPlannerAuto("BBarge2H"));

		autoChooser.addOption("Forward Path", getPathCommand());

        autoChooser.addOption("Start Auto", new PathPlannerAuto("Start2F2RStation2E"));
		



		SmartDashboard.putData("Auton Chooser", autoChooser);

		// SmartDashboard.putData("Pathplanner Auton Chooser", pathAutoChooser);
	}

	public static Command getPlanned(String plan) {
		BasicCommands.setCommands();

		return new PathPlannerAuto(plan);
	}

	public static Command getPathCommand(){
		try {
			return AutoBuilder.followPath(PathPlannerPath.fromPathFile("Forward"));
		} catch (Exception e) {
			DriverStation.reportError(e.getMessage(), e.getStackTrace());
			return autoChooser.getSelected();
		}
	}

	public static Command getAutonomousCommand () {
		return autoChooser.getSelected();
	}

}
