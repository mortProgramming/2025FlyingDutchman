package org.mort11.library.hardware.camera;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;

public interface TagCameraIntf {

    public void setLights(int input);

    public void setRobotOrientation ( double yaw, double yawRate);

    public boolean hasTag();

    public int getId();

    public double[] getPicturePosition();

    public Pose2d getRobotPosition();

    public Pose3d get3dRobotPosition();

    public String getCameraName();
    
}
