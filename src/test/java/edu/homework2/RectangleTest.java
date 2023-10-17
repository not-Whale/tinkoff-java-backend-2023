package edu.homework2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Квадрат и прямоугольник")
public class RectangleTest {
    @Test
    @DisplayName("Установка ширины для прямоугольника")
    void rectangleSetWidth() {
        // given
        Rectangle rectangle1 = new Rectangle(10, 20);

        // when
        Rectangle rectangle2 = rectangle1.setWidth(15);

        // then
        assertThat(rectangle1.area()).isEqualTo(200);
        assertThat(rectangle2.area()).isEqualTo(300);
    }

    @Test
    @DisplayName("Установка высоты для прямоугольника")
    void rectangleSetHeight() {
        // given
        Rectangle rectangle1 = new Rectangle(10, 20);

        // when
        Rectangle rectangle2 = rectangle1.setHeight(25);

        // then
        assertThat(rectangle1.area()).isEqualTo(200);
        assertThat(rectangle2.area()).isEqualTo(250);
    }

    @Test
    @DisplayName("Установка ширины для квадрата с переходом в прямоугольник")
    void squareSetWidthToRectangle() {
        // given
        Square square = new Square(10);

        // when
        Rectangle rectangle = square.setWidth(20);

        // then
        assertThat(square.area()).isEqualTo(100);
        assertThat(rectangle.area()).isEqualTo(200);
    }

    @Test
    @DisplayName("Установка высоты для квадрата с переходом в прямоугольник")
    void squareSetHeightToRectangle() {
        // given
        Square square = new Square(10);

        // when
        Rectangle rectangle = square.setHeight(20);

        // then
        assertThat(square.area()).isEqualTo(100);
        assertThat(rectangle.area()).isEqualTo(200);
    }

    @Test
    @DisplayName("Масштабирование квадрата")
    void squareSetDimension() {
        // given
        Square square = new Square(10);

        // when
        Rectangle rectangle = square.setDimension(20);

        // then
        assertThat(square.area()).isEqualTo(100);
        assertThat(rectangle.area()).isEqualTo(400);
    }

    @Test
    @DisplayName("Установка ширины для квадрата, равной старой")
    void squareSetEqualWight() {
        // given
        Square square = new Square(10);

        // when
        Rectangle rectangle = square.setWidth(10);

        // then
        assertThat(square.area()).isEqualTo(100);
        assertThat(rectangle.area()).isEqualTo(100);
    }

    @Test
    @DisplayName("Установка высоты для квадрата, равной старой")
    void squareSetEqualHeight() {
        // given
        Square square = new Square(10);

        // when
        Rectangle rectangle = square.setHeight(10);

        // then
        assertThat(square.area()).isEqualTo(100);
        assertThat(rectangle.area()).isEqualTo(100);
    }

    static Arguments[] rectangles() {
        return new Arguments[] {
            Arguments.of(new Rectangle(5, 15)),
            Arguments.of(new Square(25))
        };
    }

    @ParameterizedTest
    @MethodSource("rectangles")
    void rectangleArea(Rectangle rect) {
        rect = rect.setWidth(20);
        rect = rect.setHeight(10);

        assertThat(rect.area()).isEqualTo(200.0);
    }
}
