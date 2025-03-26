package org.mort11.commands.actions.lights;

import org.mort11.config.constants.PhysicalConstants;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;

import org.mort11.subsystems.swerve.Drivetrain;
import org.mort11.subsystems.Lights;
import org.mort11.subsystems.Elevator;

public class DisabledLights extends Command {
  /** Creates a new IntakeBeamBreak. */
  private Lights lights;

  public DisabledLights() {
    lights = Lights.getInstance();

    addRequirements(lights);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {

    lights.setLightsWhite();
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
