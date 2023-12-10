package edu.project4.postprocessing;

import edu.project4.colors.Color;
import edu.project4.fractal.FractalImage;
import edu.project4.fractal.Pixel;
import static java.lang.Math.log10;

public class ColorLogScaler implements PostProcessor {
    @Override
    public void process(FractalImage canvas) {
        for (int i = 0; i < canvas.height(); i++) {
            for (int j = 0; j < canvas.width(); j++) {
                Pixel pixel = canvas.pixel(i, j);
                Color color = pixel.color();
                pixel.setColor(scaleColor(color, pixel.hitCount()));
            }
        }
    }

    public Color scaleColor(Color color, int hitCount) {
        int newR = scale(color.r(), hitCount);
        int newG = scale(color.g(), hitCount);
        int newB = scale(color.b(), hitCount);
        return new Color(newR, newG, newB);
    }

    private int scale(int color, int hitCount) {
        return (int) (3* color * (log10(hitCount) / hitCount));
    }
}
