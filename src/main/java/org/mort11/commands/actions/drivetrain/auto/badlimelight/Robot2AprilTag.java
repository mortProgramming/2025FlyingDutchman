package org.mort11.commands.actions.drivetrain.auto.badlimelight;
// package org.mort11.commands.actions.drivetrain;

// import org.mort11.config.constants.PortConstants.Vision;
// import org.mort11.subsystems.swerve.Drivetrain;
// import edu.wpi.first.math.kinematics.ChassisSpeeds;
// import edu.wpi.first.wpilibj2.command.Command;
// import edu.wpi.first.math.geometry.Pose2d;
// import edu.wpi.first.math.controller.ProfiledPIDController;


// public class Robot2AprilTag extends Command {

//     private Drivetrain drivetrain;
//     private Vision vision;

//     private ProfiledPIDController aprilTagXController;
//     private ProfiledPIDController aprilTagYController;
//     private ProfiledPIDController aprilTagOmegaController;

//     public Robot2AprilTag(){
//         drivetrain = Drivetrain.getInstance();
//         vision = Vision.getInstance();

//         aprilTagXController = new ProfiledPIDController(0.0, 0.0, 0.0, null);
//         aprilTagYController = new ProfiledPIDController(0.0, 0.0, 0.0, null);
//         aprilTagOmegaController = new ProfiledPIDController(0.0, 0.0, 0.0, null);

//         addRequirements(drivetrain);

//     }

//     @Override
//     public void initialize(){
//         aprilTagXController.reset(0);
//         aprilTagYController.reset(0);
//         aprilTagOmegaController.reset(0);
//     }

    

//     @Override
//     public void execute() {
//         //pose of apriltagform limelight
//         Pose2d cameraPose = LimelightHelpers.getCameraPose3d_TargetSpace("limelight").toPose2d();
//         double targetX = cameraPose.getX();
//         double targetY = cameraPose.getY();
        
//             //robot position
//         double targetAngle = Math.atan2(targetY, targetX);
        
//         //robot heading
//         double robotHeading = drivetrain.getGyroscopeRotation().getRadians();
        
//         //rotation speed, align the robot with the tag
//         double omegaSpeed = aprilTagOmegaController.calculate(robotHeading, targetAngle);
        
//         double translationX = -aprilTagXController.calculate(targetX, 0);
//         double translationY = -aprilTagYController.calculate(targetY, 0);
        
        
//         drivetrain.setDrive(new ChassisSpeeds(translationX, translationY, omegaSpeed));
//         System.out.println("limelight target x " + (LimelightHelpers.getTX("limelight")));
//         System.out.println("limelight target y " + (LimelightHelpers.getTY("limelight")));

//     }
        
        
//     @Override
//     public boolean isFinished() {+
//         //apriltag no t visible
//         return false;
//     }

//     @Override
//     public void end(boolean interrupted) {
// 		drivetrain.setDrive(new ChassisSpeeds(0, 0, 0));
//     }
// }
