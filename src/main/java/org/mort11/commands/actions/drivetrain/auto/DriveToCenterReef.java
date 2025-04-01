package org.mort11.commands.actions.drivetrain.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class DriveToCenterReef extends SequentialCommandGroup {
    
    public DriveToCenterReef() {
        addCommands(
            new SequentialCommandGroup(
                new DriveNearToCenterReef(),
                new TimedDrive(0.75, -0.5, 0, 0)
            )
        );
    }
}
