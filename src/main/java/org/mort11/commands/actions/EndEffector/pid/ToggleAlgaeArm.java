package org.mort11.commands.actions.endeffector.pid;

import static org.mort11.config.constants.PhysicalConstants.AlgaeArm.REST;
import static org.mort11.config.constants.PhysicalConstants.AlgaeArm.L23_INTAKE;

import java.util.function.BooleanSupplier;

import org.mort11.subsystems.AlgaeArm;

import edu.wpi.first.wpilibj2.command.Command;

public class ToggleAlgaeArm extends Command {
  private AlgaeArm algaeArm;
  private BooleanSupplier button;
  private boolean oldButton;
  private boolean altButton;

  public ToggleAlgaeArm(BooleanSupplier button) {
    algaeArm = AlgaeArm.getInstance();

    this.button = button;

    addRequirements(algaeArm);
  }

  @Override
  public void initialize(){

    oldButton = false;
    altButton = false;

  }

  @Override
  public void execute(){
    if(oldButton && button.getAsBoolean() && altButton == false){
        altButton = true;
    }
    else if(oldButton && button.getAsBoolean() && altButton) {
        altButton = false;
    }

    if (altButton) {
      algaeArm.setArmPercent(
        algaeArm.getPIDController().calculate(
              algaeArm.encoderToDegrees(), 
              REST
          )
      );
    }
    else {
      algaeArm.setArmPercent(
        algaeArm.getPIDController().calculate(
              algaeArm.encoderToDegrees(), 
              L23_INTAKE
          )
      );
    }

    oldButton = button.getAsBoolean();
  }

  @Override
  public boolean isFinished(){
    return false;
  }

  @Override
  public void end(boolean interrupted){
    algaeArm.setArmPercent(0);
  }
}