package org.mort11.commands.autons.timed;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

import org.mort11.commands.actions.drivetrain.auto.TimedDrive;
import org.mort11.commands.actions.endeffector.Initiate;
import org.mort11.commands.actions.endeffector.pid.SetEndeffector;
import org.mort11.commands.actions.endeffector.velocity.VelocityTikiTorchRoller;
import org.mort11.commands.actions.drivetrain.ResetPosition;
import org.mort11.commands.actions.drivetrain.SetRobotOrientation;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class CenterOnePiece extends SequentialCommandGroup {
  /** Move the robot forward, far enough to gain taxi points. */
  public CenterOnePiece() {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new SequentialCommandGroup(
        new ResetPosition(7.085, 0.59, 180, true),
        new TimedDrive(1, -1, 0, 0, true),
        SetEndeffector.l4().withTimeout(2.5),
        new TimedDrive(0.7, -1, 0, 0, true),
        new WaitCommand(0.75),
        VelocityTikiTorchRoller.outtake().withTimeout(2),
        new TimedDrive(0.25, 1, 0, 0)
      )
    );
  }
}
