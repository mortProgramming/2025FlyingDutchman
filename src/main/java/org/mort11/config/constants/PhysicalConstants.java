package org.mort11.config.constants;

import edu.wpi.first.math.util.Units;

public final class PhysicalConstants {

	public static final double ROBOT_VOLTAGE = 12;

	public static final double ROBO_VOLTAGE = 12;
	
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

		public static final int IMU_TO_ROBOT_FRONT_ANGLE = 0;

		public static final double WHEEL_COEFFICIENT_OF_FRICTION = 1;
		public static final double ROBOT_MASS = 15;
		public static final double ROBOT_MOMENT_OF_INERTIA = 3;
		public static final double DRIVE_MOTOR_CURRENT_LIMIT = 80;

		// putting these here for now
		
    }

  public final static class Climber {
		public static final int COMPRESSER_MIN_PRESSURE = 80;
		public static final int COMPRESSER_MAX_PRESSURE = 120;

	}

	public static final class Elevator {

		//all in inches
		public static final double ROTATIONS_TO_INCHES = 0;
		public static final double MAXIMUM_INCH_CHANGE = 0;

		public static final double START_HEIGHT = 0;
		public static final double REST_HEIGHT = 0;

		public static final double L1_HEIGHT = 0;
		public static final double L2_HEIGHT = 0;
		public static final double L3_HEIGHT = 0;
		public static final double L4_HEIGHT = 0;
		public static final double INTAKE_HEIGHT = 0;

		public static final double LOW_ALGAE_HEIGHT = 0;
		public static final double HIGH_ALGAE_HEIGHT = 0;
		public static final double PROCESSOR_HEIGHT = 0;
		public static final double BARGE_HEIGHT = 0;
		public static final double FLOOR_HEIGHT = 0;
	}

	public final static class Arm	{

		public static final double OFFSET = 0;

		public static final double REST = 0;
		public static final double ARM_NEVER_POSITION = 0;

		public static final double L4 = 0;
		public static final double L3 = 0;
		public static final double L2 = 0;
		public static final double L1 = 0;

	} 

}
