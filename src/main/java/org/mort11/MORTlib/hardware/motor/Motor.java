package org.mort11.MORTlib.hardware.motor;

import com.revrobotics.spark.SparkLowLevel.MotorType;

import org.mort11.MORTlib.hardware.brands.ctre.TalonFXMotor;
import org.mort11.MORTlib.hardware.brands.rev.SparkFlexMotor;
import org.mort11.MORTlib.hardware.brands.rev.SparkMaxMotor;

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

    public MotorTypeEnum getMotorType() {
        return motorType;
    }

}