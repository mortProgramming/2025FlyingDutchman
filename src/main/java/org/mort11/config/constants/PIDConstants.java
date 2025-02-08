package org.mort11.config.constants;

import edu.wpi.first.math.trajectory.TrapezoidProfile.Constraints;

public final class PIDConstants {
    
    public final class Drivetrain {
        public static final double AUTON_POS_KP = 0.315;
		public static final double AUTON_POS_KI = 0;
		public static final double AUTON_POS_KD = 0.001;
	
		public static final double AUTON_ROTATION_KP = 1.45;
		public static final double AUTON_ROTATION_KI = 0;
		public static final double AUTON_ROTATION_KD = 0;

		public final static double POS_KP = 0.5;
		public final static double POS_KI = 0;
		public final static double POS_KD = 0;
		public static final Constraints POS_CONSTRAINTS = new Constraints(10, 10);
		public final static double POS_POS_TOLERANCE = 0.05;

		public final static double ANGLE_KP = 0.07;
		public final static double ANGLE_KI = 0;
		public final static double ANGLE_KD = 0.001;
		public static final Constraints ANGLE_CONSTRAINTS = new Constraints(300, 300);
		public final static double ANGLE_POS_TOLERANCE = 3;
		public final static double ANGLE_VEL_TOLERANCE = 30;
    }

  public final class Elevator {
		public static final double POS_KP = 0.5;
		public static final double POS_KI = 0;
		public static final double POS_KD = 0;
		public static final Constraints POS_CONSTRAINTS = new Constraints(0, 0);
		// public static final double POS_POS_TOLERANCE = 0.05;
		// public static final double POS_VEL_TOLERANCE = 0;

		public static final double POS_KS = 0;
		public static final double POS_KG = 0;
		public static final double POS_KV = 0;
		public static final double POS_KA = 0;

	public final static class Arm	{
		//FeedForward
		public final static double ROT_KS = 0;
		public final static double ROT_KV = 0;
		public final static double ROT_KG = 0;
		public final static double ROT_KA = 0;

		//PID Rotational
		public final static double ROT_KP = 0;
		public final static double ROT_KI = 0;
		public final static double ROT_KD = 0;
		public final static Constraints ARM_ROT_CONSTRAINTS = new Constraints(	0,0 );

	}
}
