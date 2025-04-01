package org.mort11.commands.autons.odometry.red;

import org.mort11.commands.actions.drivetrain.ResetPosition;
import org.mort11.commands.actions.drivetrain.auto.DriveToPosition;
import org.mort11.commands.actions.drivetrain.auto.Rotate;
import org.mort11.commands.actions.drivetrain.auto.TimedDrive;
import org.mort11.commands.actions.drivetrain.auto.badlimelight.DriveFastToReef;
import org.mort11.commands.actions.drivetrain.auto.badlimelight.DriveToReef;
import org.mort11.commands.actions.endeffector.Initiate;
import org.mort11.commands.actions.endeffector.pid.Elevate;
import org.mort11.commands.actions.endeffector.pid.SetEndeffector;
import org.mort11.commands.actions.endeffector.pid.SetTikiTorchArm;
import org.mort11.commands.actions.endeffector.velocity.AllTheWayDown;
import org.mort11.commands.actions.endeffector.velocity.VelocityElevator;
import org.mort11.commands.actions.endeffector.velocity.VelocityTikiTorchArm;
import org.mort11.commands.actions.endeffector.velocity.VelocityTikiTorchRoller;

import edu.wpi.first.units.measure.Velocity;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class LeftTwoHalfRed extends SequentialCommandGroup {
    
    public LeftTwoHalfRed() {
        addCommands(
            new SequentialCommandGroup(
                new ResetPosition(7.122, 6.042, 90, true),
    
                //piece one
                new ParallelCommandGroup(
                    new SequentialCommandGroup(
                        // new DriveToPosition(5.3, 2.3, -60, 2).withTimeout(1.5),
                        new DriveToPosition(5.30, 5.70, 60, 3, 100, 6).withTimeout(1.5),
                        // new DriveToReef(true)
                        new DriveFastToReef(true)
                    ),
                    new SequentialCommandGroup(
                        // Elevate.zero().withTimeout(0.7),
                        // new VelocityElevator(0.4).withTimeout(0.6),
                        new AllTheWayDown(),
                        VelocityTikiTorchRoller.rest().withTimeout(0.1),
                        Elevate.rest().withTimeout(0.5),
                        SetEndeffector.autoL4().withTimeout(2.3)
                    )
                ),
                VelocityTikiTorchRoller.outtake().withTimeout(0.5),
    
                //intake one
                new ParallelCommandGroup(
                    new TimedDrive(0.3, 0, 1.5, 90, true),
                    new SequentialCommandGroup(
                        new WaitCommand(0.25)
                    )
                ),
                new ParallelCommandGroup(
                    new DriveToPosition(0.2, 7.35, -60, 3, 100),
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
                        new DriveToPosition(3, 6.5, 120, 3, 125, 6).withTimeout(2),
                        // new DriveToReef(false)
                        new DriveFastToReef(false)
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
                    new TimedDrive(0.2, 0, 1.75, 0, true)
                ),
                new ParallelCommandGroup(
                    // new DriveToPosition(0.6, 7, -60, 4, 150, 9),
                    new DriveToPosition(1, 7, -60, 4, 150, 9),
                    SetEndeffector.intake(),
                    VelocityTikiTorchRoller.intake()
                ).withTimeout(3),
                VelocityTikiTorchRoller.intake().withTimeout(0.75)
            )
        );
  }
}
