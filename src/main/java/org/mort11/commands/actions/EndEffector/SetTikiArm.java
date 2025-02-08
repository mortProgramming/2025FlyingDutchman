package org.mort11.commands.actions.endeffector;

import org.mort11.subsystems.TikiTorch;

import edu.wpi.first.wpilibj2.command.Command;

public class SetTikiArm extends Command {
  private double setpoint;
  private TikiTorch tiki;

  public SetTikiArm(double setpoint) {
    this.setpoint = setpoint;
    tiki = TikiTorch.getInstance();

    addRequirements(TikiTorch.getInstance());

    }

  @Override
  public void initialize() {

  }

  @Override
  public void execute() {
    tiki.setPosition(setpoint);
  }

  @Override
  public boolean isFinished() {
    return false;
  }

  @Override
  public void end(boolean interrupted) {
    tiki.setPosition(setpoint);
  }
    
}
