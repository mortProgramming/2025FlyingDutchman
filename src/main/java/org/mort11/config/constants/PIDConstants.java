package org.mort11.config.constants;

import edu.wpi.first.math.trajectory.TrapezoidProfile.Constraints;

public final class PIDConstants {

	public final static class AlgaeArm	{
		//FeedForward
		public final static double ROT_KS = 0;
		public final static double ROT_KV = 0;
		public final static double ROT_KG = 0;
		public final static double ROT_KA = 0;

		//PID Rotational
		public final static double ROT_KP = 0.0065;
		public final static double ROT_KI = 0;
		public final static double ROT_KD = 0;
		public final static Constraints ARM_ROT_CONSTRAINTS = new Constraints( 200,750);
	}
    
    public final class Drivetrain {
        public static final double AUTON_POS_KP = 1;
		public static final double AUTON_POS_KI = 0;
		// public static final double AUTON_POS_KD = 0.001x;
		public static final double AUTON_POS_KD = 0.16;
	
		public static final double AUTON_ROTATION_KP = 2;
		// public static final double AUTON_ROTATION_KP = 100;
		public static final double AUTON_ROTATION_KI = 0.;
		public static final double AUTON_ROTATION_KD = 0;

		public final static double POS_KP = 3;
		public final static double POS_KI = 0;
		public final static double POS_KD = 0;
		public static final Constraints POS_CONSTRAINTS = new Constraints(1, 3);
		public final static double POS_POS_TOLERANCE = 0.03;

		public final static double ANGLE_KP = 0.07;
		public final static double ANGLE_KI = 0;
		public final static double ANGLE_KD = 0;
		public static final Constraints ANGLE_CONSTRAINTS = new Constraints(100, 180);
		// public static final Constraints ANGLE_CONSTRAINTS = new Constraints(50, 1000);
		public final static double ANGLE_POS_TOLERANCE = 5;
		public final static double ANGLE_VEL_TOLERANCE = 20;

		public final static double TRANSLATIONAL_SLEW_LIMIT = 5;
		public final static double ROTATIONAL_SLEW_LIMIT = 10;
    }
	


  public final class Elevator {
		public static final double POS_KP = 0.08;
		public static final double POS_KI = 0;
		public static final double POS_KD = 0.003;
		public static final Constraints POS_CONSTRAINTS = new Constraints(80, 300);
		public static final Constraints POS_TELEOP_CONSTRAINTS = new Constraints(87, 360);
		public static final double SLOW_MAX_ELEVATOR_SPEED = 40;
		public static final double MEDIUM_MAX_ELEVATOR_SPEED = 70;

		// public static final double POS_POS_TOLERANCE = 0.05;
		// public static final double POS_VEL_TOLERANCE = 0;

		public static final double POS_KS = 0;
		public static final double POS_KG = -0.05;
		public static final double POS_KV = 0;
		public static final double POS_KA = 0;
  }

	public final static class TikiTorchArm	{
		//FeedForward
		public final static double ROT_KS = 0;
		public final static double ROT_KV = 0;
		public final static double ROT_KG = -0.03;
		public final static double ROT_KA = 0;

		//PID Rotational
		public final static double ROT_KP = 0.006;//.024 0.006
		// public final static double ROT_KI = 0.0105;
		// public final static double ROT_KD = 0.00175;
		public final static double ROT_KI = 0.001;
		public final static double ROT_KD = 0;
		public final static Constraints ARM_ROT_CONSTRAINTS = new Constraints(300,1000);
	}
}
