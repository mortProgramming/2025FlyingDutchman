package org.mort11.commands.autons.odometry;

import org.mort11.commands.actions.Initiate;
import org.mort11.commands.actions.drivetrain.ResetPosition;
import org.mort11.commands.actions.drivetrain.auto.DriveToPosition;
import org.mort11.commands.actions.endeffector.pid.SetEndeffector;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class Start2F2RStation2E extends SequentialCommandGroup {
    
    public Start2F2RStation2E() {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
        new SequentialCommandGroup(
            new Initiate(6.921, 0.5, 270),
            new DriveToPosition(6, 2),
            new DriveToPosition(6, 4, 60),
            new ParallelCommandGroup(
                new DriveToPosition(5.3, 2.87),
                SetEndeffector.l4()
            )
        )
    );
  }
}
