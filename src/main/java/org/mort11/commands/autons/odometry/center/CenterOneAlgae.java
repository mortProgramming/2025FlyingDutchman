package org.mort11.commands.autons.odometry.center;

import org.mort11.commands.actions.drivetrain.ResetPosition;
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

public class CenterOneAlgae extends SequentialCommandGroup {
    
    public CenterOneAlgae(boolean isRight) {

    addCommands(
        new SequentialCommandGroup(
            new ResetPosition(0, 0, 180, true),
            //piece one
            new TimedDrive(2, -0.5, 0, 0),
            new DriveToReef(isRight),
            new TimedDrive(0.5, -0.5, 0, 0),
            new WaitCommand(0.5),
            SetEndeffector.l4().withTimeout(2),
            VelocityTikiTorchRoller.outtake().withTimeout(0.5),
            new TimedDrive(1, 0.5, 0, 0),
            SetEndeffector.lowAutoAlgae().withTimeout(2),
            new ParallelCommandGroup(
                new DriveToCenterReef(),
                VelocityAlgaeRoller.intake().withTimeout(2.25)
            ),
            VelocityAlgaeRoller.intake().withTimeout(0.5),
            new ParallelCommandGroup(
                // new TimedDrive(1, 0.5, 0, 0)
                // VelocityAlgaeRoller.intake().withTimeout(0.5)
            )
        )
    );
  }
}
