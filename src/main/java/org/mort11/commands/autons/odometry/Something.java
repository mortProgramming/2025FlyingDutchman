package org.mort11.commands.autons.odometry;

import org.mort11.commands.actions.Initiate;
import org.mort11.commands.actions.drivetrain.DriveToPosition;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class Something extends SequentialCommandGroup {
    
    public Something() {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
        new SequentialCommandGroup(
            new Initiate(7, 4, 180),
            new DriveToPosition(5.9, 4)
        )
    );
  }
}
