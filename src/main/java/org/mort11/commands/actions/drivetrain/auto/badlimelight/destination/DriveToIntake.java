package org.mort11.commands.actions.drivetrain.auto.badlimelight.destination;

import org.mort11.commands.actions.drivetrain.auto.TimedDrive;
import org.mort11.commands.actions.drivetrain.auto.badlimelight.near.DriveFastNearToReef;
import org.mort11.commands.actions.drivetrain.auto.badlimelight.near.DriveNearToIntake;
import org.mort11.commands.actions.drivetrain.teleop.SnapToReef;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class DriveToIntake extends SequentialCommandGroup {
    
    public DriveToIntake() {
            addCommands(
                new SequentialCommandGroup(
                    new DriveNearToIntake(),
                    new TimedDrive(1.5, -2, 0, 0)
                )
            );
    }
}
