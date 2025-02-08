package org.mort11.subsystems;

import static org.mort11.config.constants.PIDConstants.AlgaeArm.*;
import static org.mort11.config.constants.PhysicalConstants.AlgaeArm.*;
import static org.mort11.config.constants.PortConstants.AlgaeArm.*;

import static org.mort11.config.constants.PhysicalConstants.ROBOT_VOLTAGE;
import org.mort11.library.hardware.encoder.Encoder;
import static org.mort11.library.hardware.encoder.EncoderTypeEnum.THROUGHBORE;
import org.mort11.library.hardware.motor.Motor;
import static org.mort11.library.hardware.motor.MotorTypeEnum.NEO550;

import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class AlgaeArm extends SubsystemBase {
    public static AlgaeArm algaeScoop;

    public Motor scoopArm;

    private double armSpeed;

    private ProfiledPIDController armPidController;
    private ArmFeedforward feedforward;
    
    public AlgaeArm() {
        scoopArm = new Motor(NEO550, ARM_MOTOR);
        
        armSpeed = 0;

        armPidController = new ProfiledPIDController(ROT_KP, ROT_KI, ROT_KD, ARM_ROT_CONSTRAINTS);
        feedforward = new ArmFeedforward(ROT_KS, ROT_KG, ROT_KV, ROT_KA);
    }

    @Override
    public void periodic(){
        scoopArm.setVoltage(armSpeed * ROBOT_VOLTAGE);

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

public double encoderToDegrees() {
    double degrees = getEncoderPosition() * 360;

    if (degrees < ARM_TOP_NEVER_POSITION && degrees > ARM_BOTTOM_NEVER_POSITION) {
        degrees += 360;
    }

    return degrees + OFFSET; 
}

public double getArmVoltage(){
    return scoopArm.getOutputVoltage();
}

public double getEncoderPosition(){
    return scoopArm.getAbsoluteValueEncoderPosition();
}

public double getEncoderVelocityDegrees() {
    return scoopArm.getAbsoluteValueEncoderVelocity() * 360;
}

public Motor getArmMotor(){
    return scoopArm;
}

    public static AlgaeArm getInstance(){
        if (algaeScoop == null) {
			algaeScoop = new AlgaeArm();
		}
		return algaeScoop;
    }
}
