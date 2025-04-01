package org.mort11.commands.actions.endeff;

import org.mort11.commands.actions.drivetrain.ResetPosition;
import org.mort11.commands.actions.endeff.pid.Elevate;
import org.mort11.commands.actions.endeff.pid.SetAlgaeArm;
import org.mort11.commands.actions.endeff.pid.SetEndeffector;
import org.mort11.commands.actions.endeff.pid.SetTikiTorchArm;
import org.mort11.commands.actions.endeff.velocity.Climb;
import org.mort11.commands.actions.endeff.velocity.VelocityAlgaeArm;
import org.mort11.commands.actions.endeff.velocity.VelocityElevator;
import org.mort11.commands.actions.endeff.velocity.VelocityTikiTorchArm;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class Initiate extends SequentialCommandGroup {
    public Initiate() {
        addCommands(
            new SequentialCommandGroup(
                new Climb(true),
                new VelocityElevator(-0.2).withTimeout(2.5),
                new ParallelCommandGroup(
                    Elevate.l2().withTimeout(0.5),
                    SetAlgaeArm.l23Intake(),
                    SetTikiTorchArm.score()
                ),
                SetEndeffector.rest()
            ).withTimeout(1.5)
        );
    }

    public Initiate(double x, double y, double rotationDegrees) {
        addCommands(
            new SequentialCommandGroup(
                new ResetPosition(x, y, rotationDegrees, true),
                new Climb(true),
                new VelocityElevator(-0.2).withTimeout(0.5),
                new ParallelCommandGroup(
                    Elevate.l2().withTimeout(0.5),
                    SetAlgaeArm.l23Intake(),
                    SetTikiTorchArm.score()
                ),
                SetEndeffector.rest()
            ).withTimeout(1.5)
        );
    }
}

// public class Initiate extends SequentialCommandGroup {
//     public Initiate() {
//         addCommands(
//             new SequentialCommandGroup(
//                 new Climb(true),
//                 new VelocityElevator(-0.2).withTimeout(2),
//                 new ParallelCommandGroup(
//                     new VelocityAlgaeArm(0.2).withTimeout(0.5),
//                     new VelocityTikiTorchArm(-0.2).withTimeout(0.5)
//                 ),
//                 SetEndeffector.rest()
//             ).withTimeout(2.5)
//         );
//     }

//     public Initiate(double x, double y, double rotationDegrees) {
//         addCommands(
//             new SequentialCommandGroup(
//                 new ResetPosition(x, y, rotationDegrees, true),
//                 new Climb(true),
//                 new VelocityElevator(-0.2).withTimeout(2),
//                 new ParallelCommandGroup(
//                     new VelocityAlgaeArm(0.2).withTimeout(0.5),
//                     new VelocityTikiTorchArm(-0.2).withTimeout(0.5)
//                 ),
//                 SetEndeffector.rest()
//             ).withTimeout(2.5)
//         );
//     }
// }