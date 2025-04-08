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
import edu.wpi.first.wpilibj2.command.ProxyCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class CenterOneScoreAlgae extends SequentialCommandGroup {
    
    public CenterOneScoreAlgae(boolean isRight) {

    addCommands(
        new SequentialCommandGroup(
            new ResetPosition(7.122, 4.10, 180,  true),

            //coral one
            new ParallelCommandGroup(
                new DriveToReef(isRight),
                SetEndeffector.l4().withTimeout(2)
            ),
            new TimedDrive(1, -0.5, 0, 0),
            new WaitCommand(0.25),
            VelocityTikiTorchRoller.outtake().withTimeout(0.25),
            new ParallelCommandGroup(
                new TimedDrive(0.75, 1.5, 0, 0),
                VelocityTikiTorchRoller.outtake().withTimeout(0.5)
            ),


            //intake algae
            new ParallelCommandGroup(
                new DriveToCenterReef(),
                SetEndeffector.lowAutoAlgae().withTimeout(2),
                VelocityAlgaeRoller.intake().withTimeout(2)
            ),
            new ParallelCommandGroup(
                new TimedDrive(0.5, -1, 0, 0),
                VelocityAlgaeRoller.intake().withTimeout(0.5)
            ),
            // VelocityAlgaeRoller.intake().withTimeout(1.75),


            //outtake algae
            new ParallelCommandGroup(
                new TimedDrive(1.5, 0.3, 0, 0),
                VelocityAlgaeRoller.intake().withTimeout(1.5)
            ),
            new ParallelCommandGroup(
                // new SequentialCommandGroup(
                //     new TimedDrive(1.75, 1, 1, -100, true),
                //     // SetEndeffector.slowBarge().withTimeout(2),
                //     SetEndeffector.barge().withTimeout(2),
                //     new TimedDrive(1, -0.8, 0, 0)
                // )
                new SequentialCommandGroup(
                    new TimedDrive(2.19, 0.8, 0.8, -80, true),
                    new TimedDrive(1, -0.8, 0, 0)
                ),
                new SequentialCommandGroup(
                    SetEndeffector.slowBarge().withTimeout(2.5)
                )
            ),
            VelocityAlgaeRoller.outtake().withTimeout(1),
            new ParallelCommandGroup(
                new TimedDrive(1, 1.55, 0, 0),
                SetEndeffector.rest()
            )
        )
    );
  }
}
