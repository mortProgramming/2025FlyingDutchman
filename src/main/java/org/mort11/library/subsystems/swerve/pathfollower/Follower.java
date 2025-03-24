package org.mort11.library.subsystems.swerve.pathfollower;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;

public class Follower {
    public FollowerConfig config;

    public PIDController longitudinalController, lateralController, rotationalController;
    
    public Follower(FollowerConfig config) {
        this.config = config;

        longitudinalController = new PIDController(0, 0, 0);
        lateralController = new PIDController(0, 0, 0);
        rotationalController = new PIDController(0, 0, 0);

        rotationalController.enableContinuousInput(-180, 180);
    }

    public void setSpeed(double time, CubicBezierCurveSpline curve, double endingRotation) {
        double movementAngle = curve.getDirection(config.precision, timeToPercent(time, curve));
        double x = timeToSpeed(time, curve) *  Math.cos(movementAngle);
        double y = timeToSpeed(time, curve) *  Math.sin(movementAngle);

        config.speedConsumer.accept(
            ChassisSpeeds.fromFieldRelativeSpeeds(
                timeToSpeed(time, curve) *  Math.cos(movementAngle) +
                controllerHelp(time, curve).x, 

                timeToSpeed(time, curve) *  Math.sin(movementAngle) +
                controllerHelp(time, curve).y, 

                timeToRotation(time, curve, endingRotation), 
                config.poseSupplier.get().getRotation()
            )
        );
    }

    public Point controllerHelp(double time, CubicBezierCurveSpline curve) {
        Point wantedPoint = new Point(timeToPose(time, curve).getTranslation() .getX(), timeToPose(time, curve).getY());

        double movementAngle = curve.getDirection(config.precision, timeToPercent(time, curve));

        double cos = Math.cos(movementAngle);
        double sin = Math.sin(movementAngle);

        double t = (config.poseSupplier.get().getX() - wantedPoint.x) * cos + (config.poseSupplier.get().getY() - wantedPoint.y) * sin;

        // Compute closest point on the line
        double xC = wantedPoint.x + t * cos;
        double yC = wantedPoint.y + t * sin;

        // Compute shortest distance
        double lateralDistance = Math.sqrt(Math.pow(xC - config.poseSupplier.get().getX(), 2) + Math.pow(yC - config.poseSupplier.get().getY(), 2));
        double longitudinalDistance = Math.sqrt(Math.pow(wantedPoint.x - xC, 2) + Math.pow(wantedPoint.y - yC, 2));

        double lateralSpeed = lateralController.calculate(lateralDistance, 0);
        double longitudinalSpeed = longitudinalController.calculate(longitudinalDistance, 0);

        return new Point(
            cos * longitudinalSpeed + sin * lateralSpeed, 
            sin * longitudinalSpeed + cos * lateralSpeed
        );
    }

    public double timeToSpeed(double time, CubicBezierCurveSpline curve) {
        return 0;
    }

    public double timeToRotation(double time, CubicBezierCurveSpline curve, double endingRotation) {
        return rotationalController.calculate(config.poseSupplier.get().getRotation().getDegrees(), endingRotation);
    }

    public Pose2d timeToPose(double time, CubicBezierCurveSpline curve) {
        return new Pose2d();
    }

    public double timeToPercent(double time, CubicBezierCurveSpline curve) {
        return 0;
    }
}
