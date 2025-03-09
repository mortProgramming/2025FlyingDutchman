package org.mort11.library.hardware.imu;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;

public interface IMUIntf {

    public void setCanivore(String canivore);
    
    public double getAngle();

    public double getRate();

    // public double getAccelerationX();

    // public double getAccelerationY();

    // public double getAccelerationZ();

    // public double getAcceleration();

    public void reset();

    public Rotation2d getRotation2d();

    public Rotation3d getRotation3d();

}
