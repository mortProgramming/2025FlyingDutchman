package org.mort11.commands.actions.endeffector.velocity;

import org.mort11.subsystems.Elevator;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;

public class AllTheWayDown extends Command  {
    
    private Elevator elevator;

    private Timer timer;

    public AllTheWayDown() {
        elevator = Elevator.getInstance();

        timer = new Timer();

        addRequirements(elevator);
    }

    @Override
    public void initialize() {
      timer.reset();
      timer.start();
    }

    @Override
    public void execute() {
      elevator.setElevatorMotorPercent(0.25);

      elevator.setElevatorPosition(0);
    }

  @Override
  public boolean isFinished(){
    return elevator.getAtLowerLimitSwitch() && timer.get() > 0.25;
  }

  @Override
  public void end(boolean interrupted){
    elevator.setElevatorMotorPercent(0);

    elevator.setElevatorPosition(0);
  }
}
