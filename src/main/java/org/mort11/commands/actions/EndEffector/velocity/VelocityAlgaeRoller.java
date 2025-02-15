package org.mort11.commands.actions.endeffector.velocity;

import static org.mort11.config.constants.PhysicalConstants.AlgaeRoller.*;

import org.mort11.subsystems.AlgaeRoller;

import edu.wpi.first.wpilibj2.command.Command;

public class VelocityAlgaeRoller extends Command {
  private AlgaeRoller algaeRoller;

  private double speed;

  public VelocityAlgaeRoller(double speed) {
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
    return new VelocityAlgaeRoller(INTAKE_SPEED);
  }

  public static Command outtake() {
    return new VelocityAlgaeRoller(OUTAKE_SPEED);
  }

  public static Command nothing(){
    return new VelocityAlgaeRoller(0);
  }
}
