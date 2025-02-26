package org.mort11.commands.autons.pathplanned;

import org.mort11.commands.actions.endeffector.pid.Elevate;
import org.mort11.commands.actions.endeffector.pid.SetAlgaeArm;
import org.mort11.config.constants.PhysicalConstants;

import com.pathplanner.lib.auto.NamedCommands;

public class BasicCommands {
    
    public static void setCommands () {
        NamedCommands.registerCommand("AlgaeUp", SetAlgaeArm.l23Intake());
        NamedCommands.registerCommand("AlgaeDown", SetAlgaeArm.rest());

        NamedCommands.registerCommand("ElevatorL2", Elevate.l2().withTimeout(2));
        NamedCommands.registerCommand("ElevatorL3", Elevate.l3().withTimeout(2));
        NamedCommands.registerCommand("ElevatorL4", Elevate.l4().withTimeout(2));
        NamedCommands.registerCommand("ElevatorDown", Elevate.rest().withTimeout(2));
    }
}
