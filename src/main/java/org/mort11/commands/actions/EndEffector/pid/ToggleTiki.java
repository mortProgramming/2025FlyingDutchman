package org.mort11.commands.actions.endeffector.pid;

import static org.mort11.config.constants.PhysicalConstants.TikiTorchArm.INTAKE;
import static org.mort11.config.constants.PhysicalConstants.TikiTorchArm.L4_SCORE;

import java.util.function.BooleanSupplier;

import org.mort11.subsystems.TikiTorchArm;

import edu.wpi.first.wpilibj2.command.Command;

public class ToggleTiki extends Command {
  private TikiTorchArm tikiArm;
  private BooleanSupplier button;
  private boolean oldButton;
  private boolean altButton;

  public ToggleTiki(BooleanSupplier button) {
    tikiArm = TikiTorchArm.getInstance();

    this.button = button;

    addRequirements(tikiArm);
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
        tikiArm.setArmMotorPercent(
          -tikiArm.getPIDController().calculate(
                tikiArm.encoderToDegrees(), 
                L4_SCORE
            )
        );
    }
    else {
        tikiArm.setArmMotorPercent(
          -tikiArm.getPIDController().calculate(
                tikiArm.encoderToDegrees(), 
                INTAKE
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
    tikiArm.setArmMotorPercent(0);
  }
}