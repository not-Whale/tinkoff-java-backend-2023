package edu.project4.postprocessing;

import edu.project4.colors.Color;
import edu.project4.fractal.FractalImage;
import edu.project4.fractal.Pixel;
import static java.lang.Math.pow;

public class GammaCorrector implements PostProcessor {
    private static final double DEFAULT_GAMMA = 2.2;

    private final double gammaRed;

    private final double gammaGreen;

    private final double gammaBlue;

    public GammaCorrector() {
        this(DEFAULT_GAMMA);
    }

    public GammaCorrector(double gamma) {
        this(gamma, gamma, gamma);
    }

    public GammaCorrector(double gammaRed, double gammaGreen, double gammaBlue) {
        this.gammaRed = gammaRed;
        this.gammaGreen = gammaGreen;
        this.gammaBlue = gammaBlue;
    }

    @Override
    public void process(FractalImage canvas) {
        for (int i = 0; i < canvas.height(); i++) {
            for (int j = 0; j < canvas.width(); j++) {
                Pixel pixel = canvas.pixel(i, j);
                Color color = pixel.color();
                pixel.setColor(correctColor(color));
            }
        }
    }

    private Color correctColor(Color color) {
        int newR = correct(color.r(), gammaRed);
        int newG = correct(color.g(), gammaGreen);
        int newB = correct(color.b(), gammaBlue);
        return new Color(newR, newG, newB);
    }

    @SuppressWarnings("MagicNumber")
    private int correct(int color, double gamma) {
        double b = color / 255.0;
        double correctedB = pow(b, 1 / gamma);
        return (int) correctedB * 255;
    }
}
