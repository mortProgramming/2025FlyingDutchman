package org.mort11.commands.actions.endeffector.velocity;

import org.mort11.subsystems.Elevator;

import edu.wpi.first.wpilibj2.command.Command;

public class VelocityElevator extends Command  {
    
    private Elevator elevator;

    private double speed;

    public VelocityElevator(double speed) {
        this.speed = speed;

        elevator = Elevator.getInstance();

        addRequirements(elevator);
    }

    @Override
    public void execute() {
        elevator.setElevatorMotorPercent(speed);
    }

  @Override
  public boolean isFinished(){
    return false;
  }

  @Override
  public void end(boolean interrupted){
    elevator.setElevatorMotorPercent(0);
  }
}
