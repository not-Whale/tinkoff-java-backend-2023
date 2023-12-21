package edu.project4;

import edu.project4.colors.Color;
import edu.project4.coordinates.Rect;
import edu.project4.fractal.FractalImage;
import edu.project4.fractal.Pixel;
import edu.project4.processing.LinearFunction;
import edu.project4.processing.LinearTransformations;
import edu.project4.processing.Transformation;
import edu.project4.render.ParallelRenderer;
import edu.project4.render.SimpleRenderer;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.project4.processing.NonLinearFunctions.disk;
import static edu.project4.processing.NonLinearFunctions.handkerchief;
import static edu.project4.processing.NonLinearFunctions.heart;
import static edu.project4.processing.NonLinearFunctions.horseshoe;
import static edu.project4.processing.NonLinearFunctions.polar;
import static edu.project4.processing.NonLinearFunctions.sinusoidal;
import static edu.project4.processing.NonLinearFunctions.spherical;
import static edu.project4.processing.NonLinearFunctions.spiral;
import static edu.project4.processing.NonLinearFunctions.swirl;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Отрисовка фрактального пламени.")
public class RenderTest {
    @Test
    @DisplayName("Однопоточный вариант.")
    void simpleRendererRender() {
        // given
        FractalImage canvas = FractalImage.create(100, 100);
        Rect world = new Rect(-1, -1, 2, 2);
        List<LinearFunction> affines = List.of(
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
        List<Transformation> variations = List.of(
            sinusoidal(),
            spherical(),
            swirl(),
            horseshoe()
        );
        LinearFunction finalAffine = affines.get(2);
        SimpleRenderer simpleRenderer = new SimpleRenderer();

        // when
        simpleRenderer.render(
            canvas,
            world,
            affines,
            variations,
            finalAffine,
            10_000,
            100
        );
        Pixel changedPixel = getChangedPixel(canvas);

        // then
        assertThat(changedPixel).isNotNull();
    }

    @Test
    @DisplayName("Многопоточный вариант.")
    void parallelRendererRender() {
        // given
        FractalImage canvas = FractalImage.create(100, 100);
        Rect world = new Rect(-1, -1, 2, 2);
        List<LinearFunction> affines = List.of(
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
        List<Transformation> variations = List.of(
            polar(),
            handkerchief(),
            heart(),
            disk(),
            spiral()
        );
        LinearFunction finalAffine = affines.get(2);
        ParallelRenderer parallelRenderer = new ParallelRenderer(8);

        // when
        parallelRenderer.render(
            canvas,
            world,
            affines,
            variations,
            finalAffine,
            10_000,
            100
        );
        Pixel changedPixel = getChangedPixel(canvas);

        // then
        assertThat(changedPixel).isNotNull();
    }

    private static Pixel getChangedPixel(FractalImage canvas) {
        Pixel changedPixel = null;
        for (int i = 0; i < canvas.width(); i++) {
            for (int j = 0; j < canvas.height(); j++) {
                Pixel currenPixel = canvas.pixel(i, j);
                if (currenPixel.hitCount() != 0 &&
                    (currenPixel.color().r() != 0 || currenPixel.color().g() != 0 || currenPixel.color().b() != 0)) {
                    changedPixel = currenPixel;
                    break;
                }
            }
        }
        return changedPixel;
    }
}
