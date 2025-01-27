package org.mort11.library.hardware.brands.rev;

// import com.revrobotics.CANSparkMax;
// import com.revrobotics.CANSparkLowLevel;
// import com.revrobotics.RelativeEncoder;

public class RevUtility {

    public static final class NEO {
        public static final double SWERVE_STEER_KP = 6.264;
        public static final double SWERVE_STEER_KI = 0;
        public static final double SWERVE_STEER_KD = 0.6264;

        public static final double MAX_RPM = 5676;

    }

    public static final class NEO550 {
        public static final double SWERVE_STEER_KP = 6.264;
        public static final double SWERVE_STEER_KI = 0;
        public static final double SWERVE_STEER_KD = 0.6264;

        public static final double MAX_RPM = 11000;

    }

    public static final class Vortex {
        public static final double SWERVE_STEER_KP = 6.264;
        public static final double SWERVE_STEER_KI = 0;
        public static final double SWERVE_STEER_KD = 0.6264;

        public static final double MAX_RPM = 6784;

    }
}
