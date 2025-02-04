package org.mort11.commands.actions;

import org.mort11.subsystems.Climber;
import org.mort11.config.constants.PortConstants;
import org.mort11.config.constants.PhysicalConstants;

import edu.wpi.first.wpilibj2.command.Command;


public class Climb extends Command{
    
    Climber climber;

    private boolean climberUp;

    public Climb(boolean climberUp){
        this.climberUp = climberUp;

        climber = Climber.getInstance();

        addRequirements(climber);

    }

    @Override
    public void initialize() {
        climber.setClimberPosition(climberUp);
    }

    public boolean isFinished() {
        return true;
    }

    public void end(boolean interrupted) {

    }
}


    




    

