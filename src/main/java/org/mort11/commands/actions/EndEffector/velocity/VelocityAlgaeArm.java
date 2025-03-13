package org.mort11.commands.actions.endeffector.velocity;

import org.mort11.subsystems.AlgaeArm;

import edu.wpi.first.wpilibj2.command.Command;

public class VelocityAlgaeArm extends Command  {
    
    private AlgaeArm algaeArm;
    private double testingSpeed;

    public VelocityAlgaeArm(double testingSpeed){
        this.testingSpeed = testingSpeed;
        algaeArm = algaeArm.getInstance();

        addRequirements(algaeArm);
    }

    @Override
    public void execute() {
      algaeArm.getPIDController().calculate(
        algaeArm.encoderToDegrees(), 
        45
      );
      algaeArm.setArmPercent(testingSpeed);
    }

  @Override
  public boolean isFinished(){
    return false;
  }

  @Override
  public void end(boolean interrupted){}
}
