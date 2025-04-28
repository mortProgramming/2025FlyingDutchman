package org.mort11.commands.autons.odometry.blue;

import org.mort11.commands.actions.drivetrain.ResetPosition;
import org.mort11.commands.actions.drivetrain.auto.DriveToPosition;
import org.mort11.commands.actions.drivetrain.auto.Rotate;
import org.mort11.commands.actions.drivetrain.auto.TimedDrive;
import org.mort11.commands.actions.drivetrain.auto.badlimelight.destination.DriveFastToReef;
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

public class RightTwoHalfBlue extends SequentialCommandGroup {
    
    public RightTwoHalfBlue() {
        addCommands(
            new SequentialCommandGroup(
                new ResetPosition(7.122, 2.169, 270, true),
    
                //piece one
                new ParallelCommandGroup(
                    new SequentialCommandGroup(
                        // new DriveToPosition(5.3, 2.3, -60, 2).withTimeout(1.5),
                        new DriveToPosition(5.3, 2.600, -60, 3, 100, 6).withTimeout(1.5),
                        // new DriveToReef(false)
                        new DriveFastToReef(false)
                    ),
                    new SequentialCommandGroup(
                        // Elevate.zero().withTimeout(0.7),
                        // new VelocityElevator(0.4).withTimeout(0.6),
                        new AllTheWayDown(),
                        Elevate.rest().withTimeout(0.5),
                        SetEndeffector.autoL4().withTimeout(2.3)
                    )
                ),
                VelocityTikiTorchRoller.outtake().withTimeout(0.5),
    
                //intake one
                new ParallelCommandGroup(
                    new TimedDrive(0.3, 0, -1.5, -90, true),
                    new SequentialCommandGroup(
                        new WaitCommand(0.25)
                    )
                ),
                new ParallelCommandGroup(
                    // new DriveToPosition(0.6, 1.211, 60, 3, 100),
                    new DriveToPosition(0.2, 0.9, 60, 3, 100),
                    // SetEndeffector.fastIntake(),
                    SetEndeffector.intake(),
                    VelocityTikiTorchRoller.intake()
                ).withTimeout(3),
                VelocityTikiTorchRoller.intake().withTimeout(0.5),
    
                //twoed piece
                new ParallelCommandGroup(
                    new TimedDrive(0.1, 2, 0, 0),
                    VelocityTikiTorchRoller.intake().withTimeout(0.3)
                ),
                new ParallelCommandGroup(
                    new SequentialCommandGroup(
                        new DriveToPosition(3.25, 2.211, -120, 3, 125, 6).withTimeout(2),
                        // new DriveToReef(true)
                        new DriveFastToReef(true)
                    ),
                    new SequentialCommandGroup(
                        SetTikiTorchArm.algaeClear().withTimeout(1),
                        SetEndeffector.l4().withTimeout(2.3)
                    ),
                    VelocityTikiTorchRoller.intake().withTimeout(1)
                ),
                VelocityTikiTorchRoller.outtake().withTimeout(0.5),
    
                //intake take two
                new ParallelCommandGroup(
                    new TimedDrive(0.2, 0, -1.75, 0, true)
                ),
                new ParallelCommandGroup(
                    // new DriveToPosition(0.6, 7, -60, 4, 150, 9),
                    new DriveToPosition(0.2, 2.211, 60, 4, 150, 9),
                    SetEndeffector.intake(),
                    VelocityTikiTorchRoller.intake()
                ).withTimeout(3),
                VelocityTikiTorchRoller.intake().withTimeout(0.75)
            )
        );
  }
}
