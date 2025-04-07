package org.mort11.commands.autons.odometry.center;

import org.mort11.commands.actions.drivetrain.ResetPosition;
import org.mort11.commands.actions.drivetrain.SetRobotOrientation;
import org.mort11.commands.actions.drivetrain.auto.DriveToPosition;
import org.mort11.commands.actions.drivetrain.auto.badlimelight.destination.DriveToReef;
import org.mort11.commands.actions.endeff.pid.SetEndeffector;
import org.mort11.commands.actions.endeff.pid.SetTikiTorchArm;
import org.mort11.commands.actions.endeff.velocity.VelocityAlgaeRoller;
import org.mort11.commands.actions.endeff.velocity.VelocityTikiTorchArm;
import org.mort11.commands.actions.endeff.velocity.VelocityTikiTorchRoller;
import org.mort11.commands.actions.drivetrain.auto.TimedDrive;
import org.mort11.commands.actions.drivetrain.auto.badlimelight.destination.DriveToCenterReef;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class CenterOneGoodAlgae extends SequentialCommandGroup {
    
    public CenterOneGoodAlgae(boolean isRight) {

    addCommands(
        new SequentialCommandGroup(
            new ResetPosition(7.122, 4.10, 0,  true),
            new SetRobotOrientation(180).withTimeout(0.02),
            //piece one
            new TimedDrive(1, -1, 0, 0),
            new DriveToReef(isRight),
            new TimedDrive(0.25, -0.5, 0, 0),
            new WaitCommand(0.25),
            SetEndeffector.l4().withTimeout(2),
            VelocityTikiTorchRoller.outtake().withTimeout(0.25),
            new ParallelCommandGroup(
                new TimedDrive(0.5, 1, 0, 0),
                VelocityTikiTorchRoller.outtake().withTimeout(0.5)

            ),
            SetEndeffector.lowAutoAlgae().withTimeout(2),
            new ParallelCommandGroup(
                new DriveToCenterReef(),
                VelocityAlgaeRoller.intake().withTimeout(2.25)
            ),
            new ParallelCommandGroup(
                new TimedDrive(0.5, 1, 0, 0),
                VelocityAlgaeRoller.intake().withTimeout(0.75)
            ),
            // new DriveToPosition(7.12, 4.1, 0)
            new ParallelCommandGroup(
                new TimedDrive(1, 1, 1, 180),
                SetEndeffector.l4()
            )
        )
    );
  }
}
