package org.mort11.commands.actions.lights;

import org.mort11.config.constants.PhysicalConstants;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;

import org.mort11.subsystems.swerve.Drivetrain;
import org.mort11.subsystems.Lights;
import org.mort11.subsystems.Elevator;

public class TeleopLights extends Command {
  /** Creates a new IntakeBeamBreak. */
  private Lights lights;

  public TeleopLights() {
    lights = Lights.getInstance();

    addRequirements(lights);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    double height = Elevator.getInstance().getElevatorPositionInches();

    if(height > 60) {
        lights.setLightsYellow();
    }

    else if(height > 35) {
        lights.setLightsDarkBlue();
    }

    else if(height > 10) {
        lights.setLightsWhite();
    }

    else {
        lights.setLightsRed();
    }

    // lights.setLightsGold();
  }

  @Override
  public void end(boolean interrupted) {
    lights.setLightsBlue();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
