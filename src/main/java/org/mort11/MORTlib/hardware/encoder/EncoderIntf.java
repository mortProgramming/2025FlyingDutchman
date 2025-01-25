package org.mort11.MORTlib.hardware.encoder;

import edu.wpi.first.math.geometry.Rotation2d;

public interface EncoderIntf {

    public void setCanivore(String canivore);

    public Rotation2d getPosition();
    
    public double getVelocityRotations();
}
