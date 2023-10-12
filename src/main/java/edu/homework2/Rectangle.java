package edu.homework2;

public class Rectangle {
    private final int width;
    private final int height;

    public Rectangle(int width, int height) {
        if (width < 1 || height < 1) {
            throw new IllegalArgumentException("Измерения прямоугольника должны быть положительные!");
        }

        this.width = width;
        this.height = height;
    }

    Rectangle setWidth(int width) {
        return new Rectangle(width, this.height);
    }

    Rectangle setHeight(int height) {
        return new Rectangle(this.width, height);
    }

    double area() {
        return width * height;
    }
}
