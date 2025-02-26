package org.mort11.library.subsystems.swerve;

import static org.mort11.library.subsystems.swerve.ModuleConfig.*;

import org.mort11.library.hardware.brands.ctre.CTREUtility.Falcon500;
import org.mort11.library.hardware.brands.ctre.CTREUtility.Krakenx60;
import org.mort11.library.hardware.brands.rev.RevUtility.NEO;
import org.mort11.library.hardware.brands.rev.RevUtility.NEO550;
import org.mort11.library.hardware.brands.rev.RevUtility.Vortex;
import org.mort11.library.hardware.encoder.Encoder;
import org.mort11.library.hardware.encoder.EncoderTypeEnum;
import org.mort11.library.hardware.motor.Motor;
import org.mort11.library.hardware.motor.MotorTypeEnum;
import org.mort11.library.hardware.motor.PIDMotor;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;

public class SwerveModule {

    public int driveMotorID;
    public int steerMotorID;
    public int encoderID;

    public MotorTypeEnum driveMotorType;
    public MotorTypeEnum steerMotorType;
    public EncoderTypeEnum encoderType;

    public Motor driveMotor;
    public PIDMotor steerMotor;
    public Encoder encoder;

    public double maxSpeed;
    public double maxVoltage;
    public double rotationToMeters;

    public double offset;

    public SwerveModuleState state;

    public ModuleConfigIntf moduleConfig;

    public SwerveModule(MotorTypeEnum driveMotorType, int driveMotorID, 
            MotorTypeEnum steerMotorType, int steerMotorID, 
            EncoderTypeEnum encoderType, int encoderID,
            ModuleConfigEnum moduleType
        ) {

        this(driveMotorType, driveMotorID, 
            steerMotorType, steerMotorID, 
            encoderType, encoderID,
            new MK4_L1()
        );

        switch (moduleType) {
            case MK4_L1:
                moduleConfig = new MK4_L1();
                break;
            case MK4_L2:
                moduleConfig = new MK4_L2();
                break;
            case MK4_L3:
                moduleConfig = new MK4_L3();
                break;
            case MK4_L4:
                moduleConfig = new MK4_L4();
                break;
            case MK4i_L1:
                moduleConfig = new MK4i_L1();
                break;
            case MK4i_L2:
                moduleConfig = new MK4i_L2();
                break;
            case MK4i_L3:
                moduleConfig = new MK4i_L3();
                break;
            case MK4iPlus:
                moduleConfig = new MK4i_L3Plus();
                break;
        }

        maxSpeed = maxSpeed * moduleConfig.WHEEL_DIAMETER * moduleConfig.DRIVE_REDUCTION;
        rotationToMeters = rotationToMeters * moduleConfig.WHEEL_DIAMETER * moduleConfig.DRIVE_REDUCTION;
        driveMotor.setDirectionFlip(moduleConfig.DRIVE_DIRECTION_FLIP);
        steerMotor.setDirectionFlip(moduleConfig.STEER_DIRECTION_FLIP);
    }



