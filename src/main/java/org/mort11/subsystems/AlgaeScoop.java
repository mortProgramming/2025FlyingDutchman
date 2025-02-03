package org.mort11.subsystems;

import static org.mort11.config.constants.PIDConstants.Arm.ARM_ROT_CONSTRAINTS;
import static org.mort11.config.constants.PIDConstants.Arm.ROT_KA;
import static org.mort11.config.constants.PIDConstants.Arm.ROT_KD;
import static org.mort11.config.constants.PIDConstants.Arm.ROT_KG;
import static org.mort11.config.constants.PIDConstants.Arm.ROT_KI;
import static org.mort11.config.constants.PIDConstants.Arm.ROT_KP;
import static org.mort11.config.constants.PIDConstants.Arm.ROT_KS;
import static org.mort11.config.constants.PIDConstants.Arm.ROT_KV;
import static org.mort11.config.constants.PhysicalConstants.Arm.ARM_NEVER_POSITION;
import static org.mort11.config.constants.PhysicalConstants.Arm.OFFSET;
import org.mort11.library.hardware.encoder.Encoder;
import static org.mort11.library.hardware.encoder.EncoderTypeEnum.THROUGHBORE;
import org.mort11.library.hardware.motor.Motor;
import static org.mort11.library.hardware.motor.MotorTypeEnum.NEO550;

import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class AlgaeScoop extends SubsystemBase {
    public static Motor scoopMotor;
    public static Motor scoopRoller;
    public static Encoder encoder;
    private static double armSpeed;
    private static double rollerSpeed;
    private static ProfiledPIDController armPidController;
    private static ArmFeedforward feedforward;
    
    public AlgaeScoop(){
        scoopMotor = new Motor(NEO550,0);
        scoopRoller = new Motor(NEO550, 0);
        encoder = new Encoder(THROUGHBORE, 0);
        armSpeed = 0;
        rollerSpeed = 0;
        armPidController = new ProfiledPIDController(ROT_KP, ROT_KI, ROT_KD, ARM_ROT_CONSTRAINTS);
        
        feedforward = new ArmFeedforward(ROT_KS, ROT_KG, ROT_KV, ROT_KA);
    }
    @Override
    public void periodic(){
        SmartDashboard.putNumber("Encoder Position Degress", getEncoderPosition());
        SmartDashboard.putNumber("ArmSpeed", encoder.getVelocityRotations() * 360);

        scoopMotor.setVoltage(armSpeed * 12);
        scoopRoller.setVoltage(rollerSpeed * 12);
    }
    
    public static double getEncoderPosition(){
        return encoder.getPosition().getDegrees();
    }
    
    public static void setPosition(double setpoint) {
        armSpeed = armPidController.calculate(encoderToDegrees(), setpoint) + feedforward.calculate
        (Math.toRadians(encoderToDegrees()),encoder.getVelocityRotations());
    }
    
    public static void setFeedforward(double kS, double kV, double kG, double kA){
        feedforward = new ArmFeedforward(kS, kG, kV, kA);
    }

    public static double encoderToDegrees() {
        double degrees = getEncoderPosition() * 360 + OFFSET;
        if (degrees < 0) {
            degrees += 360;
        }

        if (degrees > ARM_NEVER_POSITION) {
            degrees -= 360;
        }

        if (degrees < -90 && degrees > -270) {
            degrees += 360;
        }
        return degrees;
    }
      public static double getArmVoltage(){
        return scoopMotor.getOutputVoltage();
    }

    public static void setArmVoltage(double voltage){
        scoopMotor.setVoltage(voltage);
    }

    public static void setRollerVoltage(double voltage){
        scoopRoller.setVoltage(voltage);
    }

    public static Motor getscoopRoller(){
        return scoopRoller;
    }

    public static Motor getArmMotor(){
        return scoopMotor;
    }

    public static AlgaeScoop getAlgaeScoop(){
        return new AlgaeScoop();
    }
}
