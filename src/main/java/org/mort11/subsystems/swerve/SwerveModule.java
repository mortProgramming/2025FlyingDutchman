package org.mort11.subsystems.swerve;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;

import static org.mort11.config.constants.PortConstants.Drivetrain.*;
import static org.mort11.config.constants.PhysicalConstants.Drivetrain.*;
import static org.mort11.config.constants.PhysicalConstants.ROBOT_VOLTAGE;

import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.hardware.CANcoder;

import edu.wpi.first.math.controller.PIDController;

public class SwerveModule {

    public TalonFX driveTalonFX;
    public TalonFX steerTalonFX;
    public CANcoder encoder;

    public double offset;

    public SwerveModuleState state;

    public PIDController steerPIDController;

    public SwerveModule(int driveTalonFXID, int steerTalonFXID, int encoderID) {

        driveTalonFX = new TalonFX(driveTalonFXID, CANIVORE_NAME);
        steerTalonFX = new TalonFX(steerTalonFXID, CANIVORE_NAME);
        encoder = new CANcoder(encoderID, CANIVORE_NAME);

        offset = 0;
        state = new SwerveModuleState(
            0, Rotation2d.fromDegrees(0)
        );

        steerPIDController = new PIDController(15, 0, 0);

        steerPIDController.enableContinuousInput(0, 1);
        steerPIDController.setTolerance(0.01, 10);
    }

    public void setPosition(Rotation2d setpoint) {
        steerTalonFX.setVoltage(
            steerPIDController.calculate(
                getEncoderPosition().getRotations(), 
                setpoint.getRotations()
            )
        );
    }

    public void setDriveSpeedMeters(double speedMeters) {
        driveTalonFX.setVoltage((speedMeters / MAX_SPEED) * ROBOT_VOLTAGE);
    }

    public void setModuleState(SwerveModuleState state) {
        this.state = SwerveModuleState.optimize(state, getEncoderPosition());

        setDriveSpeedMeters(this.state.speedMetersPerSecond);
        setPosition(this.state.angle);
    }

    public void setOffset(double offset) {
        this.offset = offset;
    }



    public Rotation2d getEncoderPosition() {
        return Rotation2d.fromDegrees(encoder.getPosition().getValueAsDouble() * 360 - offset);
    }

    public double getDrivePositionRotations() {
        return driveTalonFX.getPosition().getValueAsDouble();
    }

    public double getDriveVelocityRPM() {
        return driveTalonFX.getVelocity().getValueAsDouble() * 60;
    }

    public SwerveModulePosition getPosition() {
        return new SwerveModulePosition(
            (getDrivePositionRotations() * ROTATIONS_TO_METERS / ODOMETRY_MULTIPLIER), 
            getEncoderPosition()
        );
    }
}
