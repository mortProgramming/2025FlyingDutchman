package org.mort11.library.subsystems.arm;

import org.mort11.library.hardware.motor.Motor;
import org.mort11.library.hardware.motor.MotorIntf;
import org.mort11.library.hardware.motor.MotorTypeEnum;

import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.geometry.Rotation2d;

public class Arm {
    public MotorIntf motor;

    public Rotation2d offset;

    public ArmFeedforward feedForward;

    public Arm(MotorTypeEnum motorType, int motorID) {
        this(new Motor(motorType, motorID));
    }

    public Arm(MotorIntf motor) {
        this.motor = motor;

        feedForward = new ArmFeedforward(0, 0, 0, 0);
        offset = Rotation2d.fromDegrees(0);
    }

    public void setFeedforward(double kS, double kG, double kV, double kA) {
        feedForward = new ArmFeedforward(kS, kG, kV, kA);
    }

    public void setFeedforward(double kS, double kG, double kV, double kA, Rotation2d offset) {
        feedForward = new ArmFeedforward(kS, kG, kV, kA);
        this.offset = offset;
    }

    public void setFeedforward(double kS, double kG, double kV, double kA, double offsetRotations) {
        feedForward = new ArmFeedforward(kS, kG, kV, kA);
        this.offset = Rotation2d.fromRotations(offsetRotations);
    }

    public void setVoltage(double voltage) {
        motor.setVoltage(voltage);
    }

    public void setFeededVoltage(double voltage, Rotation2d position) {
        motor.setVoltage(voltage + feedForward.calculate(position.rotateBy(offset).getRadians(), 0));
    }

    public void setFeededVoltage(double voltage, double positionRotations) {
        motor.setVoltage(voltage + feedForward.calculate(
            Rotation2d.fromRotations(positionRotations).rotateBy(offset).getRadians(), 0
        ));
    }

    public void setFeededVoltage(double voltage, Rotation2d position, double velocityRotations) {
        motor.setVoltage(voltage + feedForward.calculate(position.rotateBy(offset).getRadians(), velocityRotations));
    }

    public void setFeededVoltage(double voltage, double positionRotations, double velocityRotations) {
        motor.setVoltage(voltage + feedForward.calculate(
            Rotation2d.fromRotations(positionRotations).rotateBy(offset).getRadians(), velocityRotations
        ));
    }

    public void setFeededVoltage(double voltage, Rotation2d position, double velocityRotations, double accelerationRotations) {
        motor.setVoltage(voltage + feedForward.calculate(position.rotateBy(offset).getRadians(), velocityRotations, accelerationRotations));
    }

    public void setFeededVoltage(double voltage, double positionRotations, double velocityRotations, double accelerationRotations) {
        motor.setVoltage(voltage + feedForward.calculate(
            Rotation2d.fromRotations(positionRotations).rotateBy(offset).getRadians(), velocityRotations, accelerationRotations
        ));
    }
}
