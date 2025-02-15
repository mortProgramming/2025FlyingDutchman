package org.mort11.commands.actions.endeffector.velocity;

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
  public void end(boolean interrupted){}

  public static Command intake() {
    return new VelocityTikiTorchRoller(INTAKE_SPEED);
  }

  public static Command outtake() {
    return new VelocityTikiTorchRoller(OUTAKE_SPEED);
  }

  public static Command nothing(){
    return new VelocityTikiTorchRoller(0);
  }

}


