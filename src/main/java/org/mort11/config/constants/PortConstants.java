package org.mort11.config.constants;

public final class PortConstants {

public static final class Controller {
    public static final int JOYSTICK = 1;
    public static final int TESTING_CONTROLLER = 2;
    public static final int DRIVE_CONTROLLER = 4;
    public static final int COMP_CONTROLLER = 3;

    public static final int JOYSTICK_X_CHANNEL = 0;
    public static final int JOYSTICK_Y_CHANNEL = 1;
    public static final int JOYSTICK_TWIST_CHANNEL = 3;
    public static final int THROTTLE_CHANNEL = 2;

    public static final double DEAD_BAND = 0.05;

    public static final double MAX_THROTTLE = 0.2;
    public static final double MIN_THROTTLE = 0.05;
    public static final double MAX_ROTATE = 0.2;
    public static final double MIN_ROTATE = 0.03;
  }

  public static final class AlgaeArm {
    public static final int ARM_MOTOR = 13;
  }

  public static final class AlgaeRoller {
    public static final int ROLLER_MOTOR = 14;
  }

  public static final class Climber{
    //ASSIGNED to zero because don't know yet
    public static final int PNEUMATICS_MODULE_PORT = 32;

    public static final int CLIMBER_PORT_UP = 7;
    public static final int CLIMBER_PORT_DOWN = 6;
  }
  
  public static final class Drivetrain {
    public static final int FRONT_LEFT_DRIVE_MOTOR = 7;
    public static final int FRONT_LEFT_STEER_MOTOR = 8;
    public static final int FRONT_LEFT_ENCODER = 38;

    public static final int FRONT_RIGHT_DRIVE_MOTOR = 2;
    public static final int FRONT_RIGHT_STEER_MOTOR = 1;
    public static final int FRONT_RIGHT_ENCODER = 35;

    public static final int BACK_LEFT_DRIVE_MOTOR = 5;
    public static final int BACK_LEFT_STEER_MOTOR = 6;
    public static final int BACK_LEFT_ENCODER = 37;

    public static final int BACK_RIGHT_DRIVE_MOTOR = 4;
    public static final int BACK_RIGHT_STEER_MOTOR = 3;
    public static final int BACK_RIGHT_ENCODER = 36;

    public static final int IMU_ID = 30;

    public static final String CANIVORE_NAME = "drivetrain";
  }

  public static final class Elevator {
		public static final int MOTOR = 11;
    public static final int ENCODER = 0;
	}

  public static final class TikiTorchArm {
    public static final int ARM_MOTOR = 12;
  }

  public static final class TikiTorchRoller {
    public static final int ROLLER_MOTOR = 15;
  }

  public static final class Vision {
    public static final String FRONT_CAMERA_NAME = "limelight-front";
  }
}
