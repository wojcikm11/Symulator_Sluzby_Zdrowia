package simulator.math2D;

import javafx.geometry.Point2D;

public class Math2D {
    public static double vectorProductValue(Point2D p1, Point2D p2, Point2D o1, Point2D o2) {
        return ((p2.getY() - p1.getY()) * (o2.getX() - o1.getX())) -
                (p2.getX() - p1.getX()) * (o2.getY() - o1.getY());
    }
    public static int orientation(Point2D p, Point2D q, Point2D r) {
        double ratio = vectorProductValue(p, q, q, r);

        if (ratio == 0)
            return 0;
        return (ratio > 0) ? 1 : 2;
    }

    public static double squareOfDistance(Point2D p1, Point2D p2)
    {
        return (p1.getX() - p2.getX()) * (p1.getX() - p2.getX()) +
                (p1.getY() - p2.getY()) * (p1.getY() - p2.getY());
    }

    public static double calculateEuclideanLength(Point2D p1, Point2D p2) {
        return Math.sqrt(squareOfDistance(p1, p2));
    }

    public static Point2D findIntersectionPoint(Point2D A, Point2D B, Point2D C, Point2D D) {

        if (A.equals(C) || A.equals(D) || B.equals(C) || B.equals(D))
            return null;

        double[] interval1 = {Math.min(A.getX(), B.getX()), Math.max(A.getX(), B.getX())};
        double[] interval2 = {Math.min(C.getX(), D.getX()), Math.max(C.getX(), D.getX())};

        double[] intersectionXVector = {Math.max(interval1[0], interval2[0]),
                                         Math.min(interval1[1], interval2[1])};

        double a1 = calculateAFactor(A, B);
        double a2 = calculateAFactor(C, D);
        double b1 = calculateBFactor(A, a1);
        double b2 = calculateBFactor(C, a2);
        double intersectionX;

        if (a1 == a2) {
            return null;
        } else if (a1 == Double.MAX_VALUE) {
            intersectionX = A.getX();
            if (intersectionXVector[0] == intersectionX && intersectionXVector[1] == intersectionX && intersectionX != C.getX() && intersectionX != D.getX())
                return new Point2D(intersectionX, a2 * intersectionX + b2);
        } else if (a2 == Double.MAX_VALUE) {
            intersectionX = C.getX();
            if (intersectionXVector[0] == intersectionX && intersectionXVector[1] == intersectionX && intersectionX != A.getX() && intersectionX != B.getX())
                return new Point2D(intersectionX, a1 * intersectionX + b1);
        } else {
            intersectionX = (b2 - b1) / (a1 - a2);
            if(intersectionX > intersectionXVector[0] && intersectionX < intersectionXVector[1])
                return new Point2D(intersectionX, a1 * intersectionX + b1);
        }
        return null;
    }

    private static double calculateAFactor(Point2D p1, Point2D p2) {
        if(p2.getX() == p1.getX())
            return Double.MAX_VALUE;
        else
            return (p2.getY() - p1.getY()) / (p2.getX() - p1.getX());
    }

    private static double calculateBFactor(Point2D point, double a) {
        return point.getY() - a * point.getX();
    }
}
