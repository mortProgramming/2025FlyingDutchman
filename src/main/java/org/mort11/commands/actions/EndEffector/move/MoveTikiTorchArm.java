package org.mort11.commands.actions.endeffector.move;

import org.mort11.subsystems.TikiTorchArm;

import edu.wpi.first.wpilibj2.command.Command;

public class MoveTikiTorchArm extends Command  {
    
    private TikiTorchArm tikiTorchArm;
    
    private double incrementDegPerSecond, totalDistanceChanged, startPosition;

    public MoveTikiTorchArm(double incrementDegPerSecond) {
        this.incrementDegPerSecond = incrementDegPerSecond;

        tikiTorchArm = TikiTorchArm.getInstance();

        totalDistanceChanged = 0;

        addRequirements(tikiTorchArm);
    }

    @Override
    public void initialize(){
      startPosition = tikiTorchArm.encoderToDegrees();
    }

    @Override
    public void execute() {
      totalDistanceChanged += incrementDegPerSecond / 50;
        // tikiTorchArm.setPosition(startPosition + totalDistanceChanged);
        tikiTorchArm.setArmMotorPercent(
            -tikiTorchArm.getPIDController().calculate(
              tikiTorchArm.encoderToDegrees(), 
                startPosition + totalDistanceChanged
            )
        );
    }

  @Override
  public boolean isFinished(){
    return false;
  }

  @Override
  public void end(boolean interrupted){
    
  }
}
