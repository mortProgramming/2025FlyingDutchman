package org.mort11.library.hardware.brands.ctre;

public class CTREUtility {

    public static final class Falcon500 {
        public static final double SWERVE_STEER_KP = 1.26;
        public static final double SWERVE_STEER_KI = 0;
        public static final double SWERVE_STEER_KD = 0.063;

        public static final double MAX_RPM = 6380;
    }

    public static final class Krakenx60 {
        public static final double SWERVE_STEER_KP = 15;
        public static final double SWERVE_STEER_KI = 0;
        public static final double SWERVE_STEER_KD = 0;

        public static final double MAX_RPM = 6000;
    }
}
