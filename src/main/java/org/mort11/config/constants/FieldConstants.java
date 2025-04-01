package org.mort11.config.constants;

import java.util.List;

import edu.wpi.first.apriltag.AprilTag;
import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Translation3d;

public final class FieldConstants {
    public static final double FIELD_LENGTH = 16.541;
    public static final double FIELD_WIDTH = 8.211;

    public static final AprilTagFieldLayout FIELD = AprilTagFieldLayout.loadField(AprilTagFields.k2025ReefscapeAndyMark);

    public final class Reef {
        public static final double REEF_CORNER_30_DEG_X = 1;
        public static final double REEF_CORNER_30_DEG_Y = 2;

        public static final double REEF_CORNER_90_DEG_X = 1;
        public static final double REEF_CORNER_90_DEG_Y = 2;

        public static final double REEF_CORNER_150_DEG_X = 1;
        public static final double REEF_CORNER_150_DEG_Y = 2;

        public static final double REEF_CORNER_210_DEG_X = 1;
        public static final double REEF_CORNER_210_DEG_Y = 2;

        public static final double REEF_CORNER_270_DEG_X = 1;
        public static final double REEF_CORNER_270_DEG_Y = 2;

        public static final double REEF_CORNER_330_DEG_X = 1;
        public static final double REEF_CORNER_330_DEG_Y = 2;

        public static final double REEF_LINE_POSITIVE_SLOPE = (REEF_CORNER_30_DEG_Y - REEF_CORNER_210_DEG_Y) 
            / (REEF_CORNER_30_DEG_X - REEF_CORNER_210_DEG_X);
        public static final double REEF_LINE_POSITIVE_Y_INTERCEPT = REEF_CORNER_30_DEG_Y - REEF_LINE_POSITIVE_SLOPE * REEF_CORNER_30_DEG_X;
        
        public static final double REEF_LINE_NEGATIVE_SLOPE = (REEF_CORNER_330_DEG_Y - REEF_CORNER_150_DEG_Y) 
            / (REEF_CORNER_330_DEG_X - REEF_CORNER_150_DEG_X);
        public static final double REEF_LINE_NEGATIVE_Y_INTERCEPT = REEF_CORNER_330_DEG_Y - REEF_LINE_NEGATIVE_SLOPE * REEF_CORNER_330_DEG_X;

        public static final boolean greaterThanVerticalReefLine(double xPos) {
            return xPos > REEF_CORNER_90_DEG_X;
        }
        public static final boolean greaterThanPositiveReefLine(double xPos, double yPos) {
            return yPos > (xPos * REEF_LINE_POSITIVE_SLOPE + REEF_LINE_POSITIVE_Y_INTERCEPT);
        }
        public static final boolean greaterThanNegativeReefLine(double xPos, double yPos) {
            return yPos > (xPos * REEF_LINE_NEGATIVE_SLOPE + REEF_LINE_NEGATIVE_Y_INTERCEPT);
        }

        public static final double angleToReef(double xPos, double yPos) {
            if(greaterThanVerticalReefLine(xPos) && greaterThanPositiveReefLine(xPos, yPos) && greaterThanNegativeReefLine(xPos, yPos)) {
                return 60;
            }
            else if(!greaterThanVerticalReefLine(xPos) && greaterThanPositiveReefLine(xPos, yPos) && greaterThanNegativeReefLine(xPos, yPos)) {
                return 120;
            }
            else if(!greaterThanVerticalReefLine(xPos) && greaterThanPositiveReefLine(xPos, yPos) && !greaterThanNegativeReefLine(xPos, yPos)) {
                return 180;
            }
            else if(!greaterThanVerticalReefLine(xPos) && !greaterThanPositiveReefLine(xPos, yPos) && !greaterThanNegativeReefLine(xPos, yPos)) {
                return 240;
            }
            else if(greaterThanVerticalReefLine(xPos) && !greaterThanPositiveReefLine(xPos, yPos) && !greaterThanNegativeReefLine(xPos, yPos)) {
                return 300;
            }
            else {
                return 360;
            }
        }

        public enum ReefPost {
            A, B, C, D, E, F, G, H, I, J, K, L
        }

        public static final boolean isRight(ReefPost post) {
            switch (post) {
                case A:
                    return false;
                case B:
                    return true;
                case C:
                    return false;
                case D:
                    return true;
                case E:
                    return false;
                case F:
                    return true;
                case G:
                    return false;
                case H:
                    return true;
                case I:
                    return false;
                case J:
                    return true;
                case K:
                    return false;
                case L:
                    return true;
                default:
                    return false;
            }
        }

        public static final Pose2d getPostPose(boolean isBlue, ReefPost post) {
            if (isBlue) {
                switch (post) {
                    case A:
                        return FIELD.getTagPose(18).get().toPose2d();
                    case B:
                        return FIELD.getTagPose(18).get().toPose2d();
                    case C:
                        return FIELD.getTagPose(17).get().toPose2d();
                    case D:
                        return FIELD.getTagPose(17).get().toPose2d();
                    case E:
                        return FIELD.getTagPose(22).get().toPose2d();
                    case F:
                        return FIELD.getTagPose(22).get().toPose2d();
                    case G:
                        return FIELD.getTagPose(21).get().toPose2d();
                    case H:
                        return FIELD.getTagPose(21).get().toPose2d();
                    case I:
                        return FIELD.getTagPose(20).get().toPose2d();
                    case J:
                        return FIELD.getTagPose(20).get().toPose2d();
                    case K:
                        return FIELD.getTagPose(19).get().toPose2d();
                    case L:
                        return FIELD.getTagPose(19).get().toPose2d();
                    default:
                        return FIELD.getTagPose(18).get().toPose2d();
                }
            }
            switch (post) {
                case A:
                    return FIELD.getTagPose(7).get().toPose2d();
                case B:
                    return FIELD.getTagPose(7).get().toPose2d();
                case C:
                    return FIELD.getTagPose(8).get().toPose2d();
                case D:
                    return FIELD.getTagPose(8).get().toPose2d();
                case E:
                    return FIELD.getTagPose(9).get().toPose2d();
                case F:
                    return FIELD.getTagPose(9).get().toPose2d();
                case G:
                    return FIELD.getTagPose(10).get().toPose2d();
                case H:
                    return FIELD.getTagPose(10).get().toPose2d();
                case I:
                    return FIELD.getTagPose(11).get().toPose2d();
                case J:
                    return FIELD.getTagPose(11).get().toPose2d();
                case K:
                    return FIELD.getTagPose(6).get().toPose2d();
                case L:
                    return FIELD.getTagPose(6).get().toPose2d();
                default:
                    return FIELD.getTagPose(7).get().toPose2d();
            }
        }
    }

    // public static final List<AprilTag> APRIL_TAGS = List.of(
    //     new AprilTag(
    //         1, 
    //         new Pose3d(
    //             new Translation3d(0, 0, 0),
    //             new Rotation3d(0, 0, 0)
    //         )
    //     ),

    //     new AprilTag(
    //         2,
    //         new Pose3d(
    //             new Translation3d(0, 0, 0),
    //             new Rotation3d(0, 0, 0)
    //         )
    //     )
    // );
}
