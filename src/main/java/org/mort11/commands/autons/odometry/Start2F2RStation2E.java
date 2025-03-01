package org.mort11.commands.autons.odometry;

import org.mort11.commands.actions.drivetrain.ResetPosition;
import org.mort11.commands.actions.drivetrain.auto.DriveToPosition;
import org.mort11.commands.actions.drivetrain.auto.Rotate;
import org.mort11.commands.actions.drivetrain.auto.TimedDrive;
import org.mort11.commands.actions.endeffector.Initiate;
import org.mort11.commands.actions.endeffector.pid.SetEndeffector;
import org.mort11.commands.actions.endeffector.velocity.VelocityTikiTorchRoller;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class Start2F2RStation2E extends SequentialCommandGroup {
    
    public Start2F2RStation2E() {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
        new SequentialCommandGroup(
            new Initiate(7.01, 0.54, 270),
            new DriveToPosition(6, 2),
            new Rotate(-60).withTimeout(1),
            new ParallelCommandGroup(
                new DriveToPosition(5.35, 2.99),
                SetEndeffector.l4().withTimeout(2.5)
            ),
            new TimedDrive(0.3, -1.25, 0, 0),
            VelocityTikiTorchRoller.outtake().withTimeout(0.5),
            new TimedDrive(1, 0, -1, 0, true),
            SetEndeffector.intake().withTimeout(2.5),
            new Rotate(60).withTimeout(1),
            new DriveToPosition(1.39, 1.05),
            VelocityTikiTorchRoller.intake().withTimeout(0.75),
            new TimedDrive(0.3, 1.25, 0, 0),
            new Rotate(-120).withTimeout(1),
            new DriveToPosition(2.5, 2),
            new ParallelCommandGroup(
                new DriveToPosition(3.98, 2.73),
                SetEndeffector.l4().withTimeout(2.5)
            )
        )
    );
  }
}
