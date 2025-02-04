package org.mort11.commands.actions.endeffector;

import org.mort11.subsystems.Elevator;

import edu.wpi.first.wpilibj2.command.Command;

public class Elevate extends Command {
    private final Elevator elevator;
    private final double targetPosition;

    public Elevate(Elevator elevator, double targetPosition) {
        this.elevator = elevator;
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
}
