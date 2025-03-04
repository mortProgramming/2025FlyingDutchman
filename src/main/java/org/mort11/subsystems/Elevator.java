package org.mort11.subsystems;

import static org.mort11.config.constants.PIDConstants.Elevator.*;
import static org.mort11.config.constants.PhysicalConstants.Elevator.*;
import static org.mort11.config.constants.PhysicalConstants.ROBOT_VOLTAGE;
import static org.mort11.config.constants.PortConstants.Elevator.*;
import static org.mort11.library.hardware.encoder.EncoderTypeEnum.THROUGHBORE;
import static org.mort11.library.hardware.motor.MotorTypeEnum.VORTEX;

import org.mort11.library.hardware.encoder.Encoder;
import org.mort11.library.hardware.motor.Motor;

import edu.wpi.first.math.controller.ElevatorFeedforward;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Elevator extends SubsystemBase {
    private static Elevator elevator;

    private Motor motor;
    private Encoder encoder;
    private double motorSpeed, elevatorPosition, rotationsCompleted;

    private ProfiledPIDController controller;
    private ElevatorFeedforward feedforward;

    private Elevator() {
        motor = new Motor(VORTEX, MOTOR);

        encoder = new Encoder(THROUGHBORE, ENCODER);

        controller = new ProfiledPIDController(POS_KP, POS_KI, POS_KD, POS_CONSTRAINTS);
        feedforward = new ElevatorFeedforward(POS_KS, POS_KG, POS_KV, POS_KA);

        motorSpeed = 0;
        elevatorPosition = 0;
        rotationsCompleted = ELEVATOR_OFFSET / ROTATIONS_TO_INCHES;
    }

    @Override
    public void periodic() {
        // if(motorSpeed > 0.2) {motorSpeed = 0.2;}
        motor.setVoltage(motorSpeed * ROBOT_VOLTAGE);

        elevatorPosition = calculateElevatorPosition();
        SmartDashboard.putNumber("Elevator Height", getElevatorPositionInches());
        SmartDashboard.putNumber("Elevator Speed Inches", getElevatorVelocityInches());
    }

    public void setElevatorPosition(double positionInches) {
        motorSpeed = controller.calculate(positionInches, getElevatorPositionInches()) + 
        POS_KG;
    }

    public void setElevatorMotorPercent(double motorSpeed) {
        this.motorSpeed = motorSpeed + POS_KG;
    }



    public double getElevatorPositionInches() {
        return elevatorPosition;
        // return getRelativeElevatorPosition();
    }

    public double getElevatorVelocityInches() {
        return encoder.getVelocityRotations() * ROTATIONS_TO_INCHES;
    }

    public double getAbsoluteEncoderPositionRotations() {
        return 1 - encoder.getPosition().getRotations();
    }

    public ProfiledPIDController getPIDController() {
        return controller;
    }



    public double calculateElevatorPosition() {
        double inchesFound = (getAbsoluteEncoderPositionRotations() + rotationsCompleted) * ROTATIONS_TO_INCHES;
        if((inchesFound - elevatorPosition) > MAXIMUM_INCH_CHANGE) {
            rotationsCompleted -= 1;
        }

        if((elevatorPosition - inchesFound) > MAXIMUM_INCH_CHANGE) {
            rotationsCompleted += 1;
        }

        return (getAbsoluteEncoderPositionRotations() + rotationsCompleted) * ROTATIONS_TO_INCHES;
    }

    public double getRelativeElevatorPosition() {
        return (
                (-motor.getPositionRotations() * ROTATIONS_TO_INCHES) / GEAR_RATIO
            ) 
            + ELEVATOR_START_HEIGHT;
    }

    public static Elevator getInstance() {
        if(elevator == null) {
            elevator = new Elevator();
        }
        return elevator;
    }
}
