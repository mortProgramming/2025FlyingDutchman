package org.mort11.commands.actions.drivetrain.teleop;

import org.mort11.subsystems.swerve.Drivetrain;
import java.util.function.DoubleSupplier;
import org.mort11.subsystems.Vision;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Command;

public class DriveAtAngle extends Command{

    //declare private drivetrain instance
    private Drivetrain drivetrain;

    private DoubleSupplier x;
    private DoubleSupplier y;
    private DoubleSupplier wantedAngle;

    //variable stores wanted angle
    

    //initializes command with wanted angle
    public DriveAtAngle(DoubleSupplier translationX, DoubleSupplier translationY, double wantedAngle){

        //gets singleton instance of drivetrain
        drivetrain = Drivetrain.getInstance();

        //sets the wanted angle
        this.x = translationX;
        this.y = translationY;
        this.wantedAngle = () -> wantedAngle;
        
        addRequirements(drivetrain);
    }

    public DriveAtAngle(DoubleSupplier translationX, DoubleSupplier translationY, DoubleSupplier wantedAngle){

        //gets singleton instance of drivetrain
        drivetrain = Drivetrain.getInstance();
        //sets the wanted angle
        this.x = translationX;
        this.y = translationY;
        this.wantedAngle = wantedAngle;
        
        addRequirements(drivetrain);
    }

    //executes the command
    @Override
    public void execute(){
        //gets yaw angle tx from the limelight
        drivetrain.setDrive(
            ChassisSpeeds.fromFieldRelativeSpeeds(
                x.getAsDouble(),
                y.getAsDouble(), 
                drivetrain.calculateRotateController(wantedAngle.getAsDouble()),
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
