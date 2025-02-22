package org.mort11.commands.autons.pathplanned;

import org.mort11.commands.actions.endeffector.pid.Elevate;
import org.mort11.commands.actions.endeffector.pid.SetAlgaeArm;
import org.mort11.config.constants.PhysicalConstants;

import com.pathplanner.lib.auto.NamedCommands;

public class BasicCommands {
    
    public static void setCommands () {
        // NamedCommands.registerCommand("AlgaeUp", SetAlgaeArm.l23Intake());

        NamedCommands.registerCommand("AlgaeUp", Elevate.l2().withTimeout(2));
        NamedCommands.registerCommand("ElevatorDown", Elevate.rest().withTimeout(2));
    }
}
