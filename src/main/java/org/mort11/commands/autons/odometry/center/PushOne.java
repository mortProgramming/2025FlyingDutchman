package org.mort11.commands.autons.odometry.center;

import org.mort11.commands.actions.drivetrain.ResetPosition;
import org.mort11.commands.actions.drivetrain.auto.DriveToPosition;
import org.mort11.commands.actions.drivetrain.auto.Rotate;
import org.mort11.commands.actions.drivetrain.auto.TimedDrive;
import org.mort11.commands.actions.drivetrain.auto.badlimelight.DriveToReef;
import org.mort11.commands.actions.endeffector.pid.SetEndeffector;
import org.mort11.commands.actions.endeffector.pid.SetTikiTorchArm;
import org.mort11.commands.actions.endeffector.velocity.VelocityTikiTorchArm;
import org.mort11.commands.actions.endeffector.velocity.VelocityTikiTorchRoller;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class PushOne extends SequentialCommandGroup {
    
    public PushOne(boolean isRight) {

    addCommands(
        new SequentialCommandGroup(
            new ResetPosition(0, 0, 225, true),
            //push
            new TimedDrive(2, 0, 1.5, 0),
            new TimedDrive(1, 0, -1.5, 0),
            // new Rotate(90),
            new DriveToReef(isRight),
            new TimedDrive(0.5, -0.5, 0, 0),
            new WaitCommand(0.5),
            SetEndeffector.l4().withTimeout(2),
            VelocityTikiTorchRoller.outtake().withTimeout(0.5),
            new TimedDrive(1, 0.5, 0, 0),
            SetEndeffector.rest().withTimeout(2)
        )
    );
  }
}
