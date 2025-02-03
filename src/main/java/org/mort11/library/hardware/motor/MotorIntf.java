package org.mort11.library.hardware.motor;

public interface MotorIntf {

    public void setCurrentLimit(double limit);
    
    public void setDirectionFlip(boolean direction);

    public void setPIDValues(double kP, double kI, double kD);

    public void setPercent(double percent);

    public void setVoltage(double voltage);

    public void setPositionRotations(double setpoint);

    public void setCanivore(String canivore);



    public double getPositionRotations();

    public double getVelocityRPM();

    public double getOutputVoltage();

    public boolean getForwardLimitSwitch();

    public boolean getReverseLimitSwitch();

    public double getAbsoluteValueEncoderPosition();

    public double getAbsoluteValueEncoderVelocity();
}
