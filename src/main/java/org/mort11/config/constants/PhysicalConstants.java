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
		public static final double ALGAE_FLOOR_INTAKE = -27;

		public static final double ALGAE_PROCESSOR_SCORE = 0;
		public static final double ALGAE_BARGE_SCORE = 45;
	} 

	public final static class AlgaeRoller {
		public static final double INTAKE_SPEED = -1;
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

		// public static final double FRONT_LEFT_OFFSET = 293.9;
		// public static final double FRONT_RIGHT_OFFSET = 273.1;
		// public static final double BACK_LEFT_OFFSET = 223.3;
		// public static final double BACK_RIGHT_OFFSET = 255.5;

		public static final double FRONT_LEFT_OFFSET = 21.3 + 90 + 180;
		public static final double FRONT_RIGHT_OFFSET = 3.08 + 90 + 180;
		public static final double BACK_LEFT_OFFSET = 311.75 + 90 + 180;
		public static final double BACK_RIGHT_OFFSET = 346.73 + 90 + 180;

		public static final int IMU_TO_ROBOT_FRONT_ANGLE = 270;

		public static final double WHEEL_COEFFICIENT_OF_FRICTION = 1;
		public static final double ROBOT_MASS = 62.1;
		public static final double ROBOT_MOMENT_OF_INERTIA = ROBOT_MASS * 0.254 * 0.254 / 2;
		public static final double DRIVE_MOTOR_CURRENT_LIMIT = 60;
		public static final double DRIVE_MOTOR_MAX_RPM = 6000;

		public static final double DRIVE_REDUCTION = (16.0 / 50.0) * (28.0 / 16.0) * (15.0 / 45.0);
		public static final double WHEEL_DIAMETER = 0.1014;  //0.1014
		public static final double ROTATIONS_TO_METERS = WHEEL_DIAMETER * Math.PI;
		public static final double MAX_SPEED = DRIVE_REDUCTION * ROTATIONS_TO_METERS * (DRIVE_MOTOR_MAX_RPM / 60);

		public static final double ODOMETRY_MULTIPLIER = 5.67;
		// public static final double ODOMETRY_MULTIPLIER = 5.575;
    }	

	public static final class Elevator {
		//all in inches of carriage

		//3 is elevator stage count
		public static final double ROTATIONS_TO_INCHES = 5.642 * 3;

		//The maximum inch displacement for every 0.02 sec cycle, has to be less than half of the rotations to inches		
		public static final double MAXIMUM_INCH_CHANGE = 5;
		public static final double GEAR_RATIO = 16;

		public static final double ELEVATOR_LOWER_LIMIT_SWITCH_HEIGHT = 0;
		// public static final double ELEVATOR_UPPER_LIMIT_SWITCH_HEIGHT = -71.8;
		public static final double ELEVATOR_UPPER_LIMIT_SWITCH_HEIGHT = -69.4;

		/*
			to fix offset, move the elevator to its lowest possible position, 
			then subtract the position value from the current offset value
			and make that the new offset
		*/
		public static final double ELEVATOR_OFFSET = -23.11;
		public static final double ELEVATOR_START_HEIGHT = 0.8; //for relative encoder

		public static final double ELEVATOR_REST_HEIGHT = 5.6;

		//coral
		public static final double ELEVATOR_L1_HEIGHT = 13;
		public static final double ELEVATOR_L2_HEIGHT = 27.6;
		public static final double ELEVATOR_L3_HEIGHT = 43.6;
		public static final double ELEVATOR_L4_HEIGHT = 68;
		public static final double ELEVATOR_AUTO_L4_HEIGHT = 69;
		public static final double ELEVATOR_INTAKE_HEIGHT = 17.75;
		public static final double ELEVATOR_AUTO_INTAKE_HEIGHT = 17.75; //old autos

		//algae
		public static final double ELEVATOR_LOW_ALGAE_HEIGHT = 4.4;
		public static final double ELEVATOR_HIGH_ALGAE_HEIGHT = 22.3;
		public static final double ELEVATOR_PROCESSOR_HEIGHT = 0;
		public static final double ELEVATOR_BARGE_HEIGHT = 71.5;
		public static final double ELEVATOR_FLOOR_HEIGHT = 0;
	}

	public static final class Lights {
		public static final double GREEN = 0.77;
		public static final double RED = 0.61;
		public static final double BLUE = 0.87;
		public static final double GOLD = 0.67;
		public static final double PINK = 0.57;
		public static final double DARK_BLUE = 0.85;
		public static final double PURPLE = 0.91;
		public static final double WHITE = 0.93;
		public static final double BLACK = 0.99;
		public static final double YELLOW = 0.69;
		public static final double ORANGE = 0.65;

		
	}

	public final static class TikiTorchArm {

		public static final double OFFSET = 284; //The amount in degrees to ensure that the algae arm reads 0 when flat

		public static final double TIKI_REST = 59.3; //The rest position of the tiki torch in degrees

		public static final double ARM_TOP_NEVER_POSITION = 45; // Position in degrees where the tiki torch cannot go to (top)
		public static final double ARM_BOTTOM_NEVER_POSITION = 0; //Position in degeres where the tiki torch cannot go to (bottom)

		public static final double TIKI_L234_SCORE = -65; // Position in degrees where the tiki torch goes to score at L2 3 and 4
		public static final double TIKI_AUTO_L234_SCORE = -58; // Position in degrees where the tiki torch goes to score at L2 3 and 4
		public static final double TIKI_L1_SCORE = 0; // Position in degrees where the tiki torch goes to score L1

		public static final double TIKI_INTAKE = 35; // Position in degrees where the tiki torch goes to intake 40

		public static final double TIKI_ALGAE_CLEAR = -89; // Position the tiki torch goes to when having an algae
	}

	public final static class TikiTorchRoller {
		public static final double INTAKE_SPEED = -1;
		public static final double OUTAKE_SPEED = 1;
		public static final double REST_SPEED = -0.1;
	}

	public static final class Vision {
		public static final double CAMERA_LEFT_OFFSET = Units.inchesToMeters(-1.5); //measurement from camera to left side of reef pipe
		public static final double CAMERA_RIGHT_OFFSET = Units.inchesToMeters(9); //measurement from camera to right side of reef pipe

		public static final double CAMERA_CENTER_OFFSET = Units.inchesToMeters(-0.75);
	}
}
