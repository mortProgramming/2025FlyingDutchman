package org.mort11.commands.autons.odometry.red;

import org.mort11.commands.actions.drivetrain.ResetPosition;
import org.mort11.commands.actions.drivetrain.auto.DriveToPosition;
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

public class RightTwoRed extends SequentialCommandGroup {
    
    public RightTwoRed() {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
        new SequentialCommandGroup(
            new ResetPosition(7.085, 0.59, 270, true),
//7.035 0.565

            //piece one
            new ParallelCommandGroup(
                new DriveToPosition(5.391, 3.05, -60).withTimeout(3),
                new SequentialCommandGroup(
                    new WaitCommand(0.5),
                    SetEndeffector.l4().withTimeout(2)
                )
            ),
            VelocityTikiTorchRoller.outtake().withTimeout(0.5),


            //intake
            new ParallelCommandGroup(
                new TimedDrive(0.5, 0, -1.5, 0, true)
            ),
            new ParallelCommandGroup(
                new DriveToPosition(1.55, 0.67, 60, 1.5, 100),
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
                new DriveToPosition(3.92, 2.735, -120).withTimeout(3.65),
                //3.8625, 2.71
                VelocityTikiTorchRoller.intake().withTimeout(1),
                new SequentialCommandGroup(
                    new WaitCommand(1),
                    SetEndeffector.l4().withTimeout(2)
                )
            ),
            new WaitCommand(0.25),
            VelocityTikiTorchRoller.outtake().withTimeout(0.5),
            new ParallelCommandGroup(
                new TimedDrive(1, 1.5, 0, 0)
            )
        )
    );
  }
}
