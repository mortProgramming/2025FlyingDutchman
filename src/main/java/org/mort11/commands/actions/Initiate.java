package org.mort11.commands.actions;

import org.mort11.commands.actions.endeffector.pid.Elevate;
import org.mort11.commands.actions.endeffector.pid.SetAlgaeArm;
import org.mort11.commands.actions.endeffector.pid.SetTikiTorchArm;
import org.mort11.commands.actions.endeffector.velocity.Climb;
import org.mort11.commands.actions.endeffector.velocity.VelocityElevator;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class Initiate extends SequentialCommandGroup {
    public Initiate() {
        addCommands(
            new SequentialCommandGroup(
                new Climb(true)
                // new VelocityElevator(0.2).withTimeout(0.5),
                // new ParallelCommandGroup(
                //     Elevate.l2(),
                //     SetAlgaeArm.l23Intake(),
                //     SetTikiTorchArm.score()
                // )
            )
        );
    }
}
