package org.mort11.commands.actions.endeffector;
import org.mort11.subsystems.AlgaeScoop;

import edu.wpi.first.wpilibj2.command.Command;

public class SetScoop extends Command {
  private AlgaeScoop algaeScoop;

  private double setpoint;

  public SetScoop(double setpoint) {
    algaeScoop = AlgaeScoop.getInstance();

    this.setpoint = setpoint;

    addRequirements(algaeScoop);
  }

  @Override
  public void initialize(){
  }

  @Override
  public void execute(){
    algaeScoop.setPosition(setpoint);
  }

  @Override
  public boolean isFinished(){
    return false;
  }

  @Override
  public void end(boolean interrupted){
    algaeScoop.setPosition(setpoint);
  }
}

