package org.mort11.library.hardware.motor;

import edu.wpi.first.math.controller.PIDController;

public class PIDMotor extends Motor {
    public PIDController controller;

    public PIDMotor(MotorTypeEnum motorType, int motorID) {
        super(motorType, motorID);

        controller = new PIDController(0, 0, 0);
    }

    public void setPIDValues(double kP, double kI, double kD) {
        controller = new PIDController(kP, kI, kD);
    }

    public void setPIDEnableContinuousInput(double minimum, double maximum) {
        controller.enableContinuousInput(minimum, maximum);
    }

    public void setPIDDisableContinuousInput() {
        controller.disableContinuousInput();
    }

    public void setPIDTolerance(double position, double velocity) {
        controller.setTolerance(position, velocity);
    }

    public void setPositionRotations(double position, double setpoint) {
        super.setVoltage(controller.calculate(position, setpoint));
    }
}
