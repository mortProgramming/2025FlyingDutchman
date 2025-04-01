package org.mort11.commands.autons.odometry.blue;

import org.mort11.commands.actions.drivetrain.ResetPosition;
import org.mort11.commands.actions.drivetrain.auto.DriveFastToReef;
import org.mort11.commands.actions.drivetrain.auto.DriveToPosition;
import org.mort11.commands.actions.drivetrain.auto.DriveToReef;
import org.mort11.commands.actions.drivetrain.auto.Rotate;
import org.mort11.commands.actions.drivetrain.auto.TimedDrive;
import org.mort11.commands.actions.endeffector.Initiate;
import org.mort11.commands.actions.endeffector.pid.Elevate;
import org.mort11.commands.actions.endeffector.pid.SetEndeffector;
import org.mort11.commands.actions.endeffector.pid.SetTikiTorchArm;
import org.mort11.commands.actions.endeffector.velocity.AllTheWayDown;
import org.mort11.commands.actions.endeffector.velocity.VelocityTikiTorchArm;
import org.mort11.commands.actions.endeffector.velocity.VelocityTikiTorchRoller;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class LeftTwoBlue extends SequentialCommandGroup {
    
    public LeftTwoBlue() {
    addCommands(
        new SequentialCommandGroup(
            new ResetPosition(7.122, 7.657, 90, true),

            //piece one
            new ParallelCommandGroup(
                new SequentialCommandGroup(
                    new DriveToPosition(5.125, 5.6, 60, 2).withTimeout(2),
                    new DriveFastToReef(true)
                ),
                new SequentialCommandGroup(
                    new AllTheWayDown(),
                    VelocityTikiTorchRoller.rest().withTimeout(0.1),
                    Elevate.rest().withTimeout(0.5),
                    new WaitCommand(0.5),
                    // SetEndeffector.mediumL4().withTimeout(2)
                    SetEndeffector.l4().withTimeout(2)
                    // VelocityTikiTorchRoller.outtake().withTimeout(0.5)
                    // SetTikiTorchArm.score().withTimeout(0.5)
                )
            ),
            new ParallelCommandGroup(
                // SetTikiTorchArm.score().withTimeout(0.5),
                new WaitCommand(0.25),
                VelocityTikiTorchRoller.outtake().withTimeout(0.25)
            ),

            //intake one
            new ParallelCommandGroup(
                new TimedDrive(0.5, 0, 1.5, 0, true),
                VelocityTikiTorchRoller.outtake().withTimeout(0.5),
                new SequentialCommandGroup(
                    new WaitCommand(0.25),
                    // Elevate.intake().withTimeout(0.5)
                    SetEndeffector.autoIntake().withTimeout(1)
                )
            ),
            new ParallelCommandGroup(
                new DriveToPosition(0.65, 7.261, -60, 2, 50),
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
                    new DriveToPosition(5.8, 5.6, 120).withTimeout(2.5),
                    new DriveToReef(false)
                ),
                new SequentialCommandGroup(
                    SetTikiTorchArm.algaeClear().withTimeout(1),
                    new WaitCommand(0.5),
                    SetEndeffector.mediumL4().withTimeout(2.5)
                    // SetEndeffector.slowL4().withTimeout(2.5)
                )
                // VelocityTikiTorchRoller.intake().withTimeout(1)
            ),
            VelocityTikiTorchRoller.outtake().withTimeout(0.25),

            new ParallelCommandGroup(
                new TimedDrive(0.5, 1, 0, 0),
                VelocityTikiTorchRoller.outtake().withTimeout(0.25)
            )
        )
    );
  }
}
