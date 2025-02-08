package org.mort11.library.hardware.motor;

public class MotorGroup implements MotorIntf {

    public MotorIntf[] motors;

    public int motorCount;

    public MotorGroup(MotorIntf... motors) {
        this.motors = motors; 
        motorCount = motors.length;
    }

    public MotorGroup(MotorTypeEnum motorType, int... ids) {
        motorCount = ids.length;
        motors = new Motor[motorCount];

        for(int i = 0; i < motorCount; i++) {
            motors[i] = new Motor(motorType, ids[i]);
        }
    }

    // public void giveAll(Runnable method) {
    //     for (int i = 0; i < motorCount; i++) {
    //         method(motors[i]);
    //         motors[i].method();
    //     }
    // }

    public void setCurrentLimit(double limit) {
        for (int i = 0; i < motorCount; i++) {
            motors[i].setCurrentLimit(limit);
        }
    }

    public void setCurrentLimit(int motorNumber, double limit) {
        motors[motorNumber].setCurrentLimit(limit);
    }

    public void setDirectionFlip(boolean direction) {
        for(int i = 0; i < motorCount; i++) {
            motors[i].setDirectionFlip(direction);
        }
    }

    public void setDirectionFlip(int motorNumber, boolean direction) {
        motors[motorNumber].setDirectionFlip(direction);
    }

    public void setPIDValues(double kP, double kI, double kD) {
        for (int i = 0; i < motorCount; i++) {
            motors[i].setPIDValues(kP, kI, kD);
        }
    }

    public void setPIDValues(int motorNumber, double kP, double kI, double kD) {
        motors[motorNumber].setPIDValues(kP, kI, kD);
    }

    public void setPercent(double percent) {
        for (int i = 0; i < motorCount; i++) {
            motors[i].setPercent(percent);
        }
    }

    public void setPercent(int motorNumber, double percent) {
        motors[motorNumber].setPercent(percent);
    }

    public void setVoltage(double voltage) {
        for (int i = 0; i < motorCount; i++) {
            motors[i].setVoltage(voltage);
        }
    }

    public void setVoltage(int motorNumber, double voltage) {
        motors[motorNumber].setVoltage(voltage);
    }

    public void setPositionRotations(double setpoint) {
        for (int i = 0; i < motorCount; i++) {
            motors[i].setPositionRotations(setpoint);
        }
    }
    
    public void setPositionRotations(int motorNumber, double setpoint) {
        motors[motorNumber].setPositionRotations(setpoint);
    }

    // public void setPositionRotations(double position, double setpoint) {
    //     for (int i = 0; i < motorCount; i++) {
    //         motors[i].setPositionRotations(position, setpoint);
    //     }
    // }
    
    // public void setPositionRotations(int motorNumber, double position, double setpoint) {
    //     motors[motorNumber].setPositionRotations(position, setpoint);
    // }

    public void setCanivore(String canivore) {
        for (int i = 0; i < motorCount; i++) {
            motors[i].setCanivore(canivore);
        }
    }

    public void setCanivore(int motorNumber, String canivore) {
        motors[motorNumber].setCanivore(canivore);
    }


    
    public double getPositionRotations() {
        return motors[0].getPositionRotations();
    }

     public double getPositionRotations(int motorNumber) {
        return motors[motorNumber].getPositionRotations();
    }

    public double getVelocityRPM() {
        return motors[0].getVelocityRPM();
    }

    public double getVelocityRPM(int motorNumber) {
        return motors[motorNumber].getVelocityRPM();
    }

    public double getOutputVoltage() {
        return motors[0].getOutputVoltage();
    }

    public double getOutputVoltage(int motorNumber) {
        return motors[motorNumber].getOutputVoltage();
    }

    public MotorIntf getMotor(int motorNumber) {
        return motors[motorNumber];
    }

    public boolean getForwardLimitSwitch() {
        return motors[0].getForwardLimitSwitch();
    }

    public boolean getForwardLimitSwitch(int motorNumber) {
        return motors[motorNumber].getForwardLimitSwitch();
    }

    public boolean getReverseLimitSwitch() {
        return motors[0].getReverseLimitSwitch();
    }

    public boolean getReverseLimitSwitch(int motorNumber) {
        return motors[motorNumber].getReverseLimitSwitch();
    }

    public double getAbsoluteValueEncoderPosition() {
        return motors[0].getAbsoluteValueEncoderPosition();
    }

    public double getAbsoluteValueEncoderPosition(int motorNumber) {
        return motors[motorNumber].getAbsoluteValueEncoderPosition();
    }

    public double getAbsoluteValueEncoderVelocity() {
        return motors[0].getAbsoluteValueEncoderVelocity();
    }

    public double getAbsoluteValueEncoderVelocity(int motorNumber) {
        return motors[motorNumber].getAbsoluteValueEncoderVelocity();
    }

    public MotorIntf[] getMotor () {
        return motors;
    }
}
