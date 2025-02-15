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
            tiki.getPIDController().calculate(
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
    tiki.setPosition(setpoint);
  }

  public static Command l1() {
    return new SetTikiTorchArm(L1_SCORE);
  }

  public static Command l23() {
    return new SetTikiTorchArm(L23_SCORE);
  }

  public static Command l4() {
    return new SetTikiTorchArm(L4_SCORE);
  }

  public static Command intake() {
    return new SetTikiTorchArm(INTAKE);
  }

  public static Command rest() {
    return new SetTikiTorchArm(REST);
  }
}
