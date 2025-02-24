package org.mort11.commands.actions;

import org.mort11.commands.actions.endeffector.pid.Elevate;
import org.mort11.commands.actions.endeffector.pid.SetAlgaeArm;
import org.mort11.commands.actions.endeffector.pid.SetTikiTorchArm;
import org.mort11.commands.actions.endeffector.velocity.Climb;
import org.mort11.commands.actions.endeffector.velocity.VelocityElevator;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class InitializeTeleop extends SequentialCommandGroup {
    public InitializeTeleop() {
        addCommands(
            new SequentialCommandGroup(
                new Climb(true)
            )
        );
    }
}
