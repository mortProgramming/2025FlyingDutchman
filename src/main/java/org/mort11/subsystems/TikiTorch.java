package org.mort11.subsystems;

import static org.mort11.library.hardware.encoder.EncoderTypeEnum.THROUGHBORE;
import static org.mort11.library.hardware.motor.MotorTypeEnum.NEO;
import static org.mort11.library.hardware.motor.MotorTypeEnum.NEO550;

import org.mort11.library.hardware.encoder.Encoder;
import org.mort11.library.hardware.motor.Motor;
import org.mort11.library.hardware.motor.MotorIntf;
import org.mort11.library.hardware.motor.MotorTypeEnum;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static org.mort11.config.constants.PhysicalConstants.Arm.*;
import edu.wpi.first.math.controller.ArmFeedforward;
import static org.mort11.config.constants.PIDConstants.Arm.*;

public class TikiTorch extends SubsystemBase {
    public static Motor coralArmMotor;
    public static Motor tikiWheel;
    public static Encoder encoder;
    private static double armSpeed;
    private static double rollerSpeed;
    private static ProfiledPIDController armPidController;
    private static ArmFeedforward feedforward;

    public TikiTorch(){
        coralArmMotor = new Motor(NEO550,0);
        tikiWheel = new Motor(NEO550, 0);
        encoder = new Encoder(THROUGHBORE, 0);
        armSpeed = 0;
        rollerSpeed = 0;
        armPidController = new ProfiledPIDController(ROT_KP, ROT_KI, ROT_KD, ARM_ROT_CONSTRAINTS);

        feedforward = new ArmFeedforward(ROT_KS, ROT_KG, ROT_KV, ROT_KA);
        //TODO - implement Constants here

    }

    @Override
    public void periodic(){
        SmartDashboard.putNumber("Encoder Position Degress", getEncoderPosition());
        SmartDashboard.putNumber("ArmSpeed", encoder.getVelocityRotations() * 360);

        coralArmMotor.setVoltage(armSpeed * 12);
        tikiWheel.setVoltage(rollerSpeed * 12);
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
        return coralArmMotor.getOutputVoltage();
    }

    public static void setArmVoltage(double voltage){
        coralArmMotor.setVoltage(voltage);
    }

    public static void setRollerVoltage(double voltage){
        tikiWheel.setVoltage(voltage);
    }

    public static Motor getTikiWheel(){
        return tikiWheel;
    }

    public static Motor getArmMotor(){
        return coralArmMotor;
    }

    public static TikiTorch getTikiTorch(){
        return new TikiTorch();
    }

}

