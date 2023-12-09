package edu.project4.processing;

import edu.project4.coordinates.Point;
import static java.lang.Math.PI;
import static java.lang.Math.atan;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

public class NonLinearFunctions {
    private NonLinearFunctions() {}

    public static Transformation sinusoidal() {
        return (Point p) -> {
            double newX = sin(p.x());
            double newY = sin(p.y());
            return new Point(newX, newY);
        };
    }

    public static Transformation spherical() {
        return (Point p) -> {
            double r2 = p.x() * p.x() + p.y() * p.y();
            double coeff = 1 / r2;
            double newX = coeff * p.x();
            double newY = coeff * p.y();
            return new Point(newX,  newY);
        };
    }

    public static Transformation swirl() {
        return (Point p) -> {
            double r2 = p.x() * p.x() + p.y() * p.y();
            double newX = p.x() * sin(r2) - p.y() * cos(r2);
            double newY = p.x() * cos(r2) + p.y() * sin(r2);
            return new Point(newX, newY);
        };
    }

    public static Transformation horseshoe() {
        return (Point p) -> {
            double r = sqrt(p.x() * p.x() + p.y() * p.y());
            double coeff = 1 / r;
            double newX = coeff * (p.x() - p.y()) * (p.x() + p.y());
            double newY = coeff * 2 * p.x() * p.y();
            return new Point(newX, newY);
        };
    }

    public static Transformation polar() {
        return (Point p) -> {
            double r = sqrt(p.x() * p.x() + p.y() * p.y());
            double coeff = p.y() / p.x();
            double newX = atan(coeff) / PI;
            double newY = r - 1;
            return new Point(newX, newY);
        };
    }

    public static Transformation handkerchief() {
        return (Point p) -> {
            double r = sqrt(p.x() * p.x() + p.y() * p.y());
            double coeff = p.y() / p.x();
            double newX = r * sin(atan(coeff) + r);
            double newY = r * cos(atan(coeff) - r);
            return new Point(newX, newY);
        };
    }

    public static Transformation heart() {
        return (Point p) -> {
            double r = sqrt(p.x() * p.x() + p.y() * p.y());
            double coeff = p.y() / p.x();
            double newX = r * sin(r * atan(coeff));
            double newY = -r * cos(r * atan(coeff));
            return new Point(newX, newY);
        };
    }

    public static Transformation disk() {
        return (Point p) -> {
            double r = sqrt(p.x() * p.x() + p.y() * p.y());
            double coeff = p.y() / p.x();
            double piCoeff = 1 / PI;
            double newX = piCoeff * atan(coeff) * sin(PI * r);
            double newY = piCoeff * atan(coeff) * cos(PI * r);
            return new Point(newX, newY);
        };
    }

    public static Transformation spiral() {
        return (Point p) -> {
            double r = sqrt(p.x() * p.x() + p.y() * p.y());
            double rCoeff = 1 / r;
            double coeff = p.y() / p.x();
            double newX = rCoeff * (cos(atan(coeff)) + sin(r));
            double newY = rCoeff * (sin(atan(coeff)) - cos(r));
            return new Point(newX, newY);
        };
    }

    public static Transformation pdj(double p1, double p2, double p3, double p4) {
        return (Point p) -> {
            double newX = sin(p1 * p.y()) - cos(p2 * p.x());
            double newY = sin(p3 * p.x()) - cos(p4 * p.y());
            return new Point(newX, newY);
        };
    }
}
