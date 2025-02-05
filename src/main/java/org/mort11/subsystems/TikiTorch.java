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
    public  Motor coralArmMotor;
    public  Motor tikiWheel;
    public  Encoder encoder;
    private double armSpeed;
    private double rollerSpeed;
    private ProfiledPIDController armPidController;
    private ArmFeedforward feedforward;
    private static TikiTorch tikitorch;

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
        public  double getEncoderPosition(){
                return this.encoder.getPosition().getDegrees();
            }
        
            public void setPosition(double setpoint) {
                this.armSpeed = this.armPidController.calculate(encoderToDegrees(), setpoint) + feedforward.calculate
                (Math.toRadians(encoderToDegrees()),encoder.getVelocityRotations());
            }
        
            public void setFeedforward(double kS, double kV, double kG, double kA){
                this.feedforward = new ArmFeedforward(kS, kG, kV, kA);
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
        return this.coralArmMotor.getOutputVoltage();
    }

    public void setArmVoltage(double voltage){
        this.coralArmMotor.setVoltage(voltage);
    }

    public void setRollerVoltage(double voltage){
        this.rollerSpeed = voltage / 12;
        tikiWheel.setVoltage(voltage);
    }

    public Motor getTikiWheel(){
        return this.tikiWheel;
    }

    public Motor getArmMotor(){
        return this.coralArmMotor;
    }

    public static TikiTorch getInstance() {
		if (tikitorch == null) {
			tikitorch = new TikiTorch();
		}
		return tikitorch;
	}

}

