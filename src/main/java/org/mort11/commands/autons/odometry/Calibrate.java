package org.mort11.commands.autons.odometry;

import org.mort11.commands.actions.drivetrain.auto.DriveToPosition;
import org.mort11.commands.actions.endeff.Initiate;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class Calibrate extends SequentialCommandGroup {
    
    public Calibrate() {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
        new SequentialCommandGroup(
            new Initiate(0, 0, 270),
            new DriveToPosition(0, 5, 270)
        )
    );
  }
}
