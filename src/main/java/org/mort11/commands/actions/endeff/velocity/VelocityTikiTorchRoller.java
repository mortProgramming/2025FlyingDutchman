package org.mort11.commands.actions.endeff.velocity;

import static org.mort11.config.constants.PhysicalConstants.TikiTorchRoller.*;

import org.mort11.subsystems.TikiTorchRoller;

import edu.wpi.first.wpilibj2.command.Command;

public class VelocityTikiTorchRoller extends Command {
  private TikiTorchRoller tikiTorchRoller;

  private double speed;

  public VelocityTikiTorchRoller(double speed) {
    tikiTorchRoller = TikiTorchRoller.getInstance();

    this.speed = speed;

    addRequirements(tikiTorchRoller);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute(){
    tikiTorchRoller.setRollerSpeed(speed);
  }

  @Override
  public boolean isFinished(){
    return false;
  }

  @Override
  public void end(boolean interrupted){
    tikiTorchRoller.setRollerSpeed(REST_SPEED);
  }

  public static Command intake() {
    return new VelocityTikiTorchRoller(INTAKE_SPEED);
  }

  public static Command outtake() {
    return new VelocityTikiTorchRoller(OUTAKE_SPEED);
  }

  public static Command rest(){
    return new VelocityTikiTorchRoller(REST_SPEED);
  }

}


