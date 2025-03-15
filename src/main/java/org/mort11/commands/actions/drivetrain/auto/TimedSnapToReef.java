package org.mort11.commands.actions.drivetrain.auto;

import java.util.function.DoubleSupplier;

import org.mort11.Utility;
import org.mort11.subsystems.swerve.Drivetrain;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.trajectory.TrapezoidProfile.Constraints;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import static org.mort11.config.constants.PIDConstants.Drivetrain.ANGLE_CONSTRAINTS;
import static org.mort11.config.constants.PIDConstants.Drivetrain.POS_CONSTRAINTS;

public class TimedSnapToReef extends Command{
    private Drivetrain drivetrain;

    private Timer timer;
    private double time, x, y;

    private double snapAngle;

    public TimedSnapToReef(double time, double x, double y){
        drivetrain = Drivetrain.getInstance();

        timer = new Timer();
        this.time = time;
        this.x = x;
        this.y = y;
        
        addRequirements(drivetrain);
    }

    @Override
    public void initialize() {
        timer.reset();
        timer.start();
	  drivetrain.getRotateController().reset(drivetrain.getRotation2d().getDegrees());

    drivetrain.getRotateController().setConstraints(new Constraints(100, ANGLE_CONSTRAINTS.maxAcceleration));
    }

    @Override
    public void execute(){
        double currentRotation = drivetrain.getRotation2d().getDegrees();

        if(currentRotation >= 60 && currentRotation < 120) {
            snapAngle = 90;
        }

        else if(currentRotation >= 0 && currentRotation < 60) {
            snapAngle = 30;
        }

        else if(currentRotation >= -60 && currentRotation < 0) {
            snapAngle = -30;
        }

        else if(currentRotation >= -120 && currentRotation < -60) {
            snapAngle = -90;
        }

        else if(currentRotation >= -150 && currentRotation < -180) {
            snapAngle = -120;
        }

        else {
            snapAngle = 150;
        }

        drivetrain.setDrive(
            new ChassisSpeeds(
                x,
                y, 
                Utility.clamp(drivetrain.calculateRotateController(snapAngle), 6)
            )
        );
    }

    @Override
    public void end(boolean interrupted){
        drivetrain.setDrive(new ChassisSpeeds(0,0,0));
    }

    @Override
  public boolean isFinished() {
    return timer.get() > time;
  }
}
