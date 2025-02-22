package org.mort11.commands.autons.timed;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import org.mort11.commands.actions.Initiate;
import org.mort11.commands.actions.drivetrain.TimedDrive;
import org.mort11.commands.actions.endeffector.pid.SetEndeffector;
import org.mort11.commands.actions.endeffector.velocity.VelocityTikiTorchRoller;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class OnePiece extends SequentialCommandGroup {
  /** Move the robot forward, far enough to gain taxi points. */
  public OnePiece() {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new SequentialCommandGroup(
        new Initiate(7, 4, 180),
        new TimedDrive(1.3, -0.5, 0, 0),
        SetEndeffector.l1().withTimeout(2),
        VelocityTikiTorchRoller.outtake().withTimeout(2),
        new ParallelCommandGroup(
          new TimedDrive(1, 0, 0.5, 0),
          VelocityTikiTorchRoller.outtake().withTimeout(2)
        )
      )
    );
  }
}
