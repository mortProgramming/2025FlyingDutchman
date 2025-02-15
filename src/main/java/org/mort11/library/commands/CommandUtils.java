package org.mort11.library.commands;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class CommandUtils {

    public static boolean toggleOld;

    public static Command toCommand(Runnable runnable, Subsystem subsystem) {
        return new InstantCommand(runnable, subsystem);
    }

    // public static Command toCommand(Runnable runnable, Subsystem subsystem) {
    //     return new InstantCommand(() -> runnable, subsystem);
    // }

    public static Trigger doubleInput(Trigger trigger1, Trigger trigger2) {
        return trigger1.and(trigger2);
    }

    public static Trigger toggle(BooleanSupplier supplier) {
        toggleOld = supplier.getAsBoolean();
        return new Trigger(() -> (!toggleOld && supplier.getAsBoolean()));
    }
}
