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

public class RightThreeBlue extends SequentialCommandGroup {
    
    public RightThreeBlue() {
    addCommands(
        new SequentialCommandGroup(
            new ResetPosition(7.122, 0.554, 270, true),

            //piece one
            new ParallelCommandGroup(
                new DriveToPosition(5.274, 2.297, -60, 2).withTimeout(1.5),
                new DriveToReef(false),
                new SequentialCommandGroup(
                    new WaitCommand(0.5),
                    SetEndeffector.l4().withTimeout(2)
                )
            ),
            VelocityTikiTorchRoller.outtake().withTimeout(0.5),


            //intake one
            new ParallelCommandGroup(
                new TimedDrive(0.5, 0, -1.5, 0, true)
            ),
            new ParallelCommandGroup(
                new DriveToPosition(1.668, 0.704, 60, 2),
                VelocityTikiTorchRoller.intake(),
                SetEndeffector.autoIntake()
            ).withTimeout(5),
            VelocityTikiTorchRoller.intake().withTimeout(0.75),

            //twoed piece
            new ParallelCommandGroup(
                new TimedDrive(0.3, 1.25, 0, 0),
                VelocityTikiTorchRoller.intake().withTimeout(0.3)
            ),
            new ParallelCommandGroup(
                new DriveToPosition(3.606, 2.462, -120).withTimeout(3.65),
                VelocityTikiTorchRoller.intake().withTimeout(1),
                new SequentialCommandGroup(
                    SetEndeffector.l4().withTimeout(2)
                ),
                new DriveToReef(true)
            ),
            VelocityTikiTorchRoller.outtake().withTimeout(0.5),

            //intake take two
            new ParallelCommandGroup(
                new TimedDrive(0.5, 0, -1.5, 0)
            ),
            new ParallelCommandGroup(
                new DriveToPosition(1.668, 0.704, 60, 2),
                VelocityTikiTorchRoller.intake(),
                SetEndeffector.autoIntake()
            ).withTimeout(5),
            VelocityTikiTorchRoller.intake().withTimeout(0.75),

            //threed piece
            new ParallelCommandGroup(
                new TimedDrive(0.3, 1.25, 0, 0),
                VelocityTikiTorchRoller.intake().withTimeout(0.3)
            ),
            new ParallelCommandGroup(
                new DriveToPosition(3.606, 2.462, -120).withTimeout(3.65),
                VelocityTikiTorchRoller.intake().withTimeout(1),
                new SequentialCommandGroup(
                    SetEndeffector.l4().withTimeout(2)
                )
            ),
            new DriveToReef(false),
            VelocityTikiTorchRoller.outtake().withTimeout(0.5),

            new TimedDrive(0.5, 0, -1.5, 0)
        )
    );
  }
}
