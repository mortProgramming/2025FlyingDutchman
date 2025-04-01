package org.mort11.commands.actions;

import org.mort11.commands.actions.endeff.pid.Elevate;
import org.mort11.commands.actions.endeff.pid.SetAlgaeArm;
import org.mort11.commands.actions.endeff.pid.SetTikiTorchArm;
import org.mort11.commands.actions.endeff.velocity.Climb;
import org.mort11.commands.actions.endeff.velocity.VelocityElevator;
import org.mort11.commands.actions.endeff.velocity.VelocityTikiTorchArm;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class InitializeTeleop extends SequentialCommandGroup {
    public InitializeTeleop() {
        addCommands(
            new SequentialCommandGroup(
                new Climb(true).withTimeout(0.1),
                new VelocityTikiTorchArm(0).withTimeout(0.1)
            )
        );
    }
}
