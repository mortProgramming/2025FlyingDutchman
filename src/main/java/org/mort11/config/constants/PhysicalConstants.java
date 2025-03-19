package org.mort11.config.constants;

import edu.wpi.first.math.util.Units;

public final class PhysicalConstants {

	public static final double ROBOT_VOLTAGE = 12;

	public final static class AlgaeArm {

		public static final double OFFSET = -197;

		public static final double ALGAE_REST = 95;

		public static final double ARM_TOP_NEVER_POSITION = -120;
		public static final double ARM_BOTTOM_NEVER_POSITION = -300;

		public static final double ALGAE_REEF_INTAKE = 45;
		public static final double ALGAE_FLOOR_INTAKE = -15;

		public static final double ALGAE_PROCESSOR_SCORE = 0;
		public static final double ALGAE_BARGE_SCORE = 45;
	} 

	public final static class AlgaeRoller {
		public static final double INTAKE_SPEED = -0.6;
		public static final double OUTAKE_SPEED = 0.8;
	} 

	public final static class Drivetrain {
        // The left-to-right distance between the drivetrain wheels measured from center
		// to center.
		public static final double DRIVETRAIN_TRACKWIDTH_METERS = Units.inchesToMeters(27.75);
		// The front-to-back distance between the drivetrain wheels measured from center
		// to center.
		public static final double DRIVETRAIN_WHEELBASE_METERS = Units.inchesToMeters(23.75);

		public static final double DRIVEBASE_RADIUS_METERS = Math.hypot(
			DRIVETRAIN_TRACKWIDTH_METERS / 2.0, DRIVETRAIN_WHEELBASE_METERS / 2.0
		);

		public static final double FRONT_LEFT_OFFSET = 293.9;
		public static final double FRONT_RIGHT_OFFSET = 273.1;
		public static final double BACK_LEFT_OFFSET = 223.3;
		public static final double BACK_RIGHT_OFFSET = 255.5;

		public static final int IMU_TO_ROBOT_FRONT_ANGLE = 270;

		public static final double WHEEL_COEFFICIENT_OF_FRICTION = 1;
		public static final double ROBOT_MASS = 62.1;
		public static final double ROBOT_MOMENT_OF_INERTIA = ROBOT_MASS * 0.254 * 0.254 / 2;
		public static final double DRIVE_MOTOR_CURRENT_LIMIT = 60;
		public static final double DRIVE_MOTOR_MAX_RPM = 6000;

		public static final double DRIVE_REDUCTION = (16.0 / 50.0) * (28.0 / 16.0) * (15.0 / 45.0);
		public static final double WHEEL_DIAMETER = 0.1014;
		public static final double ROTATIONS_TO_METERS = WHEEL_DIAMETER * Math.PI;
		public static final double MAX_SPEED = DRIVE_REDUCTION * ROTATIONS_TO_METERS * (DRIVE_MOTOR_MAX_RPM / 60);

		public static final double ODOMETRY_MULTIPLIER = 5.67;
		// public static final double ODOMETRY_MULTIPLIER = 5.575;
    }	

	public static final class Elevator {
		//all in inches

		//3 is elevator stage count
		public static final double ROTATIONS_TO_INCHES = 5.642 * 3;
		public static final double MAXIMUM_INCH_CHANGE = 5; //The maximum inch displacement for every 0.02 sec cycle
		public static final double GEAR_RATIO = 16;

		public static final double ELEVATOR_OFFSET = 16.5;
		public static final double ELEVATOR_REST_HEIGHT = 5.6;
		public static final double ELEVATOR_START_HEIGHT = 2;

		public static final double ELEVATOR_L1_HEIGHT = 13;
		public static final double ELEVATOR_L2_HEIGHT = 27.6;
		public static final double ELEVATOR_L3_HEIGHT = 43.6;
		public static final double ELEVATOR_L4_HEIGHT = 68;
		public static final double ELEVATOR_INTAKE_HEIGHT = 17.4;
		public static final double ELEVATOR_AUTO_INTAKE_HEIGHT = 19;

		public static final double ELEVATOR_LOW_ALGAE_HEIGHT = 4.4;
		public static final double ELEVATOR_HIGH_ALGAE_HEIGHT = 22.3;
		public static final double ELEVATOR_PROCESSOR_HEIGHT = 0;
		public static final double ELEVATOR_BARGE_HEIGHT = 72;
		public static final double ELEVATOR_FLOOR_HEIGHT = 0;

		//everything is in inches

		//unused for offset until fixed
		// public static final double ELEVATOR_TO_ZERO = 0;

		// public static final double ELEVATOR_OFFSET = -15.4;
		// public static final double ELEVATOR_REST_HEIGHT = 5.5;
		// public static final double ELEVATOR_START_HEIGHT = 2;

		// public static final double ELEVATOR_L1_HEIGHT = 13;
		// public static final double ELEVATOR_L2_HEIGHT = 30.5; // Elevator height at L2
		// public static final double ELEVATOR_L3_HEIGHT = 46; // Elevator height at L3
		// public static final double ELEVATOR_L4_HEIGHT = 72; // Elevator height at L4
		// public static final double ELEVATOR_INTAKE_HEIGHT = 20.5; // Elevator Intake height in teleop
		// public static final double ELEVATOR_AUTO_INTAKE_HEIGHT = 19; // Elevator height to intake in auton

		// public static final double ELEVATOR_LOW_ALGAE_HEIGHT = 13.5; // Elevator height at low algae height
		// public static final double ELEVATOR_HIGH_ALGAE_HEIGHT = 29; // Elevator height at high algae height 
		// public static final double ELEVATOR_PROCESSOR_HEIGHT = 0; // Elevator height at processor height for algae
		// public static final double ELEVATOR_BARGE_HEIGHT = 72; // Elevator height at barge height for algae
		// public static final double ELEVATOR_FLOOR_HEIGHT = 0; // Elevator height at floor intake for algae
	}

	public final static class TikiTorchArm {

		public static final double OFFSET = 284; //The amount in degrees to ensure that the algae arm reads 0 when flat

		public static final double TIKI_REST = 59.3; //The rest position of the tiki torch in degrees

		public static final double ARM_TOP_NEVER_POSITION = 45; // Position in degrees where the tiki torch cannot go to (top)
		public static final double ARM_BOTTOM_NEVER_POSITION = 0; //Position in degeres where the tiki torch cannot go to (bottom)

		public static final double TIKI_L234_SCORE = -58; // Position in degrees where the tiki torch goes to score at L2 3 and 4
		public static final double TIKI_AUTO_L234_SCORE = -58; // Position in degrees where the tiki torch goes to score at L2 3 and 4
		public static final double TIKI_L1_SCORE = 0; // Position in degrees where the tiki torch goes to score L1

		public static final double TIKI_INTAKE = 46; // Position in degrees where the tiki torch goes to intake

		public static final double TIKI_ALGAE_CLEAR = -89; // Position the tiki torch goes to when having an algae
	}

	public final static class TikiTorchRoller {
		public static final double INTAKE_SPEED = -1;
		public static final double OUTAKE_SPEED = 1;
	}

	public static final class Vision {
		public static final double CAMERA_LEFT_OFFSET = Units.inchesToMeters(-2); //measurement from camera to left side of reef pipe
		public static final double CAMERA_RIGHT_OFFSET = Units.inchesToMeters(13); //measurement from camera to right side of reef pipe
	}
}
