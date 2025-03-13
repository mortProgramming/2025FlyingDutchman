package org.mort11.commands.autons.pathplanned;

import org.mort11.commands.actions.endeffector.pid.Elevate;
import org.mort11.commands.actions.endeffector.pid.SetAlgaeArm;
import org.mort11.commands.actions.endeffector.pid.SetEndeffector;
import org.mort11.config.constants.PhysicalConstants;

import com.pathplanner.lib.auto.NamedCommands;

public class BasicCommands {
    
    public static void setCommands () {
        NamedCommands.registerCommand("L2", SetEndeffector.l2());
        NamedCommands.registerCommand("L3", SetEndeffector.l3());
        NamedCommands.registerCommand("L4", SetEndeffector.l4());
        NamedCommands.registerCommand("Rest", SetEndeffector.rest());
    }
}
