package org.mort11.library.hardware.brands.ctre;

import org.mort11.library.hardware.motor.MotorIntf;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;

public class TalonFXMotor implements MotorIntf {

    public int ID;

    public TalonFX motor;

    public TalonFXConfiguration config;

    public TalonFXMotor(int ID) {
        this.ID = ID;

        motor = new TalonFX(ID);
        config = new TalonFXConfiguration();

        config.Slot0.kP = 0;
        config.Slot0.kI = 0;
        config.Slot0.kD = 0;

        motor.getConfigurator().apply(config);
    }

    public void setCurrentLimit(double limit) {
        config.CurrentLimits.SupplyCurrentLimit = limit;
        motor.getConfigurator().apply(config);
    }

    public void setDirectionFlip(boolean direction) {
        config.MotorOutput.Inverted = direction ?
        InvertedValue.Clockwise_Positive
        : InvertedValue.CounterClockwise_Positive;
    }

    public void setPIDValues(double kP, double kI, double kD) {
        config.Slot0.kP = kP;
        config.Slot0.kI = kI;
        config.Slot0.kD = kD;
        motor.getConfigurator().apply(config);
    }

    public void setPercent(double percent) {
        motor.set(percent);
    }

    public void setVoltage(double voltage) {
        motor.setVoltage(voltage);
    }

    public void setPositionRotations(double setpoint) {
        motor.setPosition(setpoint);
    }

    public void setCanivore(String canivore) {
        motor = new TalonFX(ID, canivore);
    }



    public double getPositionRotations() {
        // return motor.getPosition().getValueAsDouble();
        return motor.getPosition().getValueAsDouble() * 2.875;
        // return motor.getRotorPosition().getPosition();
    }
    
    public double getVelocityRPM() {
        return motor.getVelocity().getValueAsDouble() * 60;
    }

    public double getOutputVoltage() {
        return motor.getMotorVoltage().getValueAsDouble();
    }

    public boolean getForwardLimitSwitch() {
        return false;
    }

    public boolean getReverseLimitSwitch() {
        return false;
    }

    public double getAbsoluteValueEncoderPosition() {
        return 0;
    }

    public double getAbsoluteValueEncoderVelocity() {
        return 0;
    }

    public TalonFX getMotor() {
        return motor;
    }
}
