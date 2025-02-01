package org.mort11.commands.actions.EndEffector;
import org.mort11.subsystems.TikiTorch;

import edu.wpi.first.wpilibj2.command.Command;

public class SetArm extends Command {
  private double setpoint;

  public SetArm(double setpoint) {
    this.setpoint = setpoint;

    addRequirements(TikiTorch.getTikiTorch());

    }

  @Override
  public void initialize() {

  }

  @Override
  public void execute() {
    TikiTorch.setPosition(setpoint);
  }

  @Override
  public boolean isFinished() {
    return false;
  }

  @Override
  public void end(boolean interrupted) {
    TikiTorch.setPosition(setpoint);
  }
    
}
