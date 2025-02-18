package org.mort11.commands.actions.endeffector.pid;

import static org.mort11.config.constants.PhysicalConstants.AlgaeArm.*;

import org.mort11.subsystems.AlgaeArm;

import edu.wpi.first.wpilibj2.command.Command;

public class SetAlgaeArm extends Command {
  private AlgaeArm algaeArm;

  private double setpoint;

  public SetAlgaeArm(double setpoint) {
    algaeArm = AlgaeArm.getInstance();

    this.setpoint = setpoint;

    addRequirements(algaeArm);
  }

  @Override
  public void initialize(){
  }

  @Override
  public void execute(){
    algaeArm.setArmPercent(
          algaeArm.getPIDController().calculate(
                algaeArm.encoderToDegrees(), 
                setpoint
            )
        );
  }

  @Override
  public boolean isFinished(){
    return false;
  }

  @Override
  public void end(boolean interrupted){
    algaeArm.setArmPercent(0);
  }

  public static Command l23Intake() {
    return new SetAlgaeArm(ALGAE_REEF_INTAKE);
  }

  public static Command floor() {
    return new SetAlgaeArm(ALGAE_FLOOR_INTAKE);
  }

  public static Command processor() {
    return new SetAlgaeArm(ALGAE_PROCESSOR_SCORE);
  }

  public static Command barge() {
    return new SetAlgaeArm(ALGAE_BARGE_SCORE);
  }

  public static Command rest() {
    return new SetAlgaeArm(ALGAE_REST);
  }
}
