package org.mort11.subsystems;

import org.mort11.library.hardware.camera.TagCamera;
import org.mort11.library.hardware.camera.TagCameraTypeEnum;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static org.mort11.config.constants.FieldConstants.*;
import static org.mort11.config.constants.PortConstants.Vision.*;

public class Vision extends SubsystemBase {

    private static Vision vision;

    public TagCamera frontCamera;

	private AprilTagFieldLayout tagLayout;

    private Vision() {
		frontCamera = new TagCamera(TagCameraTypeEnum.LIMELIGHT, FRONT_CAMERA_NAME);

		// tagLayout = AprilTagFieldLayout.loadField(AprilTagFields.k2024Crescendo);
		tagLayout = new AprilTagFieldLayout(APRIL_TAGS, FIELD_LENGTH, FIELD_WIDTH);
	}

	public Pose2d getTagPosition(int tagID) {
		return tagLayout.getTagPose(tagID).get().toPose2d();
	}
    
    public static Vision getInstance() {
		if (vision == null) {
			vision = new Vision();
		}
		return vision;
	}
}
