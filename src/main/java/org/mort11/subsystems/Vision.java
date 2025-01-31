package org.mort11.subsystems;

import static org.mort11.config.constants.FieldConstants.APRIL_TAGS;
import static org.mort11.config.constants.FieldConstants.FIELD_LENGTH;
import static org.mort11.config.constants.FieldConstants.FIELD_WIDTH;
import static org.mort11.config.constants.PortConstants.Vision.FRONT_CAMERA_NAME;
import org.mort11.library.hardware.camera.TagCamera;
import org.mort11.library.hardware.camera.TagCameraTypeEnum;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Vision extends SubsystemBase {

    private static Vision vision;

    public TagCamera frontCamera;

	private AprilTagFieldLayout tagLayout;
	private AprilTagFieldLayout cameraFieldLayout;

    private Vision() {
		frontCamera = new TagCamera(TagCameraTypeEnum.LIMELIGHT, FRONT_CAMERA_NAME);

		// tagLayout = AprilTagFieldLayout.loadField(AprilTagFields.k2024Crescendo);
		tagLayout = new AprilTagFieldLayout(APRIL_TAGS, FIELD_LENGTH, FIELD_WIDTH);
		cameraFieldLayout = AprilTagFieldLayout.loadField(AprilTagFields.k2025Reefscape);
	}

	public Translation2d getRobotPoseField() {
		return getTagToRobotPose().plus(
			new Transform2d(
				tagLayout.getTagPose(
					frontCamera.getId()
				).get().toPose2d().getTranslation(),
				new Rotation2d()
			)
		).getTranslation();
	}

	public Pose2d getTagToRobotPose() {
		return new Pose2d(
			frontCamera.getRobotPosition().minus(
				cameraFieldLayout.getTagPose(
					frontCamera.getId()
				).get().toPose2d()
			).getTranslation(),
			frontCamera.getRobotPosition().getRotation().minus(
				cameraFieldLayout.getTagPose(
					frontCamera.getId()
				).get().getRotation().toRotation2d()
			)
		);
	}

	public Pose2d getFieldTagPose(int tagID) {
		return tagLayout.getTagPose(tagID).get().toPose2d();
	}

	public TagCamera getFrontCamera() {
		return frontCamera;
	}
    
    public static Vision getInstance() {
		if (vision == null) {
			vision = new Vision();
		}
		return vision;
	}
}
