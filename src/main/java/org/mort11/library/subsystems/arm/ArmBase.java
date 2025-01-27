package org.mort11.library.subsystems.arm;

import static org.mort11.library.logger.LoggerTypeEnum.*;

import org.mort11.library.hardware.encoder.Encoder;
import org.mort11.library.hardware.encoder.EncoderTypeEnum;
import org.mort11.library.hardware.motor.MotorGroup;
import org.mort11.library.hardware.motor.MotorTypeEnum;
import org.mort11.library.logger.LoggerGroup;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.trajectory.TrapezoidProfile.Constraints;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ArmBase extends SubsystemBase {

    private MotorGroup motors;
    private PIDArm pidArm;

    private Encoder encoder;

    private LoggerGroup loggers;

    private double armPos;
    private double topNeverPos, bottomNeverPos;

    public ArmBase(
            EncoderTypeEnum encoderType, int encoderID,
            Rotation2d armOffset, double bottomNeverPos, double topNeverPos,
            double kP, double kI, double kD, Constraints constraints, 
            MotorTypeEnum motorType, int... motorIDs
        ) {
        this(
            encoderType, encoderID, 
            armOffset, bottomNeverPos, topNeverPos,
            kP, kI, kD, constraints, 
            0, 0, 0, 0, 
            motorType, motorIDs
        );
    }

    public ArmBase(
            EncoderTypeEnum encoderType, int encoderID, 
            Rotation2d armOffset, double bottomNeverPos, double topNeverPos,
            double kP, double kI, double kD, Constraints constraints, 
            double kS, double kG, double kV, double kA, 
            MotorTypeEnum motorType, int... motorIDs
        ) {
        motors = new MotorGroup(motorType, motorIDs);

        encoder = new Encoder(encoderType, encoderID);

        pidArm = new PIDArm(motors);
        pidArm.setPIDConstants(kP, kI, kD, constraints);
        pidArm.setFeedforward(kS, kG, kV, kA, armOffset);

        this.bottomNeverPos = bottomNeverPos;
        this.topNeverPos = topNeverPos;

        loggers = new LoggerGroup(SMARTDASHBOARD, SHUFFLEBOARD);

        loggers.putDouble("Arm Pos Deg", this::getEncoderPosDeg);
        loggers.putDouble("Arm Percent Output", () -> armPos);
        loggers.putDouble("Arm Actual Output", () -> motors.getOutputVoltage(0));
    }

    @Override
    public void periodic() {
        pidArm.setPIDPositionDeg(getEncoderPosDeg(), armPos);
    }

    public void setArmVelocity(double degPerSecond){
        this.armPos = armPos + degPerSecond * 0.02;
    }

    public void setSetpoint(double armPosDeg){
        this.armPos = armPosDeg;
    }



    public double getEncoderPosDeg() {
        double degrees = encoder.getPosition().getDegrees();

        if (degrees < topNeverPos && degrees > bottomNeverPos) {
            degrees += 360;
        }

        return degrees; 
    }

    public double getArmPos() {
        return armPos;
    }

    public MotorGroup getMotors() {
        return motors;
    }

    public PIDArm getPIDArm() {
        return pidArm;
    }

    public LoggerGroup getLogger() {
        return loggers;
    }
}
