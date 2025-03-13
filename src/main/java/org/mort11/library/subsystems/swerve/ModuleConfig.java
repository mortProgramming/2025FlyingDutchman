package org.mort11.library.subsystems.swerve;

public class ModuleConfig {

    public static final class MK4_L1 implements ModuleConfigIntf {
        public static final double WHEEL_DIAMETER = 0.10033;

        public static final double DRIVE_REDUCTION = (14.0 / 50.0) * (25.0 / 19.0) * (15.0 / 45.0);

        public static final double STEER_REDUCTION = (15.0 / 32.0) * (10.0 / 60.0);

        public static final boolean DRIVE_DIRECTION_FLIP = false;

        public static final boolean STEER_DIRECTION_FLIP = false;
    }

    public static final class MK4_L2 implements ModuleConfigIntf {
        public static final double WHEEL_DIAMETER = 0.10033;

        public static final double DRIVE_REDUCTION = (14.0 / 50.0) * (27.0 / 17.0) * (15.0 / 45.0);

        public static final double STEER_REDUCTION = (15.0 /32.0) * (10.0 / 60.0);

        public static final boolean DRIVE_DIRECTION_FLIP = false;

        public static final boolean STEER_DIRECTION_FLIP = false;
    }

    public static final class MK4_L3 implements ModuleConfigIntf {
        public static final double WHEEL_DIAMETER = 0.10033;

        public static final double DRIVE_REDUCTION = (14.0 / 50.0) * (28.0 / 16.0) * (15.0 / 45.0);

        public static final double STEER_REDUCTION = (15.0 / 32.0) * (10.0 / 60.0);

        public static final boolean DRIVE_DIRECTION_FLIP = false;

        public static final boolean STEER_DIRECTION_FLIP = false;
    }

    public static final class MK4_L4 implements ModuleConfigIntf {
        public static final double WHEEL_DIAMETER = 0.10033;

        public static final double DRIVE_REDUCTION = (16.0 / 48.0) * (28.0 / 16.0) * (15.0 / 45.0);

        public static final double STEER_REDUCTION = (15.0 / 32.0) * (10.0 / 60.0);

        public static final boolean DRIVE_DIRECTION_FLIP = false;

        public static final boolean STEER_DIRECTION_FLIP = false;

    }

    public static final class MK4i_L1 implements ModuleConfigIntf {
        public static final double WHEEL_DIAMETER = 0.10033;

        public static final double DRIVE_REDUCTION = (14.0 / 50.0) * (25.0 / 19.0) * (15.0 / 45.0);

        public static final double STEER_REDUCTION = (14.0 / 50.0) * (10.0 / 60.0);

        public static final boolean DRIVE_DIRECTION_FLIP = false;

        public static final boolean STEER_DIRECTION_FLIP = true;
    }

    public static final class MK4i_L2 implements ModuleConfigIntf {
        public static final double WHEEL_DIAMETER = 0.10033;

        public static final double DRIVE_REDUCTION = (14.0 / 50.0) * (27.0 / 17.0) * (15.0 / 45.0);

        public static final double STEER_REDUCTION = (14.0 / 50.0) * (10.0 / 60.0);

        public static final boolean DRIVE_DIRECTION_FLIP = false;

        public static final boolean STEER_DIRECTION_FLIP = true;
    }
    
    public static final class MK4i_L3 implements ModuleConfigIntf {
        public static final double WHEEL_DIAMETER = 0.10033;

        public static final double DRIVE_REDUCTION = (14.0 / 50.0) * (28.0 / 16.0) * (15.0 / 45.0);

        public static final double STEER_REDUCTION = (14.0 / 50.0) * (10.0 / 60.0);

        public static final boolean DRIVE_DIRECTION_FLIP = false;

        public static final boolean STEER_DIRECTION_FLIP = true;
    }

    public static final class MK4i_L3Plus implements ModuleConfigIntf {
        public static final double WHEEL_DIAMETER = 0.10033;

        public static final double DRIVE_REDUCTION = (16.0 / 50.0) * (28.0 / 16.0) * (15.0 / 45.0);

        public static final double STEER_REDUCTION = (16.0 / 50.0) * (10.0 / 60.0);

        public static final boolean DRIVE_DIRECTION_FLIP = false;

        public static final boolean STEER_DIRECTION_FLIP = true;
    }
}
