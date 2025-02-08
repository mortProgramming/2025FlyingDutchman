package org.mort11.subsystems;

import static org.mort11.config.constants.PIDConstants.TikiTorch.*;
import static org.mort11.config.constants.PhysicalConstants.TikiTorch.*;
import static org.mort11.config.constants.PortConstants.TikiTorch.*;

import static org.mort11.config.constants.PhysicalConstants.ROBOT_VOLTAGE;
import org.mort11.library.hardware.motor.Motor;
import static org.mort11.library.hardware.motor.MotorTypeEnum.NEO550;

import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class TikiTorch extends SubsystemBase {
    public static TikiTorch tikiTorch;

    public Motor tikiArm, tikiRoller;

    private double armSpeed, rollerSpeed;
    
    private ProfiledPIDController armPidController;
    private ArmFeedforward feedforward;
    
    public TikiTorch(){
        tikiArm = new Motor(NEO550, TIKITORCH_ARM_MOTOR);
        tikiRoller = new Motor(NEO550, TIKITORCH_ROLLER_MOTOR);

        armSpeed = 0;
        rollerSpeed = 0;

        armPidController = new ProfiledPIDController(ROT_KP, ROT_KI, ROT_KD, ARM_ROT_CONSTRAINTS);
        feedforward = new ArmFeedforward(ROT_KS, ROT_KG, ROT_KV, ROT_KA);
    }

    @Override
    public void periodic(){
        tikiArm.setVoltage(armSpeed * ROBOT_VOLTAGE);
        tikiRoller.setVoltage(rollerSpeed * ROBOT_VOLTAGE);

        SmartDashboard.putNumber("Encoder Position Degress", getEncoderPosition());
        SmartDashboard.putNumber("ArmSpeed", getEncoderVelocityDegrees());
    }
        
        public void setPosition(double setpoint) {
            armSpeed = armPidController.calculate(encoderToDegrees(), setpoint) + 
            feedforward.calculate(Math.toRadians(encoderToDegrees()), getEncoderVelocityDegrees());
        }

        public void setArmVoltage(double voltage){
           this.armSpeed = voltage / 12;
        }
    
        public void setRollerSpeed(double rollerSpeed){
            this.rollerSpeed = rollerSpeed / 12;
        }

    public double encoderToDegrees() {
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

    public double getArmVoltage(){
        return tikiArm.getOutputVoltage();
    }

    public double getEncoderPosition(){
        return tikiArm.getAbsoluteValueEncoderPosition();
    }
    
    public double getEncoderVelocityDegrees() {
        return tikiArm.getAbsoluteValueEncoderVelocity() * 360;
    }

    public Motor getRollerMotor(){
        return tikiRoller;
    }

    public Motor getArmMotor(){
        return tikiArm;
    }

    public static TikiTorch getInstance() {
		if (tikiTorch == null) {
			tikiTorch = new TikiTorch();
		}
		return tikiTorch;
	}
}
