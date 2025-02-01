package org.mort11.subsystems;

import static org.mort11.library.hardware.motor.MotorTypeEnum.VORTEX;

import org.mort11.library.hardware.motor.MotorGroup;

import static org.mort11.config.constants.PhysicalConstants.ROBOT_VOLTAGE;
import static org.mort11.config.constants.PhysicalConstants.Elevator.*;
import static org.mort11.config.constants.PIDConstants.Elevator.*;
import static org.mort11.config.constants.PortConstants.Elevator.*;

import edu.wpi.first.math.controller.ElevatorFeedforward;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Elevator extends SubsystemBase {
    private static Elevator elevator;

    private MotorGroup motors;
    private double motorsSpeed;

    private ProfiledPIDController controller;
    private ElevatorFeedforward feedforward;

    private Elevator() {
        motors = new MotorGroup(VORTEX, LEFT_MOTOR, RIGHT_MOTOR);

        motors.setDirectionFlip(1, true);

        controller = new ProfiledPIDController(POS_KP, POS_KI, POS_KD, POS_CONSTRAINTS);
        feedforward = new ElevatorFeedforward(POS_KS, POS_KG, POS_KV, POS_KA);

        motorsSpeed = 0;
    }

    @Override
    public void periodic() {
        motors.setVoltage(motorsSpeed * ROBOT_VOLTAGE);
    }

    public void setElevatorPosition(double positionInches) {
        motorsSpeed = controller.calculate(positionInches, getElevatorPositionInches()) + 
            feedforward.calculate(getElevatorVelocityInches()
        );
    }



    public double getElevatorPositionInches() {
        return motors.getPositionRotations() * POSE_TO_HEIGHT + START_HEIGHT;
    }

    // 60 is seconds per minute
    public double getElevatorVelocityInches() {
        return motors.getVelocityRPM() * 60 * POSE_TO_HEIGHT;
    }

    public Elevator getInstance() {
        if(elevator == null) {
            elevator = new Elevator();
        }
        return elevator;
    }
}
