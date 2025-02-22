package org.mort11.commands.autons.pathplanned;

import org.mort11.commands.actions.endeffector.pid.SetAlgaeArm;

import com.pathplanner.lib.auto.NamedCommands;

public class BasicCommands {
    
    public static void setCommands () {
        // NamedCommands.registerCommand("AlgaeUp", SetAlgaeArm.l23Intake());

        NamedCommands.registerCommand("AlgaeUp", null);
    }
}
