package org.mort11.commands.actions.endeffector;

import org.mort11.subsystems.Elevator;

import edu.wpi.first.wpilibj2.command.Command;

public class MoveElevator extends Command  {
    
    private Elevator elevator;

    private double incrementInPerSecond, totalDistanceChanged, startPosition;

    public MoveElevator(double incrementInPerSecond) {
        this.incrementInPerSecond = incrementInPerSecond;

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
        totalDistanceChanged += incrementInPerSecond / 50;
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
