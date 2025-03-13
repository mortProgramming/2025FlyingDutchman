package org.mort11.commands.actions.drivetrain.teleop;

import java.util.function.DoubleSupplier;

import org.mort11.Utility;
import org.mort11.subsystems.swerve.Drivetrain;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Command;

public class SnapToReef extends Command{
    private Drivetrain drivetrain;

    private DoubleSupplier x;
    private DoubleSupplier y;

    private double snapAngle;

    public SnapToReef(DoubleSupplier translationX, DoubleSupplier translationY){
        drivetrain = Drivetrain.getInstance();

        this.x = translationX;
        this.y = translationY;
        
        addRequirements(drivetrain);
    }

    @Override
    public void initialize() {
	  drivetrain.getRotateController().reset(drivetrain.getRotation2d().getDegrees());
    }

    @Override
    public void execute(){
        double currentRotation = drivetrain.getRotation2d().getDegrees() - 30;

        if(currentRotation >= -30 && currentRotation < 30) {
            snapAngle = 0;
        }

        else if(currentRotation >= 30 && currentRotation < 90) {
            snapAngle = 60;
        }

        else if(currentRotation >= 90 && currentRotation < 150) {
            snapAngle = 120;
        }

        else if(currentRotation >= 150 || currentRotation < -150) {
            snapAngle = 180;
        }

        else if(currentRotation >= -150 && currentRotation < -90) {
            snapAngle = -120;
        }

        else {
            snapAngle = -60;
        }

        // drivetrain.setDrive(
        //     ChassisSpeeds.fromFieldRelativeSpeeds(
        //         x.getAsDouble(),
        //         y.getAsDouble(), 
        //         drivetrain.calculateRotateController(snapAngle),
        //         drivetrain.getRotation2d()
        //     ).times(0.15)
        // );

        drivetrain.setDrive(
            ChassisSpeeds.fromFieldRelativeSpeeds(
                x.getAsDouble() * 0.106,
                y.getAsDouble() * 0.106, 
                -Utility.clamp(drivetrain.calculateRotateController(snapAngle), 4),
                drivetrain.getRotation2d()
            )
        );
    }

    @Override
    public void end(boolean interrupted){
        drivetrain.setDrive(new ChassisSpeeds(0,0,0));
    }

    @Override
    public boolean isFinished(){
        return false;
    }   
}
