package org.mort11.config;

import static org.mort11.config.constants.PhysicalConstants.Drivetrain.*;
import static org.mort11.config.constants.PIDConstants.Drivetrain.*;

import com.pathplanner.lib.commands.PathPlannerAuto;
import com.pathplanner.lib.config.PIDConstants;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;

import org.mort11.MORTlib.subsystems.swerve.PathPlanner;
import org.mort11.commands.autons.timed.Taxi;
import org.mort11.subsystems.Drivetrain;
import org.mort11.commands.autons.pathplanned.BasicCommands;


public class Auto {

	private static Drivetrain drivetrain;

	private static SendableChooser<Command> autoChooser;
	
	public static void configure() {
		drivetrain = Drivetrain.getInstance();

		configureAutoBuilder();
		addAutoOptions();
	}

	public static void configureAutoBuilder() {
		drivetrain.setGyroscopeZero(0);

		PathPlanner.configure(
			drivetrain, drivetrain.getSwerveDrive(),
			new PIDConstants(AUTON_POS_KP, AUTON_POS_KI, AUTON_POS_KD), 
			new PIDConstants(AUTON_ROTATION_KP, AUTON_ROTATION_KI, AUTON_ROTATION_KD),
			WHEEL_COEFFICIENT_OF_FRICTION, DRIVE_MOTOR_CURRENT_LIMIT,
			ROBOT_MASS, ROBOT_MOMENT_OF_INERTIA
		);
	}
	
	public static void addAutoOptions () {
		autoChooser = new SendableChooser<Command>();

		autoChooser.setDefaultOption("nothing", null);

		autoChooser.addOption("Forward", new Taxi());
		// autoChooser.addOption("Circle", GetPlanned.getCircle());

		SmartDashboard.putData(autoChooser);
	}

	public static Command getPlanned(String plan) {
		BasicCommands.setCommands();

		return new PathPlannerAuto(plan);
	}

	public static Command getAutonomousCommand () {
		return autoChooser.getSelected();
	}
}
