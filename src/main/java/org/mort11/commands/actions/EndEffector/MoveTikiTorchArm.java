package org.mort11.commands.actions.endeffector;

import edu.wpi.first.wpilibj2.command.Command;
import org.mort11.subsystems.TikiTorchArm;

public class MoveTikiTorchArm extends Command  {
    
    private TikiTorchArm tikiTorchArm;

    private double incrementInPerSecond;

    public MoveTikiTorchArm(double incrementInPerSecond) {
        this.incrementInPerSecond = incrementInPerSecond;

        tikiTorchArm = TikiTorchArm.getInstance();

        addRequirements(tikiTorchArm);
    }

    @Override
    public void initialize(){}

    @Override
    public void execute() {
        double increment = incrementInPerSecond / 50;
        tikiTorchArm.setPosition(tikiTorchArm.encoderToDegrees() + increment);
    }

  @Override
  public boolean isFinished(){
    return false;
  }

  @Override
  public void end(boolean interrupted){
    
  }
}
