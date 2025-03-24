package org.mort11.library.subsystems.swerve.pathfollower;

public class CubicBezierCurveSpline {
    public CubicBezierCurve[] curves;

    public CubicBezierCurveSpline(CubicBezierCurve... curves) {
        this.curves = curves;
    }

    public Point getPoint(double percent) {
        return curves[(int) percent].getPoint(percent - Math.floor(percent));
    }

    public double getLength(int precision, double percent) {
        double length = 0;
        for(int i = 0; i < Math.floor(percent); i++) {
            length += curves[i].getLength(precision, 1);
        }
        length += curves[(int) Math.ceil(percent)].getLength(precision, percent - Math.floor(percent));
        return length;
    }

    public double getDirection(int precision, double percent) {
        return curves[(int) percent].getDirection(precision, percent - Math.floor(percent));
    }

    public double getPercent(int precision, double length) {
        int curveNum = 0;
        for (int i = 0; length < getLength(precision, i); i++) {
            curveNum = i;
        }

        double previousCurveLength = 0;

        for (int i = 0; i <= curveNum; i++) {
            previousCurveLength += getLength(precision, 1);
        }

        double finalCurvePercent = curves[curveNum].getPercent(precision, length - previousCurveLength);

        return curveNum + finalCurvePercent;
    }
}
