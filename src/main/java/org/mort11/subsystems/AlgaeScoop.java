package org.mort11.subsystems;

import org.mort11.library.hardware.motor.Motor;

import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class AlgaeScoop extends SubsystemBase{
    public static Motor scoopMotor;
    public static Motor scoopRoller;
    public static Encoder encoder;
    private static double armSpeed;
    private static double rollerSpeed;
    private static ProfiledPIDController armPidController;
    private static ArmFeedforward feedforward;
    

}
