package org.mort11.commands.autons.odometry.blue;

import org.mort11.commands.actions.drivetrain.ResetPosition;
import org.mort11.commands.actions.drivetrain.auto.DriveToPosition;
import org.mort11.commands.actions.drivetrain.auto.DriveToReef;
import org.mort11.commands.actions.drivetrain.auto.Rotate;
import org.mort11.commands.actions.drivetrain.auto.TimedDrive;
import org.mort11.commands.actions.endeffector.Initiate;
import org.mort11.commands.actions.endeffector.pid.Elevate;
import org.mort11.commands.actions.endeffector.pid.SetEndeffector;
import org.mort11.commands.actions.endeffector.pid.SetTikiTorchArm;
import org.mort11.commands.actions.endeffector.velocity.VelocityTikiTorchArm;
import org.mort11.commands.actions.endeffector.velocity.VelocityTikiTorchRoller;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class LeftTwoHalfBlue extends SequentialCommandGroup {
    
    public LeftTwoHalfBlue() {
    addCommands(
        new SequentialCommandGroup(
            new ResetPosition(7.122, 7.657, 90, true),

            //piece one
            new ParallelCommandGroup(
                new SequentialCommandGroup(
                    // new DriveToPosition(5.3, 2.3, -60, 2).withTimeout(1.5),
                    new DriveToPosition(5.3, 5.611, 60, 2).withTimeout(2),
                    new DriveToReef(true)
                ),
                new SequentialCommandGroup(
                    Elevate.rest().withTimeout(0.5),
                    SetEndeffector.slowL4().withTimeout(2.5)
                )
            ),
            VelocityTikiTorchRoller.outtake().withTimeout(0.5),

            //intake one
            new ParallelCommandGroup(
                new TimedDrive(0.5, 0, 1.5, 0, true),
                new SequentialCommandGroup(
                    new WaitCommand(0.25),
                    Elevate.intake().withTimeout(0.5)
                )
            ),
            new ParallelCommandGroup(
                new DriveToPosition(1.317, 7.6, -60, 2, 50),
                SetEndeffector.intake(),
                VelocityTikiTorchRoller.intake()
            ).withTimeout(3),
            VelocityTikiTorchRoller.intake().withTimeout(0.75),

            //twoed piece
            new ParallelCommandGroup(
                new TimedDrive(0.3, 1.25, 0, 0),
                VelocityTikiTorchRoller.intake().withTimeout(0.3)
            ),
            new ParallelCommandGroup(
                new SequentialCommandGroup(
                    new DriveToPosition(3.75, 5.749, 120).withTimeout(2.5),
                    new DriveToReef(false)
                ),
                new SequentialCommandGroup(
                    SetTikiTorchArm.algaeClear().withTimeout(1),
                    SetEndeffector.slowL4().withTimeout(2.5)
                ),
                VelocityTikiTorchRoller.intake().withTimeout(1)
            ),
            VelocityTikiTorchRoller.outtake().withTimeout(0.5),

            //intake take two
            new ParallelCommandGroup(
                new TimedDrive(0.5, 1, 0, 0)
            )
        )
    );
  }
}
