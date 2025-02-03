package org.mort11.commands.actions.endeffector;
import org.mort11.subsystems.AlgaeScoop;

import edu.wpi.first.wpilibj2.command.Command;

public class SetArm extends Command {
  private double setpoint;

  public SetArm(double setpoint) {
    this.setpoint = setpoint;

    addRequirements(AlgaeScoop.getAlgaeScoop());
  }

  @Override
  public void initialize() {

  }

  @Override
  public void execute() {
    AlgaeScoop.setPosition(setpoint);
  }

  @Override
  public boolean isFinished() {
    return false;
  }

  @Override
  public void end(boolean interrupted) {
    AlgaeScoop.setPosition(setpoint);
  }
    
}

