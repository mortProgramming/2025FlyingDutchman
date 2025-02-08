package org.mort11.subsystems;

import org.mort11.config.IO;
import static org.mort11.config.constants.PIDConstants.Drivetrain.ANGLE_CONSTRAINTS;
import static org.mort11.config.constants.PIDConstants.Drivetrain.ANGLE_KD;
import static org.mort11.config.constants.PIDConstants.Drivetrain.ANGLE_KI;
import static org.mort11.config.constants.PIDConstants.Drivetrain.ANGLE_KP;
import static org.mort11.config.constants.PIDConstants.Drivetrain.POS_CONSTRAINTS;
import static org.mort11.config.constants.PIDConstants.Drivetrain.POS_KD;
import static org.mort11.config.constants.PIDConstants.Drivetrain.POS_KI;
import static org.mort11.config.constants.PIDConstants.Drivetrain.POS_KP;
import static org.mort11.config.constants.PhysicalConstants.Drivetrain.BACK_LEFT_OFFSET;
import static org.mort11.config.constants.PhysicalConstants.Drivetrain.BACK_RIGHT_OFFSET;
import static org.mort11.config.constants.PhysicalConstants.Drivetrain.DRIVETRAIN_TRACKWIDTH_METERS;
import static org.mort11.config.constants.PhysicalConstants.Drivetrain.DRIVETRAIN_WHEELBASE_METERS;
import static org.mort11.config.constants.PhysicalConstants.Drivetrain.FRONT_LEFT_OFFSET;
import static org.mort11.config.constants.PhysicalConstants.Drivetrain.FRONT_RIGHT_OFFSET;
import static org.mort11.config.constants.PortConstants.Drivetrain.BACK_LEFT_DRIVE_MOTOR;
import static org.mort11.config.constants.PortConstants.Drivetrain.BACK_LEFT_ENCODER;
import static org.mort11.config.constants.PortConstants.Drivetrain.BACK_LEFT_STEER_MOTOR;
import static org.mort11.config.constants.PortConstants.Drivetrain.BACK_RIGHT_DRIVE_MOTOR;
import static org.mort11.config.constants.PortConstants.Drivetrain.BACK_RIGHT_ENCODER;
import static org.mort11.config.constants.PortConstants.Drivetrain.BACK_RIGHT_STEER_MOTOR;
import static org.mort11.config.constants.PortConstants.Drivetrain.CANIVORE_NAME;
import static org.mort11.config.constants.PortConstants.Drivetrain.FRONT_LEFT_DRIVE_MOTOR;
import static org.mort11.config.constants.PortConstants.Drivetrain.FRONT_LEFT_ENCODER;
import static org.mort11.config.constants.PortConstants.Drivetrain.FRONT_LEFT_STEER_MOTOR;
import static org.mort11.config.constants.PortConstants.Drivetrain.FRONT_RIGHT_DRIVE_MOTOR;
import static org.mort11.config.constants.PortConstants.Drivetrain.FRONT_RIGHT_ENCODER;
import static org.mort11.config.constants.PortConstants.Drivetrain.FRONT_RIGHT_STEER_MOTOR;
import static org.mort11.config.constants.PortConstants.Drivetrain.IMU_ID;
import static org.mort11.library.hardware.encoder.EncoderTypeEnum.CANCODER;
import org.mort11.library.hardware.imu.IMU;
import static org.mort11.library.hardware.imu.IMUTypeEnum.PIGEON2;
import static org.mort11.library.hardware.motor.MotorTypeEnum.KRAKEN;
import static org.mort11.library.subsystems.swerve.ModuleConfigEnum.MK4i_L3;
import org.mort11.library.subsystems.swerve.SwerveModule;
import org.mort11.library.subsystems.swerve.swervedrives.OdometeredSwerveDrive;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drivetrain extends SubsystemBase {
  private static Drivetrain drivetrain;

  private OdometeredSwerveDrive swerveDrive;

  private SwerveModule frontLeftModule;
  private SwerveModule frontRightModule;
  private SwerveModule backLeftModule;
  private SwerveModule backRightModule;

  private IMU imu;

  private SwerveDriveKinematics kinematics;

  private ChassisSpeeds speeds;

  private ProfiledPIDController xToPosController, yToPosController, rotateToAngleController;

  @SuppressWarnings("OverridableMethodCallInConstructor")
  private Drivetrain() {
    configureSwerve();
    
    speeds = new ChassisSpeeds(0, 0, 0);

	xToPosController = new ProfiledPIDController(
			POS_KP, POS_KI, POS_KD, POS_CONSTRAINTS
		);
		yToPosController = new ProfiledPIDController(
			POS_KP, POS_KI, POS_KD, POS_CONSTRAINTS
		);
    	rotateToAngleController = new ProfiledPIDController(
			ANGLE_KP, ANGLE_KI, ANGLE_KD, ANGLE_CONSTRAINTS
		);

		rotateToAngleController.enableContinuousInput(POS_KI, POS_KI);
  }

  public void configureSwerve () {
    frontLeftModule = new SwerveModule(
      KRAKEN, FRONT_LEFT_DRIVE_MOTOR, 
      KRAKEN, FRONT_LEFT_STEER_MOTOR, 
      CANCODER, FRONT_LEFT_ENCODER, 
      MK4i_L3
    );

    frontRightModule = new SwerveModule(
      KRAKEN, FRONT_RIGHT_DRIVE_MOTOR, 
      KRAKEN, FRONT_RIGHT_STEER_MOTOR, 
      CANCODER, FRONT_RIGHT_ENCODER, 
      MK4i_L3
    );

    backLeftModule = new SwerveModule(
      KRAKEN, BACK_LEFT_DRIVE_MOTOR, 
      KRAKEN, BACK_LEFT_STEER_MOTOR, 
      CANCODER, BACK_LEFT_ENCODER, 
      MK4i_L3
    );

    backRightModule = new SwerveModule(
      KRAKEN, BACK_RIGHT_DRIVE_MOTOR, 
      KRAKEN, BACK_RIGHT_STEER_MOTOR, 
      CANCODER, BACK_RIGHT_ENCODER, 
      MK4i_L3
    );

    kinematics = new SwerveDriveKinematics(
      // Front left
			new Translation2d(DRIVETRAIN_TRACKWIDTH_METERS / 2.0, DRIVETRAIN_WHEELBASE_METERS / 2.0),
			// Front right
			new Translation2d(DRIVETRAIN_TRACKWIDTH_METERS / 2.0, -DRIVETRAIN_WHEELBASE_METERS / 2.0),
			// Back left
			new Translation2d(-DRIVETRAIN_TRACKWIDTH_METERS / 2.0, DRIVETRAIN_WHEELBASE_METERS / 2.0),
			// Back right
			new Translation2d(-DRIVETRAIN_TRACKWIDTH_METERS / 2.0, -DRIVETRAIN_WHEELBASE_METERS / 2.0)
    );

    imu = new IMU(PIGEON2, IMU_ID);

    swerveDrive = new OdometeredSwerveDrive(
      frontLeftModule, frontRightModule, 
      backLeftModule, backRightModule, 
      kinematics, imu
    );

    swerveDrive.setOffsets(FRONT_LEFT_OFFSET, FRONT_RIGHT_OFFSET, BACK_LEFT_OFFSET, BACK_RIGHT_OFFSET);

    swerveDrive.setCanivore(CANIVORE_NAME);
  }

  @Override
  public void periodic() {
    if (IO.isBlue()) {
			speeds = new ChassisSpeeds(
				speeds.vyMetersPerSecond, -speeds.vxMetersPerSecond,
				speeds.omegaRadiansPerSecond
			);
		}
		else {
			speeds = new ChassisSpeeds(
				-speeds.vyMetersPerSecond, speeds.vxMetersPerSecond,
				speeds.omegaRadiansPerSecond
			);
		}

		swerveDrive.setOrientedVelocity(speeds);

    swerveDrive.update();

    SmartDashboard.putNumber("XPose", swerveDrive.getPosition().getX());
    SmartDashboard.putNumber("YPose", swerveDrive.getPosition().getY());

    SmartDashboard.putNumber("Yaw", Math.toDegrees(swerveDrive.getRobotRotations().getZ()));
    SmartDashboard.putNumber("Pitch", Math.toDegrees(swerveDrive.getRobotRotations().getY()));
    SmartDashboard.putNumber("Roll", Math.toDegrees(swerveDrive.getRobotRotations().getX()));
  }

  public void setDrive(ChassisSpeeds speeds) {
    this.speeds = speeds;
  }

  public Command setGyroscopeZero(double angle) {
		return new InstantCommand(() -> swerveDrive.zeroIMU(angle));
	}



	public ChassisSpeeds getChassisSpeeds() {
        return speeds;
    }

	public double getMaxSpeedMeters() {
		return frontLeftModule.maxSpeed;
	}

	public double calculateRotateController(double wantedDegrees) {
		return rotateToAngleController.calculate(
			swerveDrive.getFieldRelativeAngle2d().getDegrees(), 
			wantedDegrees
		);
	}

	public double calculateChangeRotateController(double wantedPosition) {
		return rotateToAngleController.calculate(
			swerveDrive.getFieldRelativeAngle2d().getDegrees(), 
			swerveDrive.getFieldRelativeAngle2d().getDegrees() + wantedPosition
		);
	}
	
	public OdometeredSwerveDrive getSwerveDrive() {
		return swerveDrive;
	}

	public SwerveDriveKinematics getDriveKinematics() {
		return kinematics;
	}

	public Rotation2d getRotation2d() {
		return imu.getRotation2d();
	}

	public Pose2d getPose() {
		return swerveDrive.getPosition();
	}

	public ProfiledPIDController getXController() {
		return xToPosController;
	}

	public ProfiledPIDController getYController() {
		return yToPosController;
	}

	public ProfiledPIDController getRotateController() {
		return rotateToAngleController;
	}

  public static Drivetrain getInstance() {
		if (drivetrain == null) {
			drivetrain = new Drivetrain();
		}
		return drivetrain;
	}
}
