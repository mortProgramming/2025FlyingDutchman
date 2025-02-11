package org.mort11.commands.actions.endeffector;

import org.mort11.subsystems.TikiTorchArm;

import edu.wpi.first.wpilibj2.command.Command;

public class MoveTikiTorchArm extends Command  {
    
    private TikiTorchArm tikiTorchArm;
    private double testingSpeed;

    private double incrementDegPerSecond, totalDistanceChanged, startPosition;

    // public MoveTikiTorchArm(double incrementDegPerSecond) {
    //     this.incrementDegPerSecond = incrementDegPerSecond;

    //     tikiTorchArm = TikiTorchArm.getInstance();

    //     totalDistanceChanged = 0;

    //     addRequirements(tikiTorchArm);
    // }

    public MoveTikiTorchArm(double testingSpeed){
      this.testingSpeed = testingSpeed;
      tikiTorchArm = tikiTorchArm.getInstance();

      addRequirements(tikiTorchArm);
    }

    @Override
    public void initialize(){
      startPosition = tikiTorchArm.encoderToDegrees();
    }

    @Override
    public void execute() {
      // totalDistanceChanged += incrementDegPerSecond / 50;
      //   tikiTorchArm.setPosition(startPosition + totalDistanceChanged);
      tikiTorchArm.getArmMotor().setPercent(testingSpeed);
    }

  @Override
  public boolean isFinished(){
    return false;
  }

  @Override
  public void end(boolean interrupted){
    
  }
}
