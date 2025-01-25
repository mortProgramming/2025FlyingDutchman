package org.mort11.config.constants;

import edu.wpi.first.math.util.Units;

public final class PhysicalConstants {
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
    }
}
