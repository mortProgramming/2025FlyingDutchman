package org.mort11.subsystems;

import static org.mort11.config.constants.FieldConstants.APRIL_TAGS;
import static org.mort11.config.constants.FieldConstants.FIELD_LENGTH;
import static org.mort11.config.constants.FieldConstants.FIELD_WIDTH;
import static org.mort11.config.constants.PortConstants.Vision.FRONT_CAMERA_NAME;
import org.mort11.library.hardware.camera.TagCamera;
import org.mort11.library.hardware.camera.TagCameraTypeEnum;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.HttpCamera;
import edu.wpi.first.cscore.HttpCamera.HttpCameraKind;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Vision extends SubsystemBase {

    private static Vision vision;

	private static HttpCamera feed;

	private AprilTagFieldLayout tagLayout;
	private AprilTagFieldLayout cameraFieldLayout;

	private NetworkTable cameraTable;

    private Vision() {

		tagLayout = new AprilTagFieldLayout(APRIL_TAGS, FIELD_LENGTH, FIELD_WIDTH);
		cameraFieldLayout = AprilTagFieldLayout.loadField(AprilTagFields.k2025ReefscapeAndyMark);

		cameraTable = NetworkTableInstance.getDefault().getTable(FRONT_CAMERA_NAME);
		// feed = new HttpCamera("limelight", "http://10.0.11.11:5801/",HttpCameraKind.kMJPGStreamer);
		// CameraServer.startAutomaticCapture(feed);
	}

	@Override
	public void periodic() {
		SmartDashboard.putNumber("Tag Pose", cameraTable.getEntry("tid").getInteger(-1));
		SmartDashboard.putNumber("X Degrees", cameraTable.getEntry("tx").getDouble(-1));
		SmartDashboard.putBoolean("Tag?", hasTag());
	}

	public void setLights(int input) {
		cameraTable.getEntry("ledMode").setNumber(input);
	}

    public void setRobotOrientation(double yaw, double yawRate) {
        double[] positionArray = {yaw, yawRate, 0, 0, 0, 0};
        cameraTable.getEntry("robot_orientation_set").setDoubleArray(positionArray);
    }

    public boolean hasTag () {
        return 1 == cameraTable.getEntry("tv").getDouble(0);
    }

    public int getId() {
		if(hasTag()){
			return (int) cameraTable.getEntry("tid").getInteger(-1);
		}
		return -1;
		
	}

    public double[] getPicturePosition() {
        double[] data = new double[3];
        data[0] = cameraTable.getEntry("tx").getDouble(0);
        data[1] = cameraTable.getEntry("ty").getDouble(0);
        data[2] = cameraTable.getEntry("ta").getDouble(0);
        return data;
    }

	public Pose2d getRelativeRobotPosition() {
		double[] poseNums = new double[6];
		
		poseNums = cameraTable.getEntry("camerapose_targetspace").getDoubleArray(new double[6]);

		return new Pose2d(poseNums[0], poseNums[2], new Rotation2d(Math.toRadians(poseNums[4])));
	}

    public Pose2d getRobotPosition() {
		double[] poseNums = new double[6];
		
		poseNums = cameraTable.getEntry("botpose_orb_wpiblue").getDoubleArray(new double[6]);

		return new Pose2d(poseNums[0], poseNums[2], new Rotation2d(Math.toRadians(poseNums[4])));
	}

    public Pose3d get3dRobotPosition() {
        double[] poseNums = new double[6];

        poseNums = cameraTable.getEntry("botpose_orb_wpiblue").getDoubleArray(new double[6]);

        return new Pose3d(
            new Translation3d(poseNums[0], poseNums[1], poseNums[2]), 
            new Rotation3d(Math.toRadians(poseNums[3]), Math.toRadians(poseNums[4]), Math.toRadians(poseNums[5]))
        );
    }

	// public Translation2d getRobotPoseField() {
	// 	return getTagToRobotPose().plus(
	// 		new Transform2d(
	// 			tagLayout.getTagPose(
	// 				frontCamera.getId()
	// 			).get().toPose2d().getTranslation(),
	// 			new Rotation2d()
	// 		)
	// 	).getTranslation();
	// }

	// public Pose2d getTagToRobotPose() {
	// 	return new Pose2d(
	// 		frontCamera.getRobotPosition().minus(
	// 			cameraFieldLayout.getTagPose(
	// 				frontCamera.getId()
	// 			).get().toPose2d()
	// 		).getTranslation(),
	// 		frontCamera.getRobotPosition().getRotation().minus(
	// 			cameraFieldLayout.getTagPose(
	// 				frontCamera.getId()
	// 			).get().getRotation().toRotation2d()
	// 		)
	// 	);
	// }

	public Pose2d getFieldTagPose(int tagID) {
		return tagLayout.getTagPose(tagID).get().toPose2d();
	}

	// public TagCamera getFrontCamera() {
	// 	return frontCamera;
	// }
    
    public static Vision getInstance() {
		if (vision == null) {
			vision = new Vision();
		}
		return vision;
	}
}