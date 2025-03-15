package org.mort11.commands.actions.drivetrain.teleop;

import org.mort11.subsystems.swerve.Drivetrain;
import java.util.function.DoubleSupplier;
import org.mort11.subsystems.Vision;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Command;

public class DriveTagAngled extends Command{

    //declare private drivetrain instance
    private Drivetrain drivetrain;
    private Vision vision; 

    private DoubleSupplier x;
    private DoubleSupplier y;
    private DoubleSupplier theta;

    //variable stores wanted angle

    //initializes command with wanted angle
    public DriveTagAngled(DoubleSupplier translationX, DoubleSupplier translationY, DoubleSupplier theta){

        drivetrain = Drivetrain.getInstance();
        vision = Vision.getInstance();

        //sets the wanted angle
        this.x = translationX;
        this.y = translationY;
        this.theta = theta;
        
        addRequirements(drivetrain, vision);
    }

    //executes the command
    @Override
    public void execute(){
        //gets yaw angle tx from the limelight

        double wantedSpeed = 0;

        if(vision.getId() == -1) {
            wantedSpeed = theta.getAsDouble();
        }   else {
            drivetrain.calculateRotateController(
                vision.getFieldTagPose(
                    vision.getId()
                ).getRotation().getDegrees()
            );
        }

        drivetrain.setDrive(
            ChassisSpeeds.fromFieldRelativeSpeeds(
                x.getAsDouble(),
                y.getAsDouble(), 
                wantedSpeed,
                drivetrain.getRotation2d()
            ).times(0.15)
        );
    }

    @Override
    public void end(boolean interrupted){
        drivetrain.setDrive(new ChassisSpeeds(0,0,0));
    }

    @Override
    public boolean isFinished(){
        // return drivetrain.getRotateController().atSetpoint();
        return false;

    }   
}