    public SwerveModule(MotorTypeEnum driveMotorType, int driveMotorID, 
            MotorTypeEnum steerMotorType, int steerMotorID, 
            EncoderTypeEnum encoderType, int encoderID,
            ModuleConfigIntf moduleConfig
        ) {
        this.driveMotorID = driveMotorID;
        this.driveMotorID = driveMotorID;
        this.encoderID = encoderID;

        this.driveMotorType = driveMotorType;
        this.steerMotorType = steerMotorType;
        this.encoderType = encoderType;
        this.moduleConfig = moduleConfig;

        driveMotor = new Motor(driveMotorType, driveMotorID);
        steerMotor = new PIDMotor(steerMotorType, steerMotorID);
        encoder = new Encoder(encoderType, encoderID);

        maxSpeed = Math.PI / 60;
        maxVoltage = 12;
        offset = 0;
        rotationToMeters = Math.PI;
        state = new SwerveModuleState(
            0, Rotation2d.fromDegrees(0)
        );

        switch(driveMotorType) {
            case NEO:
                maxSpeed = maxSpeed * NEO.MAX_RPM;
                break;
            case NEO550:
                maxSpeed = maxSpeed * NEO550.MAX_RPM;
                break;
            case VORTEX:
                maxSpeed = maxSpeed * Vortex.MAX_RPM;
                break;
            case FALCON:
                maxSpeed = maxSpeed * Falcon500.MAX_RPM;
                break;
            case KRAKEN:
                maxSpeed = maxSpeed * Krakenx60.MAX_RPM;
                break;
        }

        switch (steerMotorType) {
            case NEO:
                steerMotor.setPIDValues(
                    NEO.SWERVE_STEER_KP, 
                    NEO.SWERVE_STEER_KI, 
                    NEO.SWERVE_STEER_KD
                );
                break;
            case NEO550:
                steerMotor.setPIDValues(
                    NEO550.SWERVE_STEER_KP, 
                    NEO550.SWERVE_STEER_KI, 
                    NEO550.SWERVE_STEER_KD
                );
                break;
            case VORTEX:
                steerMotor.setPIDValues(
                    Vortex.SWERVE_STEER_KP, 
                    Vortex.SWERVE_STEER_KI, 
                    Vortex.SWERVE_STEER_KD
                );
                break;
            case FALCON:
                steerMotor.setPIDValues(
                    Falcon500.SWERVE_STEER_KP, 
                    Falcon500.SWERVE_STEER_KI, 
                    Falcon500.SWERVE_STEER_KD
                );
                break;
            case KRAKEN:
                steerMotor.setPIDValues(
                    Krakenx60.SWERVE_STEER_KP, 
                    Krakenx60.SWERVE_STEER_KI, 
                    Krakenx60.SWERVE_STEER_KD
                );
                break;
        }

        steerMotor.setPIDEnableContinuousInput(0, 1);
        steerMotor.setPIDTolerance(0.5, 10);

        maxSpeed = maxSpeed * moduleConfig.WHEEL_DIAMETER * moduleConfig.DRIVE_REDUCTION;
        rotationToMeters = rotationToMeters * moduleConfig.WHEEL_DIAMETER * moduleConfig.DRIVE_REDUCTION;
        driveMotor.setDirectionFlip(moduleConfig.DRIVE_DIRECTION_FLIP);
        steerMotor.setDirectionFlip(moduleConfig.STEER_DIRECTION_FLIP);
    }

    public void setCurrentLimits(double limit) {
        driveMotor.setCurrentLimit(limit);
        steerMotor.setCurrentLimit(limit);
    }

    public void setPosition(Rotation2d setpoint) {
        steerMotor.setPositionRotations(getEncoderPosition().getRotations(), setpoint.getRotations());
    }

    public void setDriveSpeedMeters(double speedMeters) {
        driveMotor.setVoltage((speedMeters / maxSpeed) * maxVoltage);
    }

    public void setModuleState(SwerveModuleState state) {
        this.state = SwerveModuleState.optimize(state, getEncoderPosition());

        setDriveSpeedMeters(this.state.speedMetersPerSecond);
        setPosition(this.state.angle);
    }

    public void setMaxVoltage(double maxVoltage) {
        this.maxVoltage = maxVoltage;
    }

    public void setOffset(double offset) {
        this.offset = offset;
    }



    public Rotation2d getEncoderPosition() {
        return Rotation2d.fromDegrees(encoder.getPosition().getDegrees() - offset);
    }

    public double getDrivePositionRotations() {
        return driveMotor.getPositionRotations();
    }

    public double getDriveVelocityRPM() {
        return driveMotor.getVelocityRPM();
    }

    public SwerveModulePosition getPosition() {
        return new SwerveModulePosition(
            (driveMotor.getPositionRotations() * rotationToMeters), 
            getEncoderPosition()
        );
    }



    public MotorTypeEnum getDriveMotorType() {
        return driveMotorType;
    }
    
    public MotorTypeEnum getSteerMotorType() {
        return steerMotorType;
    }

    public EncoderTypeEnum getEncoderType() {
        return encoderType;
    }

    public ModuleConfigIntf getModuleConfig() {
        return moduleConfig;
    }
}
