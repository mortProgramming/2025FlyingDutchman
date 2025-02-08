package org.mort11.commands.actions.endeffector;

import edu.wpi.first.wpilibj2.command.Command;
import org.mort11.subsystems.AlgaeArm;

public class MoveAlgaeArm extends Command  {
    
    private AlgaeArm algaeArm;

    private double incrementInPerSecond;

    public MoveAlgaeArm(double incrementInPerSecond) {
        this.incrementInPerSecond = incrementInPerSecond;

        algaeArm = AlgaeArm.getInstance();

        addRequirements(algaeArm);
    }

    @Override
    public void initialize(){}

    @Override
    public void execute() {
        double increment = incrementInPerSecond / 50;
        algaeArm.setPosition(algaeArm.encoderToDegrees() + increment);
    }

  @Override
  public boolean isFinished(){
    return false;
  }

  @Override
  public void end(boolean interrupted){}
}
