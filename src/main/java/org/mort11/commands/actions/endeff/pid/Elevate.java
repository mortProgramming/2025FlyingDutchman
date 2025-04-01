package org.mort11.commands.actions.endeff.pid;

import static org.mort11.config.constants.PhysicalConstants.Elevator.*;
import org.mort11.subsystems.Elevator;
import static org.mort11.config.constants.PIDConstants.Elevator.POS_CONSTRAINTS;

import edu.wpi.first.math.trajectory.TrapezoidProfile.Constraints;
import edu.wpi.first.wpilibj2.command.Command;

public class Elevate extends Command {
    private Elevator elevator;
    private double targetPosition;

    private double elevatorSpeed;

    public Elevate(double targetPosition) {
        this.elevator = Elevator.getInstance();
        this.targetPosition = targetPosition;
        this.elevatorSpeed = POS_CONSTRAINTS.maxVelocity;

        addRequirements(elevator);
    }

    public Elevate(double targetPosition, double elevatorSpeed) {
        this.elevator = Elevator.getInstance();
        this.targetPosition = targetPosition;
        this.elevatorSpeed = elevatorSpeed;

        addRequirements(elevator);
    }

    @Override
    public void initialize() {
        elevator.getPIDController().reset(elevator.getElevatorPositionInches());
        elevator.getPIDController().setConstraints(new Constraints(elevatorSpeed, POS_CONSTRAINTS.maxAcceleration));
    }

    @Override
    public void execute() {
        // elevator.setElevatorPosition(targetPosition);
        elevator.setElevatorMotorPercent(
            -elevator.getPIDController().calculate(
                elevator.getElevatorPositionInches(), 
                targetPosition
            )
        );
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        elevator.setElevatorMotorPercent(0);
    }

    public static Command l1() {
        return new Elevate(ELEVATOR_L1_HEIGHT);
    }

    public static Command l2() {
        return new Elevate(ELEVATOR_L2_HEIGHT);
    }

    public static Command l3() {
        return new Elevate(ELEVATOR_L3_HEIGHT);
    }

    public static Command l4() {
        return new Elevate(ELEVATOR_L4_HEIGHT);
    }

    public static Command intake() {
        return new Elevate(ELEVATOR_INTAKE_HEIGHT);
    }

    public static Command autoIntake() {
        return new Elevate(ELEVATOR_AUTO_INTAKE_HEIGHT);
    }

    public static Command zero() {
        return new Elevate(ELEVATOR_LOWER_LIMIT_SWITCH_HEIGHT);
    }

    public static Command max() {
        return new Elevate(-ELEVATOR_UPPER_LIMIT_SWITCH_HEIGHT);
    }

    public static Command rest() {
        return new Elevate(ELEVATOR_REST_HEIGHT);
    }

    public static Command lowAlgae() {
        return new Elevate(ELEVATOR_LOW_ALGAE_HEIGHT);
    }

    public static Command highAlgae() {
        return new Elevate(ELEVATOR_HIGH_ALGAE_HEIGHT);
    }

    public static Command processor() {
        return new Elevate(ELEVATOR_PROCESSOR_HEIGHT);
    }

    public static Command floor() {
        return new Elevate(ELEVATOR_FLOOR_HEIGHT);
    }

    public static Command barge() {
        return new Elevate(ELEVATOR_BARGE_HEIGHT);
    }
}
