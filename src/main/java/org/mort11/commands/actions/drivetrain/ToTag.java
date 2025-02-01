package org.mort11.commands.actions.drivetrain;

import org.mort11.subsystems.Drivetrain;
import org.mort11.subsystems.Vision;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Command;

public class ToTag  extends Command {

    private Drivetrain drivetrain;
    private Vision vision;

    public int tagNumber;
    
    public ToTag (int tagNumber) {
        this.tagNumber = tagNumber;

        drivetrain = Drivetrain.getInstance();
        vision = Vision.getInstance();
    }

    public void execute () {
        drivetrain.setDrive(
            new ChassisSpeeds(
                drivetrain.getXController().calculate(
                    vision.getTagToRobotPose().getX(), 0.4
                ), 
                drivetrain.getYController().calculate(
                    vision.getTagToRobotPose().getY(), 0.4
                ),
                drivetrain.getRotateController().calculate(
                    vision.getTagToRobotPose().getRotation().getDegrees(), 0
                )
            )
        );
    }

    public boolean isFinished () {
        return false;
    }

    public void end(boolean interrupted) {
        drivetrain.setDrive(
            new ChassisSpeeds(0, 0, 0)
        );
    }
}
