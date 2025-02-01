package org.mort11.subsystems;

import static org.mort11.config.constants.PhysicalConstants.Drivetrain.*;
import static org.mort11.config.constants.PIDConstants.Drivetrain.*;
import static org.mort11.config.constants.PortConstants.Drivetrain.*;
import static org.mort11.library.hardware.encoder.EncoderTypeEnum.*;
import static org.mort11.library.hardware.imu.IMUTypeEnum.*;
import static org.mort11.library.hardware.motor.MotorTypeEnum.*;
import static org.mort11.library.subsystems.swerve.ModuleConfigEnum.*;

import org.mort11.config.IO;
import org.mort11.library.subsystems.swerve.SwerveDriveBase;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.kinematics.ChassisSpeeds;

public class Drivetrain extends SwerveDriveBase {
	private static Drivetrain drivetrain;

	private ProfiledPIDController xToPosController;
	private ProfiledPIDController yToPosController;
	private ProfiledPIDController rotateToAngleController;

	private Drivetrain() {
		super(
			DRIVETRAIN_WHEELBASE_METERS, DRIVETRAIN_TRACKWIDTH_METERS,
			KRAKEN,
			FRONT_LEFT_DRIVE_MOTOR, FRONT_RIGHT_DRIVE_MOTOR,
			BACK_LEFT_DRIVE_MOTOR, BACK_RIGHT_DRIVE_MOTOR,
			KRAKEN,
			FRONT_LEFT_STEER_MOTOR, FRONT_RIGHT_STEER_MOTOR,
			BACK_LEFT_STEER_MOTOR, BACK_RIGHT_STEER_MOTOR,
			CANCODER,
			FRONT_LEFT_ENCODER, FRONT_RIGHT_ENCODER,
			BACK_LEFT_ENCODER, BACK_RIGHT_ENCODER,
			MK4i_L3, NAVX,
			FRONT_LEFT_OFFSET, FRONT_RIGHT_OFFSET,
			BACK_LEFT_OFFSET, BACK_RIGHT_OFFSET
		);

		xToPosController = new ProfiledPIDController(
			POS_KP, POS_KI, POS_KD, POS_CONSTRAINTS
		);
		yToPosController = new ProfiledPIDController(
			POS_KP, POS_KI, POS_KD, POS_CONSTRAINTS
		);
    	rotateToAngleController = new ProfiledPIDController(
			ANGLE_KP, ANGLE_KI, ANGLE_KD, ANGLE_CONSTRAINTS
		);

    	xToPosController.setTolerance(POS_POS_TOLERANCE);
		  yToPosController.setTolerance(POS_POS_TOLERANCE);
    	rotateToAngleController.setTolerance(ANGLE_POS_TOLERANCE, ANGLE_VEL_TOLERANCE);

    	rotateToAngleController.enableContinuousInput(-180, 180);
	}

	@Override
	public void periodic() {
		ChassisSpeeds speeds;

    	if(IO.isBlue()) {
			speeds = new ChassisSpeeds(
				-getChassisSpeeds().vyMetersPerSecond, -getChassisSpeeds().vxMetersPerSecond,
				getChassisSpeeds().omegaRadiansPerSecond
			);
		}
		else {
			speeds = new ChassisSpeeds(
				getChassisSpeeds().vyMetersPerSecond, getChassisSpeeds().vxMetersPerSecond,
				getChassisSpeeds().omegaRadiansPerSecond
			);
		};

		setDrive(speeds);

    getSwerveDrive().update();
	}

	public double calculateRotateController(double wantedDegrees) {
		return rotateToAngleController.calculate(getIMURotation().getDegrees(), wantedDegrees);
	}

	public double calculateChangeRotateController(double wantedPosition) {
		return rotateToAngleController.calculate(getIMURotation().getDegrees(), getIMURotation().getDegrees() + wantedPosition);
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
