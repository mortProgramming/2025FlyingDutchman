package org.mort11.commands.actions.drivetrain.auto.badlimelight.destination;

import org.mort11.commands.actions.drivetrain.auto.TimedDrive;
import org.mort11.commands.actions.drivetrain.auto.badlimelight.near.DriveNearToReef;
import org.mort11.commands.actions.drivetrain.teleop.SnapToReef;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class DriveToReef extends SequentialCommandGroup {
    
    public DriveToReef(boolean isRight) {
        if(isRight) {
            addCommands(
                new SequentialCommandGroup(
                    new DriveNearToReef(true),
                    new TimedDrive(0.75, -0.5, 0, 0)
                    // new DriveNearToReef(true),
                    // new TimedDrive(0.5, -1, 0, 0)
                )
            );
        }

        else {
            addCommands(
                new SequentialCommandGroup(
                    new DriveNearToReef(false),
                    new TimedDrive(0.75, -0.5, 0, 0)
                    // new DriveNearToReef(false),
                    // new TimedDrive(0.5, -1, 0, 0)
                )
            );
        }
    }
}
