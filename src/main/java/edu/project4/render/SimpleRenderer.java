package edu.project4.render;

import edu.project4.colors.Color;
import edu.project4.coordinates.Point;
import edu.project4.coordinates.Rect;
import edu.project4.fractal.FractalImage;
import edu.project4.fractal.Pixel;
import edu.project4.processing.LinearFunction;
import edu.project4.processing.Transformation;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class SimpleRenderer implements Renderer {
    private static final Color[] COLORS = new Color[] {
        new Color(0, 0, 0),
        new Color(0, 0, 255),
        new Color(0, 255, 0),
        new Color(255, 0, 0),
        new Color(255, 0, 255),
        new Color(255, 255, 0),
        new Color(0, 255, 255),
        new Color(128, 0, 255),
        new Color(128, 255, 0),
        new Color(0, 128, 255),
        new Color(255, 128, 0),
        new Color(0, 255, 128),
        new Color(255, 0, 128)
    };

    @Override
    public FractalImage render(FractalImage canvas, Rect world,
        List<LinearFunction> affineTransformations, Transformation variation,
        int samples, short perSampleIterations, int symmetry) {

        for (int i = 0; i < samples; i++) {
            Point currentPoint = getRandomPointFromRect(world);

            for (short step = 0; step < perSampleIterations; step++) {
                LinearFunction affine = getRandomAffineTransformation(affineTransformations);
                currentPoint = transformPoint(currentPoint, affine, variation);
                // TODO: affine FINAL transformation

                double theta = 0.0;
                for (int s = 0; s < symmetry; s++) {
                    Point pointRotated = rotatePoint(currentPoint, theta);
                    theta += 2 * PI / symmetry;

                    if (!world.contains(pointRotated)) {
                        continue;
                    }

                    // TODO: лок на пиксель во время работы
                    Pixel pixel = mapPointToPixel(canvas, world, pointRotated);
                    if (pixel == null) {
                        continue;
                    }
                    if (pixel.hitCount() == 0) {
                        ThreadLocalRandom random = ThreadLocalRandom.current();
                        pixel.setColor(COLORS[random.nextInt(0, COLORS.length)]);
                    } else {
                        pixel.mixColor(affine.color());
                    }
                    pixel.hit();
                }
            }
        }

        // TODO: сделать фабрику
        return canvas;
    }

    private Point getRandomPointFromRect(Rect world) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        double x = random.nextDouble(world.x(), world.x() + world.width());
        double y = random.nextDouble(world.y(), world.y() + world.height());
        return new Point(x, y);
    }

    private LinearFunction getRandomAffineTransformation(List<LinearFunction> affineTransformations) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        double p = random.nextDouble();
        double offset = 0.0;
        for (LinearFunction affine : affineTransformations) {
            if (p < affine.probability() + offset) {
                return affine;
            }
            offset += affine.probability();
        }
        return affineTransformations.get(0);
    }

    private Point transformPoint(Point currentPoint, LinearFunction affineTransformation, Transformation variation) {
        Point linearTransform = affineTransformation.transformation().apply(currentPoint);
        Point nonLinearTransform = variation.apply(linearTransform);
        // TODO: affine post transformation
        return nonLinearTransform;
    }

    private Point rotatePoint(Point currentPoint, double theta) {
        double x = currentPoint.x() * cos(theta) - currentPoint.y() * sin(theta);
        double y = currentPoint.x() * sin(theta) + currentPoint.y() * cos(theta);
        return new Point(x, y);
    }

    private Pixel mapPointToPixel(FractalImage canvas, Rect world, Point point) {
        if (!world.contains(point)) {
            return null;
        }

        int pixelX = (int) ((point.x() + 1) * canvas.width() / 2);
        int pixelY = (int) ((point.y() + 1) * canvas.height() / 2);

        if (!canvas.contains(pixelX, pixelY)) {
            return null;
        }

        return canvas.pixel(pixelX, pixelY);
    }
}
