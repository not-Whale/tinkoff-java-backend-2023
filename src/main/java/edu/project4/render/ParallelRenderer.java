package edu.project4.render;

import edu.project4.coordinates.Point;
import edu.project4.coordinates.Rect;
import edu.project4.fractal.FractalImage;
import edu.project4.fractal.Pixel;
import edu.project4.processing.LinearFunction;
import edu.project4.processing.Transformation;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

@SuppressWarnings("ParameterNumber")
public class ParallelRenderer implements Renderer {
    private final int coreNumber;

    public ParallelRenderer(int coreNumber) {
        this.coreNumber = coreNumber;
    }

    @Override
    public void render(FractalImage canvas, Rect world,
        List<LinearFunction> affineTransformations, List<Transformation> variations, LinearFunction finalAffine,
        int samples, int perSampleIterations, int symmetry) {

        try (ExecutorService executorService = Executors.newCachedThreadPool()) {
            int samplesPerThread = samples / coreNumber;
            List<Callable<Void>> tasks = new ArrayList<>();
            for (int i = 0; i < coreNumber; i++) {
                double splitSize = world.width() / coreNumber;
                double startPoint = i * (splitSize) + world.x();
                Rect threadWorld = new Rect(startPoint, 0, splitSize, world.height());
                Callable<Void> splitTask = getSplitTask(canvas, threadWorld, world,
                    affineTransformations, variations, finalAffine,
                    samplesPerThread, perSampleIterations, symmetry
                );
                tasks.add(splitTask);
            }
            var futures = executorService.invokeAll(tasks);
            for (var future : futures) {
                future.get();
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private Callable<Void> getSplitTask(FractalImage canvas, Rect threadWorld, Rect world,
        List<LinearFunction> affineTransformations, List<Transformation> variations, LinearFunction finalAffine,
        int samplesPerThread, int perSampleIterations, int symmetry) {
        return () -> {
            for (int num = 0; num < samplesPerThread; num++) {
                Point currentPoint = getRandomPointFromRect(threadWorld);

                for (int step = 0; step < perSampleIterations; step++) {
                    LinearFunction affine = getRandomAffineTransformation(affineTransformations);
                    Transformation variation = getRandomVariation(variations);
                    currentPoint = transformPoint(currentPoint, affine, variation, finalAffine);

                    double theta = 0.0;
                    for (int s = 0; s < symmetry; s++) {
                        Point pointRotated = rotatePoint(currentPoint, theta);
                        theta += 2 * PI / symmetry;

                        if (!world.contains(pointRotated)) {
                            continue;
                        }

                        Pixel pixel = mapPointToPixel(canvas, world, pointRotated);
                        if (pixel == null) {
                            continue;
                        }
                        pixel.lock.lock();
                        pixel.mixColor(affine.color());
                        pixel.hit();
                        pixel.lock.unlock();
                    }
                }
            }
            return null;
        };
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

    private Transformation getRandomVariation(List<Transformation> variations) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        return variations.get(random.nextInt(0, variations.size()));
    }

    private Point transformPoint(Point currentPoint, LinearFunction affineTransformation,
        Transformation variation, LinearFunction finalAffine) {
        Point linearTransform = affineTransformation.transformation().apply(currentPoint);
        Point nonLinearTransform = variation.apply(linearTransform);
        return finalAffine.transformation().apply(nonLinearTransform);
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
        int pixelX = (int) ((point.x() - world.x()) * canvas.width() / world.width());
        int pixelY = (int) ((point.y() - world.y()) * canvas.height() / world.height());
        if (!canvas.contains(pixelX, pixelY)) {
            return null;
        }
        return canvas.pixel(pixelX, pixelY);
    }
}
