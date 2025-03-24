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
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Elevator extends SubsystemBase {
    private static Elevator elevator;

    private Motor motor;
    private Encoder encoder;
    private DigitalInput lowerLimitSwitch, upperLimitSwitch;

    private double motorSpeed, elevatorPosition, rotationsCompleted;

    private ProfiledPIDController controller;
    private ElevatorFeedforward feedforward;

    private Elevator() {
        motor = new Motor(VORTEX, MOTOR);

        encoder = new Encoder(THROUGHBORE, ENCODER);

        lowerLimitSwitch = new DigitalInput(LOWER_LIMIT_SWITCH);
        upperLimitSwitch = new DigitalInput(UPPER_LIMIT_SWITCH);

        controller = new ProfiledPIDController(POS_KP, POS_KI, POS_KD, POS_CONSTRAINTS);
        feedforward = new ElevatorFeedforward(POS_KS, POS_KG, POS_KV, POS_KA);

        motorSpeed = 0;
        elevatorPosition = 0;
        rotationsCompleted = ELEVATOR_OFFSET / ROTATIONS_TO_INCHES;
    }

    @Override
    public void periodic() {
        motor.setVoltage(motorSpeed * ROBOT_VOLTAGE);

        elevatorPosition = calculateElevatorPosition();

        fixWithLimitSwitch();

        SmartDashboard.putNumber("Elevator Height", getElevatorPositionInches());
        SmartDashboard.putNumber("Elevator Speed Inches", getElevatorVelocityInches());
        SmartDashboard.putBoolean("Lower Limit Switch", getAtLowerLimitSwitch());
        SmartDashboard.putBoolean("Upper Limit Switch", getAtUpperLimitSwitch());
    }

    public void setElevatorMotorPercent(double motorSpeed) {
        this.motorSpeed = motorSpeed + POS_KG;
    }

    public void setElevatorPosition(double newPoseInches) {
        rotationsCompleted -= (getElevatorPositionInches() + newPoseInches) / ROTATIONS_TO_INCHES;

        elevatorPosition = (getAbsoluteEncoderPositionRotations() + rotationsCompleted) * ROTATIONS_TO_INCHES;
    }

    public void fixWithLimitSwitch() {
        if (getAtLowerLimitSwitch()) {
            setElevatorPosition(ELEVATOR_LOWER_LIMIT_SWITCH_HEIGHT);
        }

        if(getAtUpperLimitSwitch()) {
            setElevatorPosition(ELEVATOR_UPPER_LIMIT_SWITCH_HEIGHT);
        }
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

    public boolean getAtLowerLimitSwitch() {
        return !lowerLimitSwitch.get();
    }

    public boolean getAtUpperLimitSwitch() {
        return !upperLimitSwitch.get();
        // return false;
    }

    public static Elevator getInstance() {
        if(elevator == null) {
            elevator = new Elevator();
        }
        return elevator;
    }
}
