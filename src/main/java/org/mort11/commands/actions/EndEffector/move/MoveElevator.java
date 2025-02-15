package org.mort11.commands.actions.endeffector.move;

import org.mort11.subsystems.Elevator;

import edu.wpi.first.wpilibj2.command.Command;

public class MoveElevator extends Command  {
    
    private Elevator elevator;

    private double speed, totalDistanceChanged, startPosition;

    public MoveElevator(double speed) {
        this.speed = speed;

        elevator = Elevator.getInstance();

        totalDistanceChanged = 0;

        addRequirements(elevator);
    }

    @Override
    public void initialize(){
      startPosition = elevator.getElevatorPositionInches();
    }

    @Override
    public void execute() {
        totalDistanceChanged += speed / 50;
        elevator.setElevatorPosition(startPosition + totalDistanceChanged);
    }

  @Override
  public boolean isFinished(){
    return false;
  }

  @Override
  public void end(boolean interrupted){
    
  }
}
