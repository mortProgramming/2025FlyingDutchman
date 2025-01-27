package org.mort11.library.subsystems.flywheel;

import org.mort11.library.hardware.motor.MotorIntf;
import org.mort11.library.hardware.motor.MotorTypeEnum;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile.Constraints;

public class PIDFlywheel extends Flywheel {
    public ProfiledPIDController controller;

    public PIDFlywheel(MotorTypeEnum motorType, int motorID) {
        super(motorType, motorID);

        controller = new ProfiledPIDController(0, 0, 0, new Constraints(0, 0));
    }
    
    public PIDFlywheel(MotorIntf motor) {
        super(motor);

        controller = new ProfiledPIDController(0, 0, 0, new Constraints(0, 0));
    }

    public void setPIDConstants(double kP, double kI, double kD, double kV, double kA) {
        controller = new ProfiledPIDController(kP, kI, kD, new Constraints(kV, kA));
    }

    public void setPIDConstants(double kP, double kI, double kD, Constraints constraints) {
        controller = new ProfiledPIDController(kP, kI, kD, constraints);
    }

    public void setPIDTolerance(double velocity, double acceleration) {
        controller.setTolerance(velocity, acceleration);
    }

    public void setPIDPosition(double wantedVelocityRPM) {
        setFeededVoltage(getPIDCalculation(wantedVelocityRPM), motor.getVelocityRPM());
    }



    public double getPIDCalculation(double wantedVelocityRPM) {
        return controller.calculate(motor.getVelocityRPM(), wantedVelocityRPM);
    }
}
