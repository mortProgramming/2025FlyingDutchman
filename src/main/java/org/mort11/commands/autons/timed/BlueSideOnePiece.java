package org.mort11.commands.autons.timed;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

import org.mort11.commands.actions.drivetrain.auto.TimedDrive;
import org.mort11.commands.actions.endeff.Initiate;
import org.mort11.commands.actions.endeff.pid.SetEndeffector;
import org.mort11.commands.actions.endeff.velocity.VelocityTikiTorchRoller;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class BlueSideOnePiece extends SequentialCommandGroup {
  /** Move the robot forward, far enough to gain taxi points. */
  public BlueSideOnePiece() {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new SequentialCommandGroup(
        new Initiate(7, 0.5, 270),
        new TimedDrive(2.65, -0.70, 0.85, -9.05, true),
        SetEndeffector.l4().withTimeout(2.5),
        new TimedDrive(0.3, -1.23, 0.85, 0),
        new WaitCommand(0.75),
        VelocityTikiTorchRoller.outtake().withTimeout(2),
        new TimedDrive(0.25, 0, -1, 0, true)
        // SetEndeffector.intake().withTimeout(2),
        // new TimedDrive(3, -1, -1, -40, true)
      )
    );
  }
}
