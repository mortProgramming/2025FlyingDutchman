package org.mort11.config.constants;

import edu.wpi.first.math.util.Units;

public final class PhysicalConstants {

	public static final double ROBOT_VOLTAGE = 12;

	public final static class AlgaeArm {

		public static final double OFFSET = -261;

		public static final double ALGAE_REST = 95;

		public static final double ARM_TOP_NEVER_POSITION = -120;
		public static final double ARM_BOTTOM_NEVER_POSITION = -300;

		public static final double ALGAE_REEF_INTAKE = 45;
		public static final double ALGAE_FLOOR_INTAKE = -15;

		public static final double ALGAE_PROCESSOR_SCORE = 0;
		public static final double ALGAE_BARGE_SCORE = 45;
	} 

	public final static class AlgaeRoller {
		public static final double INTAKE_SPEED = 0.6;
		public static final double OUTAKE_SPEED = -0.8;
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
		public static final double MAXIMUM_INCH_CHANGE = 4;
		public static final double GEAR_RATIO = 16;

		public static final double ELEVATOR_OFFSET = 2.95;
		public static final double ELEVATOR_REST_HEIGHT = 5.5;
		public static final double ELEVATOR_START_HEIGHT = 2.7;

		public static final double ELEVATOR_L1_HEIGHT = 13;
		public static final double ELEVATOR_L2_HEIGHT = 30.5;
		public static final double ELEVATOR_L3_HEIGHT = 47;
		public static final double ELEVATOR_L4_HEIGHT = 72;
		public static final double ELEVATOR_INTAKE_HEIGHT = 22;

		public static final double ELEVATOR_LOW_ALGAE_HEIGHT = 13.5;
		public static final double ELEVATOR_HIGH_ALGAE_HEIGHT = 29;
		public static final double ELEVATOR_PROCESSOR_HEIGHT = 0;
		public static final double ELEVATOR_BARGE_HEIGHT = 72;
		public static final double ELEVATOR_FLOOR_HEIGHT = 0;
	}

	public final static class TikiTorchArm {

		public static final double OFFSET = 295;

		public static final double TIKI_REST = 59.3;

		public static final double ARM_TOP_NEVER_POSITION = 45;
		public static final double ARM_BOTTOM_NEVER_POSITION = 0;

		public static final double TIKI_L234_SCORE = -50;
		public static final double TIKI_L1_SCORE = 0;

		public static final double TIKI_INTAKE = 46;

		public static final double TIKI_ALGAE_CLEAR = -89;
	}

	public final static class TikiTorchRoller {
		public static final double INTAKE_SPEED = -1;
		public static final double OUTAKE_SPEED = 1;
	}
}
