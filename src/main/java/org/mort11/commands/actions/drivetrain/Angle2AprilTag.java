package org.mort11.commands.actions.drivetrain;

import org.mort11.subsystems.Drivetrain;
import org.mort11.subsystems.Vision;


import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Command;

public class Angle2AprilTag extends Command{

    //declare private drivetrain instance
    private Drivetrain drivetrain;
    private Vision vision; 
    //variable stores wanted angle
    

    //initializes command with wanted angle
    public Angle2AprilTag(double wantedAngle){

        //gets singleton instance of drivetrain
        drivetrain = Drivetrain.getInstance();
        vision = Vision.getInstance();
        //sets the wanted angle
        
        addRequirements(drivetrain, vision);

    }

    //executes the command
    @Override
    public void execute(){
        //gets yaw angle tx from the limelight
        double wantedAngle = vision.getFrontCamera().getPicturePosition()[0];

        //uses the yaw angle to rotate to wanted angle
        drivetrain.setAngle2Controller(wantedAngle);
        System.out.println(wantedAngle);
    }
    @Override
    public void end(boolean interrupted){
        drivetrain.setDrive(new ChassisSpeeds(0,0,0));
    }

    @Override
    public boolean isFinished(){
        return drivetrain.getRotateController().atSetpoint();
    }   
}
