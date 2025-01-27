package org.mort11.library.hardware.brands.limelight;

import org.mort11.library.hardware.camera.NoteCameraIntf;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class LimelightNoteCamera implements NoteCameraIntf {

    public NetworkTable cameraTable;

    public String cameraName;

    public LimelightNoteCamera(String cameraName) {
        this.cameraName = cameraName;

        cameraTable = NetworkTableInstance.getDefault().getTable("limelight-" + cameraName);
    }

    public void setLights(int input) {
		cameraTable.getEntry("ledMode").setNumber(input);
	}

    public double[] getPicturePosition() {
        double[] data = new double[3];
        data[0] = cameraTable.getEntry("tx").getDouble(0);
        data[1] = cameraTable.getEntry("ty").getDouble(0);
        data[2] = cameraTable.getEntry("ta").getDouble(0);
        return data;
    }

    public String getCameraName () {
        return cameraName;
    }
}
