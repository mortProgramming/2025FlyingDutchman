package org.mort11.library.subsystems.swerve.pathfollower;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.kinematics.ChassisSpeeds;

public class Follower {
    public FollowerConfig config;

    public PIDController longitudinalController, lateralController, rotationalController;
    
    public Follower(FollowerConfig config) {
        this.config = config;

        longitudinalController = new PIDController(config.longitudinal_kp, config.longitudinal_ki, config.longitudinal_kd);
        lateralController = new PIDController(config.lateral_kp, config.lateral_ki, config.lateral_kd);
        rotationalController = new PIDController(config.rotational_kp, config.rotational_ki, config.rotational_kd);

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
        Point wantedPoint = timeToPoint(time, curve);

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
        double timeToAccelerate = config.maxVelocity / config.maxAcceleration;

        if(time < timeToAccelerate) {
            return config.maxAcceleration * time;
        }

        else if(time > (getTotalTime(curve) - timeToAccelerate)) {
            return (getTotalTime(curve) - time) * config.maxAcceleration;
        }

        return config.maxVelocity;
    }

    public double timeToRotation(double time, CubicBezierCurveSpline curve, double endingRotation) {
        return rotationalController.calculate(config.poseSupplier.get().getRotation().getDegrees(), endingRotation);
    }

    public Point timeToPoint(double time, CubicBezierCurveSpline curve) {
        return curve.getPoint(timeToPercent(time, curve));
    }

    public double timeToPercent(double time, CubicBezierCurveSpline curve) {
        return curve.getPercent(config.precision, timeToLength(time, curve));
    }

    public double timeToLength(double time, CubicBezierCurveSpline curve) {
        double timeToAccelerate = config.maxVelocity / config.maxAcceleration;

        double accelerateDistance = config.maxAcceleration * Math.pow(timeToAccelerate, 2) / 2;
        
        double fullSpeedDistance = curve.getLength(config.precision, curve.curves.length) - accelerateDistance * 2;

        if(time < timeToAccelerate) {
            return config.maxAcceleration * Math.pow(time, 2) / 2;
        }

        else if(time > (getTotalTime(curve) - timeToAccelerate)) {
            double timeLeft = getTotalTime(curve) - (timeToAccelerate + fullSpeedDistance / config.maxVelocity);
            return config.maxAcceleration * Math.pow(accelerateDistance, 2) / 2
            + fullSpeedDistance
            + config.maxVelocity * timeLeft - (config.maxAcceleration * Math.pow(timeLeft, 2) / 2);
        }

        return accelerateDistance + time * config.maxVelocity;

    }

    public double getTotalTime(CubicBezierCurveSpline curve) {
        double timeToAccelerate = config.maxVelocity / config.maxAcceleration;

        double accelerateDistance = config.maxAcceleration * Math.pow(timeToAccelerate, 2) / 2;
        
        double fullSpeedDistance = curve.getLength(config.precision, curve.curves.length) - accelerateDistance * 2;

        return (fullSpeedDistance / config.maxVelocity) + timeToAccelerate * 2;
    }
}
