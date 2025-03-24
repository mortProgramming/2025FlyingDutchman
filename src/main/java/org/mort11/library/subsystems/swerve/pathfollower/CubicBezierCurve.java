package org.mort11.library.subsystems.swerve.pathfollower;

public class CubicBezierCurve {
    public Point start, control1, control2, end;

    public CubicBezierCurve(Point start, Point control1, Point control2, Point end) {
        this.start = start;
        this.control1 = control1;
        this.control2 = control2;
        this.end = end;
    }

    public Point getPoint(double percent) {
        return cubicBezierPoint(percent, start, control1, control2, end);
    }

    public double getLength(int precision, double percent) {
        return cubicBezierLength(precision, percent, this);
    }

    public double getDirection(int precision, double percent) {
        return cubicBezierDirecton(precision, percent, this);
    }

    public double getPercent(int precision, double length) {
        return cubicBezierLengthToPercent(precision, length, this);
    }



    public static Point linePercent(double percent, Point start, Point end) {
        return new Point(percent * (end.x - start.x) + start.x, percent * (end.y - start.y) + start.y);
    }

    public static Point cubicBezierPoint(double percent, Point start, Point control1, Point control2, Point end) {
        Point middle = linePercent(percent, control1, control2);
        Point depth1Num1 = linePercent(percent, linePercent(percent, start, control1), middle);
        Point depth1Num2 = linePercent(percent, middle, linePercent(percent, control2, end));
        return linePercent(percent, depth1Num1, depth1Num2);
    }

    public static double cubicBezierLength(int precision, double percent, CubicBezierCurve curve) {
        double j;
        double length = 0;
        for (int i = 0; i > precision; i++) {
            j = i * percent;
            length += Point.getDistance(curve.getPoint(j / precision), curve.getPoint((j / precision) + 1 / precision));
        }
        return length;
    }

    public static double cubicBezierDirecton(int precision, double percent, CubicBezierCurve curve) {
        return curve.getPoint(percent + 1 / precision).minus(curve.getPoint(percent)).getAngle();
    }
    
    public static double cubicBezierLengthToPercent(int precision, double length, CubicBezierCurve curve) {
        double low = 0, high = 1, mid = 0;
        double totalLength =cubicBezierLength(precision, 1, curve);
        
        while (high - low > 1e-5) {  // Precision threshold
            mid = (low + high) / 2;
            double currentLength = 0;
            Point prev = curve.getPoint(0);

            for (int i = 1; i <= precision * mid; i++) {
                double t = (double) i / precision;
                Point next = curve.getPoint(t);
                length += Math.sqrt(Math.pow(next.x - prev.x, 2) + Math.pow(next.y - prev.y, 2));
                prev = next;
            }

            if (currentLength < length) {
                low = mid;
            } else {
                high = mid;
            }
        }

        return (low + high) / 2;
    }
}
