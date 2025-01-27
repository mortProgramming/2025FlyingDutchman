package org.mort11.library.hardware.camera;

import org.mort11.library.hardware.brands.limelight.LimelightTagCamera;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;

public class TagCamera implements TagCameraIntf {

    public TagCameraIntf camera;

    public TagCameraTypeEnum cameraType;

    public String cameraName;

    public TagCamera (TagCameraTypeEnum cameraType, String cameraName) {
        this.cameraType = cameraType;
        this.cameraName = cameraName;

        switch (cameraType) {
            // case PhotonVision:
            //     camera = new PhotonVisionTagCamera(cameraName);
            //     break;
            case LIMELIGHT:
                camera = new LimelightTagCamera(cameraName);
                break;
        }
    }

    public void setLights(int input) {
		camera.setLights(input);
	}

    public void setRobotOrientation (double yaw, double yawRate) {
        camera.setRobotOrientation(yaw, yawRate);
    }

    public boolean hasTag() {
        return camera.hasTag();
    }

    public int getId() {
        return camera.getId();
    }

    public double[] getPicturePosition() {
        return camera.getPicturePosition();
    }

    public Pose2d getRobotPosition () {
        return camera.getRobotPosition();
    }

    public Pose3d get3dRobotPosition () {
        return camera.get3dRobotPosition();
    }

    public String getCameraName() {
        return camera.getCameraName();
    }
    
}
