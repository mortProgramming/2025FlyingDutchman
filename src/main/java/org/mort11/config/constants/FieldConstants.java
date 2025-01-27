package org.mort11.config.constants;

import java.util.List;

import edu.wpi.first.apriltag.AprilTag;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Translation3d;

public final class FieldConstants {
    public static final double FIELD_LENGTH = 3; //16.541
    public static final double FIELD_WIDTH = 3; //8.211

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
    }

    public static final List<AprilTag> APRIL_TAGS = List.of(
        new AprilTag(
            1, 
            new Pose3d(
                new Translation3d(0, 0, 0),
                new Rotation3d(0, 0, 0)
            )
        ),

        new AprilTag(
            2, 
            new Pose3d(
                new Translation3d(0, 0, 0),
                new Rotation3d(0, 0, 0)
            )
        )
    );
}
