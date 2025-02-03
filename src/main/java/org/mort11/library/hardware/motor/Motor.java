package org.mort11.library.hardware.motor;

import org.mort11.library.hardware.brands.ctre.TalonFXMotor;
import org.mort11.library.hardware.brands.rev.SparkFlexMotor;
import org.mort11.library.hardware.brands.rev.SparkMaxMotor;

import com.revrobotics.spark.SparkLowLevel.MotorType;

public class Motor implements MotorIntf {

    public MotorTypeEnum motorType;
    public int ID;
    public MotorType brushType;
    public boolean direction;

    public MotorIntf motor;
    
    public Motor(MotorTypeEnum motorType, int ID) {
        this(motorType, ID, MotorType.kBrushless);
    }
    
    public Motor(MotorTypeEnum motorType, int ID, MotorType brushType) {
        this.motorType = motorType;
        this.ID = ID;
        this.brushType = brushType;

        switch (motorType) {
            case NEO:
                motor = new SparkMaxMotor(ID, brushType);
                break;

            case NEO550:
                motor = new SparkMaxMotor(ID, brushType);
                break;

            case VORTEX:
                motor = new SparkFlexMotor(ID, brushType);
                break;

            case FALCON:
                motor = new TalonFXMotor(ID);
                break;

            case KRAKEN:
                motor = new TalonFXMotor(ID);
                break;
        }
    }

    public void setCurrentLimit(double limit) {
        motor.setCurrentLimit(limit);
    }

    public void setDirectionFlip(boolean direction) {
        motor.setDirectionFlip(direction);
    }

    public void setPIDValues(double kP, double kI, double kD) {
        motor.setPIDValues(kP, kI, kD);
    }

    public void setPercent(double percent) {
        motor.setPercent(percent);
    }

    public void setVoltage(double voltage) {
        motor.setVoltage(voltage);
    }

    public void setPositionRotations(double setpoint) {
        motor.setPositionRotations(setpoint);
    }

    public void setCanivore(String canivore) {
        motor.setCanivore(canivore);
    }


    
    public double getPositionRotations() {
        return motor.getPositionRotations();
    }

    public double getVelocityRPM() {
        return motor.getVelocityRPM();
    }

    public double getOutputVoltage() {
        return motor.getOutputVoltage();
    }

    public boolean getForwardLimitSwitch() {
        return motor.getForwardLimitSwitch();
    }

    public boolean getReverseLimitSwitch() {
        return motor.getReverseLimitSwitch();
    }

    public double getAbsoluteValueEncoderPosition() {
        return motor.getAbsoluteValueEncoderPosition();
    }

    public double getAbsoluteValueEncoderVelocity() {
        return motor.getAbsoluteValueEncoderVelocity();
    }

    public MotorTypeEnum getMotorType() {
        return motorType;
    }

}