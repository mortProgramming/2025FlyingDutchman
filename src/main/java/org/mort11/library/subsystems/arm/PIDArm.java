package org.mort11.library.subsystems.arm;

import org.mort11.library.hardware.motor.MotorIntf;
import org.mort11.library.hardware.motor.MotorTypeEnum;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.trajectory.TrapezoidProfile.Constraints;

public class PIDArm extends Arm {

    public ProfiledPIDController controller;

    public PIDArm(MotorTypeEnum motorType, int motorID) {
        super(motorType, motorID);

        controller = new ProfiledPIDController(0, 0, 0, new Constraints(0, 0));
    }
    
    public PIDArm (MotorIntf motor) {
        super(motor);

        controller = new ProfiledPIDController(0, 0, 0, new Constraints(0, 0));
    }

    public void setPIDConstants (double kP, double kI, double kD, double kV, double kA) {
        controller = new ProfiledPIDController(kP, kI, kD, new Constraints(kV, kA));
    }

    public void setPIDConstants (double kP, double kI, double kD, Constraints constraints) {
        controller = new ProfiledPIDController(kP, kI, kD, constraints);
    }

    public void setPIDEnableContinuousInput(Rotation2d minimum, Rotation2d maximum) {
        controller.enableContinuousInput(minimum.getRotations(), maximum.getRotations());
    }

    public void setPIDEnableContinuousInput(double minimum, double maximum) {
        controller.enableContinuousInput(minimum, maximum);
    }

    public void setPIDDisableContinuousInput() {
        controller.disableContinuousInput();
    }

    public void setPIDTolerance(Rotation2d position, Rotation2d velocity) {
        controller.setTolerance(position.getRotations(), velocity.getRotations());
    }

    public void setPIDTolerance(double position, double velocity) {
        controller.setTolerance(position, velocity);
    }

    public void setPIDPosition(Rotation2d currentPosition, Rotation2d wantedPosition) {
        setFeededVoltage(controller.calculate(currentPosition.getRotations(), wantedPosition.getRotations()), currentPosition);
    }

    public void setPIDPosition(double currentPosition, double wantedPosition) {
        setFeededVoltage(controller.calculate(currentPosition, wantedPosition), currentPosition);
    }

    public void setPIDPositionDeg(double currentPosition, double wantedPosition) {
        setFeededVoltage(controller.calculate(currentPosition, wantedPosition), currentPosition / 360);
    }



    public double getPIDCalculation(double currentPosition, double wantedPosition) {
        return controller.calculate(currentPosition, wantedPosition);
    }
}
