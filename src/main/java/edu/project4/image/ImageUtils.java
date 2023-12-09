package edu.project4.image;

import edu.project4.colors.Color;
import edu.project4.fractal.FractalImage;
import edu.project4.fractal.Pixel;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import javax.imageio.ImageIO;

public final class ImageUtils {
    private static final int RED_SHIFT = 16;

    private static final int GREEN_SHIFT = 8;

    private ImageUtils() {}

    public static void save(FractalImage canvas, Path filename, ImageFormat format) {
        BufferedImage image = new BufferedImage(canvas.width(), canvas.height(), BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < canvas.height(); i++) {
            for (int j = 0; j < canvas.width(); j++) {
                Pixel pixel = canvas.pixel(i, j);
                Color color = pixel.color();
                int rgb = (color.r() << RED_SHIFT) + (color.g() << GREEN_SHIFT) + color.b();
                image.setRGB(i, j, rgb);
            }
        }
        try {
            ImageIO.write(image, "png", filename.toFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
