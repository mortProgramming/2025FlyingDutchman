package org.mort11.subsystems;

import static org.mort11.config.constants.PIDConstants.Elevator.POS_CONSTRAINTS;
import static org.mort11.config.constants.PIDConstants.Elevator.POS_KA;
import static org.mort11.config.constants.PIDConstants.Elevator.POS_KD;
import static org.mort11.config.constants.PIDConstants.Elevator.POS_KG;
import static org.mort11.config.constants.PIDConstants.Elevator.POS_KI;
import static org.mort11.config.constants.PIDConstants.Elevator.POS_KP;
import static org.mort11.config.constants.PIDConstants.Elevator.POS_KS;
import static org.mort11.config.constants.PIDConstants.Elevator.POS_KV;
import static org.mort11.config.constants.PhysicalConstants.Elevator.POSE_TO_HEIGHT;
import static org.mort11.config.constants.PhysicalConstants.Elevator.START_HEIGHT;
import static org.mort11.config.constants.PhysicalConstants.ROBOT_VOLTAGE;
import static org.mort11.config.constants.PortConstants.Elevator.LEFT_MOTOR;
import static org.mort11.config.constants.PortConstants.Elevator.RIGHT_MOTOR;
import org.mort11.library.hardware.motor.MotorGroup;
import static org.mort11.library.hardware.motor.MotorTypeEnum.VORTEX;

import edu.wpi.first.math.controller.ElevatorFeedforward;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Elevator extends SubsystemBase {
    private static Elevator elevator;

    private MotorGroup motors;
    private double motorsSpeed, elevatorOffset;

    private ProfiledPIDController controller;
    private ElevatorFeedforward feedforward;

    private Elevator() {
        motors = new MotorGroup(VORTEX, LEFT_MOTOR, RIGHT_MOTOR);

        motors.setDirectionFlip(1, true);

        controller = new ProfiledPIDController(POS_KP, POS_KI, POS_KD, POS_CONSTRAINTS);
        feedforward = new ElevatorFeedforward(POS_KS, POS_KG, POS_KV, POS_KA);

        motorsSpeed = 0;
        elevatorOffset = START_HEIGHT;
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

    public void setElevatorOffset(double elevatorOffset) {
        this.elevatorOffset = elevatorOffset;
    }



    public double getElevatorPositionInches() {
        return motors.getPositionRotations() * POSE_TO_HEIGHT + elevatorOffset;
    }

    // 60 is seconds per minute
    public double getElevatorVelocityInches() {
        return motors.getVelocityRPM() * 60 * POSE_TO_HEIGHT;
    }

    public boolean getTopLimitSwitch() {
        return motors.getMotor(0).getForwardLimitSwitch();
    }

    public boolean getBottomLimitSwitch() {
        return motors.getMotor(0).getForwardLimitSwitch();
    }

    public static Elevator getInstance() {
        if(elevator == null) {
            elevator = new Elevator();
        }
        return elevator;
    }
}
