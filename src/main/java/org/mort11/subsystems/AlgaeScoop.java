package org.mort11.subsystems;

import static org.mort11.config.constants.PIDConstants.AlgaeScoop.*;
import static org.mort11.config.constants.PhysicalConstants.AlgaeScoop.*;
import static org.mort11.config.constants.PortConstants.AlgaeScoop.*;

import static org.mort11.config.constants.PhysicalConstants.ROBOT_VOLTAGE;
import org.mort11.library.hardware.encoder.Encoder;
import static org.mort11.library.hardware.encoder.EncoderTypeEnum.THROUGHBORE;
import org.mort11.library.hardware.motor.Motor;
import static org.mort11.library.hardware.motor.MotorTypeEnum.NEO550;

import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class AlgaeScoop extends SubsystemBase {
    public static AlgaeScoop algaeScoop;

    public Motor scoopArm, scoopRoller;

    private double armSpeed, rollerSpeed;

    private ProfiledPIDController armPidController;
    private ArmFeedforward feedforward;
    
    public AlgaeScoop() {
        scoopArm = new Motor(NEO550, ALGAESCOOP_ARM_MOTOR);
        scoopRoller = new Motor(NEO550, ALGAESCOOP_ROLLER_MOTOR);
        
        armSpeed = 0;
        rollerSpeed = 0;

        armPidController = new ProfiledPIDController(ROT_KP, ROT_KI, ROT_KD, ARM_ROT_CONSTRAINTS);
        feedforward = new ArmFeedforward(ROT_KS, ROT_KG, ROT_KV, ROT_KA);
    }

    @Override
    public void periodic(){
        scoopArm.setVoltage(armSpeed * ROBOT_VOLTAGE);
        scoopRoller.setVoltage(rollerSpeed * ROBOT_VOLTAGE);

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
    return scoopArm.getOutputVoltage();
}

public double getEncoderPosition(){
    return scoopArm.getAbsoluteValueEncoderPosition();
}

public double getEncoderVelocityDegrees() {
    return scoopArm.getAbsoluteValueEncoderVelocity() * 360;
}

public Motor getRollerMotor(){
    return scoopRoller;
}

public Motor getArmMotor(){
    return scoopArm;
}

    public static AlgaeScoop getInstance(){
        if (algaeScoop == null) {
			algaeScoop = new AlgaeScoop();
		}
		return algaeScoop;
    }
}
