package org.mort11.subsystems;

import static org.mort11.config.constants.PIDConstants.TikiTorchArm.*;
import static org.mort11.config.constants.PhysicalConstants.TikiTorchArm.*;
import static org.mort11.config.constants.PortConstants.TikiTorchArm.*;

import static org.mort11.config.constants.PhysicalConstants.ROBOT_VOLTAGE;
import org.mort11.library.hardware.motor.Motor;
import static org.mort11.library.hardware.motor.MotorTypeEnum.NEO550;

import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class TikiTorchArm extends SubsystemBase {
    public static TikiTorchArm tikiTorchArm;

    public Motor tikiTorchArmMotor;

    private double armSpeed, rollerSpeed;
    
    private ProfiledPIDController armPidController;
    private ArmFeedforward feedforward;
    
    public TikiTorchArm(){
        tikiTorchArmMotor = new Motor(NEO550, ARM_MOTOR);

        armSpeed = 0;
        rollerSpeed = 0;

        armPidController = new ProfiledPIDController(ROT_KP, ROT_KI, ROT_KD, ARM_ROT_CONSTRAINTS);
        feedforward = new ArmFeedforward(ROT_KS, ROT_KG, ROT_KV, ROT_KA);
    }

    @Override
    public void periodic(){
        tikiTorchArmMotor.setPercent(armSpeed);
        SmartDashboard.putNumber("Tiki Encoder Position Degress", encoderToDegrees());
        SmartDashboard.putNumber("TikiArmSpeed", getEncoderVelocityDegrees());
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
            double degrees = getEncoderPosition() * 360;
        
            if (degrees < ARM_TOP_NEVER_POSITION && degrees > ARM_BOTTOM_NEVER_POSITION) {
                degrees += 360;
            }
        
            return degrees + OFFSET; 
        }

    public double getArmVoltage(){
        return tikiTorchArmMotor.getOutputVoltage();
    }

    public double getEncoderPosition(){
        return tikiTorchArmMotor.getAbsoluteValueEncoderPosition();
    }
    
    public double getEncoderVelocityDegrees() {
        return tikiTorchArmMotor.getAbsoluteValueEncoderVelocity() * 360;
    }

    public Motor getArmMotor(){
        return tikiTorchArmMotor;
    }

    public static TikiTorchArm getInstance() {
		if (tikiTorchArm == null) {
			tikiTorchArm = new TikiTorchArm();
		}
		return tikiTorchArm;
	}
}
