package edu.project4;

import edu.project4.fractal.FractalImage;
import edu.project4.image.ImageFormat;
import edu.project4.image.ImageUtils;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DisplayName("Инструменты для работы с картинками.")
public class ImageUtilsTest {
    private static final Logger LOGGER = LogManager.getLogger();

    @Test
    @DisplayName("Сохранение картинки.")
    void saveImage() {
        // given
        FractalImage fractalImage = FractalImage.create(1920, 1080);

        // when
        Path saveFilePath = Path.of("src", "test", "resources", "project4", "media", "test.png");
        ImageUtils.save(fractalImage, saveFilePath, ImageFormat.PNG);

        // then
        assertThat(saveFilePath.toFile().exists()).isTrue();
        try {
            Files.delete(saveFilePath);
        } catch (IOException e) {
            LOGGER.error("Something went wrong!");
        }
    }

    @Test
    @DisplayName("Сохранение в разных форматах.")
    void saveFormats() {
        // given
        FractalImage fractalImage = FractalImage.create(1920, 1080);

        // when
        Path saveFilePathPng = Path.of("src", "test", "resources", "project4", "media", "test.png");
        Path saveFilePathBmp = Path.of("src", "test", "resources", "project4", "media", "test.bmp");
        Path saveFilePathJpeg = Path.of("src", "test", "resources", "project4", "media", "test.jpeg");

        // then
        assertDoesNotThrow(() -> ImageUtils.save(fractalImage, saveFilePathPng, ImageFormat.PNG));
        assertDoesNotThrow(() -> ImageUtils.save(fractalImage, saveFilePathBmp, ImageFormat.BMP));
        assertDoesNotThrow(() -> ImageUtils.save(fractalImage, saveFilePathJpeg, ImageFormat.JPEG));
        try {
            Files.delete(saveFilePathPng);
            Files.delete(saveFilePathBmp);
            Files.delete(saveFilePathJpeg);
        } catch (IOException e) {
            LOGGER.error("Something went wrong!");
        }
    }
}
