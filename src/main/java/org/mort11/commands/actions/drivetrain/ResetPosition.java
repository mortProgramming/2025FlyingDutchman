package org.mort11.commands.actions.drivetrain;

import org.mort11.subsystems.Drivetrain;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class ResetPosition extends SequentialCommandGroup {

    private Drivetrain drivetrain;

    public ResetPosition(double x, double y, double rotationDegrees) {

        drivetrain = Drivetrain.getInstance();

        addCommands(
            new SequentialCommandGroup(
                drivetrain.setRobotPosition(x, y, rotationDegrees),
                drivetrain.setGyroscopeZero(rotationDegrees)
            )
        );
    }
}
