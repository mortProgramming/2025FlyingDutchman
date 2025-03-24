package org.mort11.library.subsystems.swerve.pathfollower;

public class Point {
    public double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /*
     * returns 0 - 2 pi
     */
    public double getAngle() {
        if (y > 0 && x < 0) {
            return Math.atan(y / x) + Math.PI;
        }

        else if (y < 0 && x > 0) {
            return Math.atan(y / x) + 2 * Math.PI;
        }

        else if (y < 0 && x < 0) {
            return Math.atan(y / x) + Math.PI;
        }

        return Math.atan(y / x);
    }

    public double magnitude() {
        return Math.sqrt(x * x + y * y);
    }

    public Point plus(Point otherPoint) {
        return new Point(x + otherPoint.x, y + otherPoint.y);
    }

    public Point minus(Point otherPoint) {
        return new Point(x - otherPoint.x, y - otherPoint.y);
    }



    public static double getDistance(Point start, Point end) {
        return Math.sqrt(Math.pow(end.x - start.x, 2) + Math.pow(end.y - start.y, 2));
    }
}
