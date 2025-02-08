package org.mort11.commands.actions.endeffector;

import org.mort11.config.constants.PhysicalConstants;
import org.mort11.subsystems.Elevator;

import edu.wpi.first.wpilibj2.command.Command;

import org.mort11.subsystems.TikiTorchArm;

public class MoveElevator extends Command  {
    
    private Elevator elevator;

    private double incrementInPerSecond;

    public MoveElevator(double incrementInPerSecond) {
        this.incrementInPerSecond = incrementInPerSecond;

        elevator = Elevator.getInstance();

        addRequirements(elevator);
    }

    @Override
    public void initialize(){}

    @Override
    public void execute() {
        double increment = incrementInPerSecond / 50;
        elevator.setElevatorPosition(elevator.getElevatorPositionInches() + increment);
    }

  @Override
  public boolean isFinished(){
    return false;
  }

  @Override
  public void end(boolean interrupted){
    
  }
}
