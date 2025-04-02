package org.mort11.commands.autons.odometry.red;

import org.mort11.commands.actions.drivetrain.ResetPosition;
import org.mort11.commands.actions.drivetrain.auto.DriveToPosition;
import org.mort11.commands.actions.drivetrain.auto.Rotate;
import org.mort11.commands.actions.drivetrain.auto.TimedDrive;
import org.mort11.commands.actions.drivetrain.auto.badlimelight.destination.DriveToReef;
import org.mort11.commands.actions.endeff.Initiate;
import org.mort11.commands.actions.endeff.pid.Elevate;
import org.mort11.commands.actions.endeff.pid.SetEndeffector;
import org.mort11.commands.actions.endeff.pid.SetTikiTorchArm;
import org.mort11.commands.actions.endeff.velocity.AllTheWayDown;
import org.mort11.commands.actions.endeff.velocity.VelocityElevator;
import org.mort11.commands.actions.endeff.velocity.VelocityTikiTorchArm;
import org.mort11.commands.actions.endeff.velocity.VelocityTikiTorchRoller;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class RightTwoRed extends SequentialCommandGroup {
    
    public RightTwoRed() {
    addCommands(
        new SequentialCommandGroup(
            new ResetPosition(7.122, 0.554, 270, true),

            //piece one
            new ParallelCommandGroup(
                new SequentialCommandGroup(
                    // new DriveToPosition(5.3, 2.3, -60, 2).withTimeout(1.5),
                    new DriveToPosition(5.25, 2.711, -60, 2).withTimeout(2),
                    new DriveToReef(false)
                ),
                new SequentialCommandGroup(
                    // new VelocityElevator(0.4).withTimeout(0.6),
                    new AllTheWayDown(),
                    VelocityTikiTorchRoller.rest().withTimeout(0.1),
                    Elevate.rest().withTimeout(0.5),
                    new WaitCommand(0.5),
                    SetEndeffector.l4().withTimeout(2)
                )
            ),
            VelocityTikiTorchRoller.outtake().withTimeout(0.25),

            //intake one
            new ParallelCommandGroup(
                new TimedDrive(0.5, 0, -1.5, 0, true),
                VelocityTikiTorchRoller.outtake().withTimeout(0.5),
                new SequentialCommandGroup(
                    new WaitCommand(0.25),
                    // Elevate.intake().withTimeout(0.5)
                    // SetEndeffector.intake().withTimeout(0.5)
                    SetEndeffector.autoIntake().withTimeout(1)
                )
            ),
            new ParallelCommandGroup(
                new DriveToPosition(0.65, 0.782, 60, 2, 50),
                SetEndeffector.autoIntake(),
                VelocityTikiTorchRoller.intake()
            ).withTimeout(3),
            VelocityTikiTorchRoller.intake().withTimeout(1),

            //twoed piece
            new ParallelCommandGroup(
                new TimedDrive(0.3, 1.25, 0, 0),
                VelocityTikiTorchRoller.intake().withTimeout(0.3)
            ),
            new ParallelCommandGroup(
                new SequentialCommandGroup(
                    new DriveToPosition(4.15, 2.411, -120).withTimeout(2.5),
                    new DriveToReef(true)
                ),
                new SequentialCommandGroup(
                    SetTikiTorchArm.algaeClear().withTimeout(1),
                    new WaitCommand(0.5),
                    SetEndeffector.mediumL4().withTimeout(2.5)
                )
            ),
            VelocityTikiTorchRoller.outtake().withTimeout(0.25),

            //intake take two
            new ParallelCommandGroup(
                new TimedDrive(0.5, 1, 0, 0),
                VelocityTikiTorchRoller.outtake().withTimeout(0.25)
            )
        )
    );
  }
}
