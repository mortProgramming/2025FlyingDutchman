package org.mort11.commands.actions.drivetrain.auto;

import org.mort11.commands.actions.drivetrain.teleop.SnapToReef;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class DriveToReef extends SequentialCommandGroup {
    
    public DriveToReef(boolean isRight) {
        if(isRight) {
            addCommands(
                new SequentialCommandGroup(
                    new DriveNearToReef(true),
                    // new SnapToReef(() -> 0, () -> 0).withTimeout(0.5),
                    new TimedDrive(0.5, -1, 0, 0)
                )
            );
        }

        else {
            addCommands(
                new SequentialCommandGroup(
                    new DriveNearToReef(false),
                    // new SnapToReef(() -> 0, () -> 0).withTimeout(0.5),
                    new TimedDrive(0.5, -1, 0, 0)
                )
            );
        }
    }
}
