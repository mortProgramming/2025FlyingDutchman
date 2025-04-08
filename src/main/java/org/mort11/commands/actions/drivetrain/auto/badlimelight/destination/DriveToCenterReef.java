package org.mort11.commands.actions.drivetrain.auto.badlimelight.destination;

import org.mort11.commands.actions.drivetrain.auto.TimedDrive;
import org.mort11.commands.actions.drivetrain.auto.badlimelight.near.DriveNearToCenterReef;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class DriveToCenterReef extends SequentialCommandGroup {
    
    public DriveToCenterReef() {
        addCommands(
            new SequentialCommandGroup(
                new DriveNearToCenterReef(),
                new TimedDrive(1, -0.5, 0, 0)
            )
        );
    }
}
