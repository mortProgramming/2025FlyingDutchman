package org.mort11.subsystems;

import static org.mort11.library.hardware.motor.MotorTypeEnum.VORTEX;

import org.mort11.library.hardware.motor.Motor;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Elevator extends SubsystemBase {
    private Elevator elevator;

    private Motor leftMotor, rightMotor;

    private Elevator() {
        leftMotor = new Motor(VORTEX, 0);
    }
}
