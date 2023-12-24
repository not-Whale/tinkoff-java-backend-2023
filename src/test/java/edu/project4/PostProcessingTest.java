package edu.project4;

import edu.project4.colors.Color;
import edu.project4.coordinates.Rect;
import edu.project4.fractal.FractalImage;
import edu.project4.postprocessing.ColorLogScaler;
import edu.project4.postprocessing.GammaCorrector;
import edu.project4.processing.LinearFunction;
import edu.project4.processing.LinearTransformations;
import edu.project4.processing.Transformation;
import edu.project4.render.ParallelRenderer;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.RepeatedTest;
import static edu.project4.processing.NonLinearFunctions.horseshoe;
import static edu.project4.processing.NonLinearFunctions.sinusoidal;
import static edu.project4.processing.NonLinearFunctions.spherical;
import static edu.project4.processing.NonLinearFunctions.swirl;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Пост обработка картинки.")
public class PostProcessingTest {
    private static FractalImage canvas;

    private static Rect world;

    private static List<LinearFunction> affines;

    private static List<Transformation> variations;

    private static LinearFunction finalAffine;

    @BeforeAll static void createImage() {
        canvas = FractalImage.create(100, 100);
        world = new Rect(-1, -1, 2, 2);
        affines = List.of(
            new LinearFunction(
                LinearTransformations.create(0.95, 0.45, -0.25, -0.5, 0.0, 0.0),
                0.2,
                new Color(255, 255, 255)
            ),
            new LinearFunction(
                LinearTransformations.create(0.6, 0.7, 0.36, 0.7, 0.9, 0.61),
                0.4,
                new Color(185, 43, 39)
            ),
            new LinearFunction(
                LinearTransformations.create(0.45, 0.65, 0.31, 0.65, 0.5, 0.2),
                0.4,
                new Color(21, 101, 192)
            )
        );
        variations = List.of(
            sinusoidal(),
            spherical(),
            swirl(),
            horseshoe()
        );
        finalAffine = affines.get(2);
        ParallelRenderer parallelRenderer = new ParallelRenderer(8);
        parallelRenderer.render(
            canvas,
            world,
            affines,
            variations,
            finalAffine,
            10_000,
            100
        );
    }

    @Order(2)
    @RepeatedTest(20)
    @DisplayName("Логаримфимическое масштабирование цвета.")
    void colorLogScaling() {
        // given
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int x = random.nextInt(0, canvas.width());
        int y = random.nextInt(0, canvas.height());
        Color oldColor = canvas.pixel(x, y).color();

        // when
        ColorLogScaler colorLogScaler = new ColorLogScaler(1);
        colorLogScaler.process(canvas);
        Color newColor = canvas.pixel(x, y).color();

        // then
        assertThat(oldColor.r()).isGreaterThanOrEqualTo(newColor.r());
        assertThat(oldColor.g()).isGreaterThanOrEqualTo(newColor.g());
        assertThat(oldColor.b()).isGreaterThanOrEqualTo(newColor.b());
    }

    @Order(1)
    @RepeatedTest(20)
    @DisplayName("Гамма-коррекция")
    void gammaCorrection() {
        // given
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int x = random.nextInt(0, canvas.width());
        int y = random.nextInt(0, canvas.height());
        Color oldColor = canvas.pixel(x, y).color();

        // when
        GammaCorrector gammaCorrector = new GammaCorrector();
        gammaCorrector.process(canvas);
        Color newColor = canvas.pixel(x, y).color();

        // then
        assertThat(oldColor.r()).isLessThanOrEqualTo(newColor.r());
        assertThat(oldColor.g()).isLessThanOrEqualTo(newColor.g());
        assertThat(oldColor.b()).isLessThanOrEqualTo(newColor.b());
    }
}
