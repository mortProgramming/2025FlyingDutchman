package org.mort11.commands.actions.drivetrain;

import org.mort11.subsystems.swerve.Drivetrain;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class ResetPosition extends SequentialCommandGroup {

    private Drivetrain drivetrain;

    public ResetPosition(double x, double y, double rotationDegrees) {

        drivetrain = Drivetrain.getInstance();

        addCommands(
            new SequentialCommandGroup(
                new InstantCommand(() -> drivetrain.setRobotPosition(x, y, rotationDegrees), drivetrain)
            ).withTimeout(0.02)
        );
    }

    public ResetPosition(double x, double y, double rotationDegrees, boolean orientRobot) {

        drivetrain = Drivetrain.getInstance();

        if(orientRobot) {
            addCommands(
                new SequentialCommandGroup(
                    new InstantCommand(() -> drivetrain.setRobotPosition(x, y, rotationDegrees), drivetrain),
                    new SetRobotOrientation(rotationDegrees)
                ).withTimeout(0.02)
            );
        }

        else {
            addCommands(
                new SequentialCommandGroup(
                    new InstantCommand(() -> drivetrain.setRobotPosition(x, y, rotationDegrees), drivetrain)
                ).withTimeout(0.02)
            );
        }
    }
}
