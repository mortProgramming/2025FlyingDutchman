package org.mort11.library.hardware.encoder;

import edu.wpi.first.math.geometry.Rotation2d;

public interface EncoderIntf {

    public void setCanivore(String canivore);

    public Rotation2d getPosition();
    
    public double getVelocityRotations();
}
