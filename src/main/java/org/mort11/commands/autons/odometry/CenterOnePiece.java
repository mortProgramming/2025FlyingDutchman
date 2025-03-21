package org.mort11.commands.autons.odometry;

import org.mort11.commands.actions.drivetrain.ResetPosition;
import org.mort11.commands.actions.drivetrain.auto.DriveToPosition;
import org.mort11.commands.actions.drivetrain.auto.DriveToReef;
import org.mort11.commands.actions.drivetrain.auto.TimedDrive;
import org.mort11.commands.actions.endeffector.pid.SetEndeffector;
import org.mort11.commands.actions.endeffector.pid.SetTikiTorchArm;
import org.mort11.commands.actions.endeffector.velocity.VelocityTikiTorchArm;
import org.mort11.commands.actions.endeffector.velocity.VelocityTikiTorchRoller;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class CenterOnePiece extends SequentialCommandGroup {
    
    public CenterOnePiece(boolean isRight) {

    addCommands(
        new SequentialCommandGroup(
            new ResetPosition(0, 0, 180, true),
            //piece one
            new DriveToPosition(0, 1.5, 180).withTimeout(3),
            new DriveToReef(isRight),
            new WaitCommand(0.5),
            SetEndeffector.mediumL4().withTimeout(2),
            VelocityTikiTorchRoller.outtake().withTimeout(0.5),
            new TimedDrive(1, -0.5, 0, 0),
            SetEndeffector.rest().withTimeout(2)
        )
    );
  }
}
