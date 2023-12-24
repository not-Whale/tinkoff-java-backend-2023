package edu.project4.postprocessing;

import edu.project4.fractal.FractalImage;

@FunctionalInterface
public interface PostProcessor {
    void process(FractalImage image);
}
