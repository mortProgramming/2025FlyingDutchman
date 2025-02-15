package org.mort11.commands.actions.endeffector.velocity;

import org.mort11.subsystems.TikiTorchArm;

import edu.wpi.first.wpilibj2.command.Command;

public class VelocityTikiTorchArm extends Command  {
    
    private TikiTorchArm tikiTorchArm;
    private double testingSpeed;

    public VelocityTikiTorchArm(double testingSpeed){
      this.testingSpeed = testingSpeed;
      tikiTorchArm = tikiTorchArm.getInstance();

      addRequirements(tikiTorchArm);
    }

    @Override
    public void execute() {
      tikiTorchArm.setArmMotorPercent(testingSpeed);
    }

  @Override
  public boolean isFinished(){
    return false;
  }

  @Override
  public void end(boolean interrupted){
    
  }
}
