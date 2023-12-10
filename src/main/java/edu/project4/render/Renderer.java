package edu.project4.render;

import edu.project4.coordinates.Rect;
import edu.project4.fractal.FractalImage;
import edu.project4.processing.LinearFunction;
import edu.project4.processing.Transformation;
import java.util.List;

@FunctionalInterface
public interface Renderer {
    default FractalImage render(FractalImage canvas, Rect world,
        List<LinearFunction> affineTransformations, List<Transformation> variations, LinearFunction finalAffine,
        int samples, int perSampleIterations) {
        return render(canvas, world, affineTransformations, variations, finalAffine, samples, perSampleIterations, 1);
    }

    FractalImage render(FractalImage canvas, Rect world,
        List<LinearFunction> affineTransformations, List<Transformation> variations, LinearFunction finalAffine,
        int samples, int perSampleIterations, int symmetry);
}
