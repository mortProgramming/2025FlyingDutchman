package org.mort11.commands.autons.odometry.center;

import org.mort11.commands.actions.drivetrain.ResetPosition;
import org.mort11.commands.actions.drivetrain.auto.DriveToPosition;
import org.mort11.commands.actions.drivetrain.auto.Rotate;
import org.mort11.commands.actions.drivetrain.auto.TimedDrive;
import org.mort11.commands.actions.endeff.Initiate;
import org.mort11.commands.actions.endeff.pid.Elevate;
import org.mort11.commands.actions.endeff.pid.SetEndeffector;
import org.mort11.commands.actions.endeff.pid.SetTikiTorchArm;
import org.mort11.commands.actions.endeff.velocity.VelocityTikiTorchArm;
import org.mort11.commands.actions.endeff.velocity.VelocityTikiTorchRoller;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class OldCenterOnePiece extends SequentialCommandGroup {
    
    public OldCenterOnePiece() {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
        new SequentialCommandGroup(
            new ResetPosition(0, 0, 270, true),
//7.035 0.565
            //piece one
            new ParallelCommandGroup(
                new DriveToPosition(1.846, 0, -60).withTimeout(3),
                new SequentialCommandGroup(
                    new WaitCommand(0.5),
                    SetEndeffector.l4().withTimeout(2)
                )
            ),
            VelocityTikiTorchRoller.outtake().withTimeout(0.5),
            new DriveToPosition(1.5, 0, -60).withTimeout(3),
            SetEndeffector.rest().withTimeout(2)
        )
    );
  }
}
