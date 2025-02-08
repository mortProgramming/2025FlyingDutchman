package org.mort11.commands.actions.endeffector;

import static org.mort11.config.constants.PhysicalConstants.AlgaeRoller.*;

import org.mort11.subsystems.AlgaeRoller;

import edu.wpi.first.wpilibj2.command.Command;

public class SetAlgaeRoller extends Command {
  private AlgaeRoller algaeRoller;

  private double speed;

  public SetAlgaeRoller(double speed) {
    algaeRoller = AlgaeRoller.getInstance();

    this.speed = speed;

    addRequirements(algaeRoller);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute(){
    algaeRoller.setRollerSpeed(speed);
  }

  @Override
  public boolean isFinished(){
    return false;
  }

  @Override
  public void end(boolean interrupted){}

  public static Command intake() {
    return new SetAlgaeRoller(INTAKE_SPEED);
  }

  public static Command outtake() {
    return new SetAlgaeRoller(OUTAKE_SPEED);
  }
}
