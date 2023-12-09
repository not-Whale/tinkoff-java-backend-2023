package edu.project4.processing;

import edu.project4.coordinates.Point;

public class LinearTransformations {
    private LinearTransformations() {}

    public Transformation create(double a, double b, double c, double d, double e, double f) {
        return (Point p) -> {
            double newX = a * p.x() + b * p.y() + c;
            double newY = d * p.x() + e * p.y() + f;
            return new Point(newX, newY);
        };
    }
}
