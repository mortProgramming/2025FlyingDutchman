package org.mort11.commands.autons.timed;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import org.mort11.commands.actions.drivetrain.auto.TimedDrive;
import org.mort11.commands.actions.endeff.Initiate;
import org.mort11.commands.actions.endeff.pid.SetEndeffector;
import org.mort11.commands.actions.endeff.velocity.VelocityTikiTorchRoller;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class RedSideOnePiece extends SequentialCommandGroup {
  /** Move the robot forward, far enough to gain taxi points. */
  public RedSideOnePiece() {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new SequentialCommandGroup(
        new Initiate(7, 4, 90),
        new TimedDrive(2.65, 0.75, -0.85, -9.15, true),
        SetEndeffector.l4().withTimeout(2),
        new TimedDrive(0.3, 1.25, 0.45, 0),
        VelocityTikiTorchRoller.outtake().withTimeout(2)
        // new TimedDrive(0.25, 0, 1, 0, true),
        // SetEndeffector.intake().withTimeout(2),
        // new TimedDrive(3, 1, 1, -40, true)
      )
    );
  }
}
