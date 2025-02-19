package org.mort11.commands.actions.endeffector.pid;

import static org.mort11.config.constants.PhysicalConstants.TikiTorchArm.*;

import org.mort11.subsystems.TikiTorchArm;

import edu.wpi.first.wpilibj2.command.Command;

public class SetTikiTorchArm extends Command {
  private double setpoint;
  private TikiTorchArm tiki;

  public SetTikiTorchArm(double setpoint) {
    this.setpoint = setpoint;
    tiki = TikiTorchArm.getInstance();

    addRequirements(TikiTorchArm.getInstance());

    }

  @Override
  public void initialize() {

  }

  @Override
  public void execute() {
    tiki.setArmMotorPercent(
      -tiki.getPIDController().calculate(
        tiki.encoderToDegrees(), 
        setpoint
      )
    );
  }

  @Override
  public boolean isFinished() {
    return false;
  }

  @Override
  public void end(boolean interrupted) {
    tiki.setArmMotorPercent(0);
  }

  public static Command l1() {
    return new SetTikiTorchArm(TIKI_L1_SCORE);
  }

  public static Command score() {
    return new SetTikiTorchArm(TIKI_L234_SCORE);
  }

  public static Command intake() {
    return new SetTikiTorchArm(TIKI_INTAKE);
  }

  public static Command rest() {
    return new SetTikiTorchArm(TIKI_REST);
  }

  public static Command algaeClear() {
    return new SetTikiTorchArm(TIKI_ALGAE_CLEAR);
  }
}
