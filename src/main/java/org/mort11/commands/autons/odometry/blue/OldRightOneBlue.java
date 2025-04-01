package org.mort11.commands.autons.odometry.blue;

import org.mort11.commands.actions.drivetrain.ResetPosition;
import org.mort11.commands.actions.drivetrain.auto.DriveToPosition;
import org.mort11.commands.actions.drivetrain.auto.Rotate;
import org.mort11.commands.actions.drivetrain.auto.TimedDrive;
import org.mort11.commands.actions.endeff.Initiate;
import org.mort11.commands.actions.endeff.pid.SetEndeffector;
import org.mort11.commands.actions.endeff.velocity.VelocityTikiTorchRoller;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class OldRightOneBlue extends SequentialCommandGroup {
    
    public OldRightOneBlue() {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
        new SequentialCommandGroup(
            new Initiate(7.01, 0.54, 270),
            new DriveToPosition(6, 2, 270),
            new Rotate(-60).withTimeout(1),
            new ParallelCommandGroup(
                new DriveToPosition(5.17, 3.08, -60).withTimeout(5),
                SetEndeffector.l4().withTimeout(2.5)
            ),
            VelocityTikiTorchRoller.outtake().withTimeout(0.5),
            new TimedDrive(1, 0, -1, 0, true),
            SetEndeffector.intake().withTimeout(2.5),
            new Rotate(60).withTimeout(1)
        )
    );
  }
}
