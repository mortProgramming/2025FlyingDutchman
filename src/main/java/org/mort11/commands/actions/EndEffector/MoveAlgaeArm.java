package org.mort11.commands.actions.endeffector;

import org.mort11.subsystems.AlgaeArm;

import edu.wpi.first.wpilibj2.command.Command;

public class MoveAlgaeArm extends Command  {
    
    private AlgaeArm algaeArm;
    private double testingSpeed;

    private double incrementDegPerSecond, totalDistanceChanged, startPosition;

    // public MoveAlgaeArm(double incrementDegPerSecond) {
    //     this.incrementDegPerSecond = incrementDegPerSecond;

    //     algaeArm = AlgaeArm.getInstance();

    //     totalDistanceChanged = 0;

    //     addRequirements(algaeArm);
    // }

    public MoveAlgaeArm(double testingSpeed){
        this.testingSpeed = testingSpeed;
        algaeArm = algaeArm.getInstance();

        addRequirements(algaeArm);
    }

    @Override
    public void initialize(){
      startPosition = algaeArm.encoderToDegrees();
    }

    @Override
    public void execute() {
      // totalDistanceChanged += incrementDegPerSecond / 200;
      // algaeArm.setPosition(startPosition + totalDistanceChanged);
      algaeArm.getArmMotor().setPercent(testingSpeed);
    }

  @Override
  public boolean isFinished(){
    return false;
  }

  @Override
  public void end(boolean interrupted){}
}
