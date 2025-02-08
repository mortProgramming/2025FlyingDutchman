package org.mort11.commands.actions.endeffector;

import org.mort11.subsystems.Elevator;

import static org.mort11.config.constants.PhysicalConstants.Elevator.*;

import edu.wpi.first.wpilibj2.command.Command;

public class Elevate extends Command {
    private final Elevator elevator;
    private final double targetPosition;

    public Elevate(double targetPosition) {
        this.elevator = Elevator.getInstance();
        this.targetPosition = targetPosition;

        addRequirements(elevator);
    }

    @Override
    public void execute() {
        elevator.setElevatorPosition(targetPosition);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {}

    public static Command l1() {
        return new Elevate(L1_HEIGHT);
    }

    public static Command l2() {
        return new Elevate(L2_HEIGHT);
    }

    public static Command l3() {
        return new Elevate(L3_HEIGHT);
    }

    public static Command l4() {
        return new Elevate(L4_HEIGHT);
    }

    public static Command intake() {
        return new Elevate(L1_HEIGHT);
    }

    public static Command rest() {
        return new Elevate(REST_HEIGHT);
    }

    public static Command lowAlgae() {
        return new Elevate(LOW_ALGAE_HEIGHT);
    }

    public static Command highAlgae() {
        return new Elevate(HIGH_ALGAE_HEIGHT);
    }

    public static Command processor() {
        return new Elevate(PROCESSOR_HEIGHT);
    }

    public static Command floor() {
        return new Elevate(FLOOR_HEIGHT);
    }

    public static Command barge() {
        return new Elevate(BARGE_HEIGHT);
    }
}
