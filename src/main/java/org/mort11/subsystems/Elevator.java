package org.mort11.subsystems;

import static org.mort11.config.constants.PIDConstants.Elevator.*;
import static org.mort11.config.constants.PhysicalConstants.Elevator.*;
import static org.mort11.config.constants.PhysicalConstants.ROBOT_VOLTAGE;
import static org.mort11.config.constants.PortConstants.Elevator.MOTOR;
import static org.mort11.library.hardware.motor.MotorTypeEnum.VORTEX;
import org.mort11.library.hardware.motor.Motor;

import edu.wpi.first.math.controller.ElevatorFeedforward;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Elevator extends SubsystemBase {
    private static Elevator elevator;

    private Motor motor;
    // private double motorSpeed, elevatorPosition, rotationsCompleted;
    private double motorSpeed, offset;

    private ProfiledPIDController controller;
    private ElevatorFeedforward feedforward;

    private Elevator() {
        motor = new Motor(VORTEX, MOTOR);

        controller = new ProfiledPIDController(POS_KP, POS_KI, POS_KD, POS_CONSTRAINTS);
        feedforward = new ElevatorFeedforward(POS_KS, POS_KG, POS_KV, POS_KA);

        motorSpeed = 0;
        // elevatorPosition = START_HEIGHT;
        // rotationsCompleted = 0;

        offset = START_HEIGHT;
    }

    @Override
    public void periodic() {
        motor.setVoltage(motorSpeed * ROBOT_VOLTAGE);

        // elevatorPosition = calculateElevatorPosition();
    }

    public void setElevatorPosition(double positionInches) {
        motorSpeed = controller.calculate(positionInches, getElevatorPositionInches()) + 
            feedforward.calculate(getElevatorVelocityRPM()
        );
    }



    // public double getElevatorPositionInches() {
    //     return elevatorPosition;
    // }

    public double getElevatorPositionInches() {
        return motor.getPositionRotations() * ROTATIONS_TO_INCHES + offset;
    }

    // public double getElevatorVelocityRPM() {
    //     return motor.getAbsoluteValueEncoderVelocity() * ROTATIONS_TO_INCHES;
    // }

    public double getElevatorVelocityRPM() {
        return motor.getAbsoluteValueEncoderVelocity() * ROTATIONS_TO_INCHES;
    }

    // public double getAbsoluteEncoderPositionRotations() {
    //     return motor.getAbsoluteValueEncoderPosition();
    // }



    // public double calculateElevatorPosition() {
    //     double inchesFound = (getAbsoluteEncoderPositionRotations() + rotationsCompleted) * ROTATIONS_TO_INCHES;
    //     if((inchesFound - elevatorPosition) > MAXIMUM_INCH_CHANGE) {
    //         rotationsCompleted += 1;
    //     }

    //     if((elevatorPosition - inchesFound) > MAXIMUM_INCH_CHANGE) {
    //         rotationsCompleted -= 1;
    //     }

    //     return (getAbsoluteEncoderPositionRotations() + rotationsCompleted) * ROTATIONS_TO_INCHES;
    // }

    public static Elevator getInstance() {
        if(elevator == null) {
            elevator = new Elevator();
        }
        return elevator;
    }
}
