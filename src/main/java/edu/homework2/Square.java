package edu.homework2;

public class Square extends Rectangle {
    private final int width;
    private final int height;

    public Square(int width, int height) {
        super(width, height);

        if (width != height) {
            throw new IllegalArgumentException("Измерения квадрата должны быть равны!");
        }

        this.width = width;
        this.height = height;
    }

    public Square(int dimension) {
        this(dimension, dimension);
    }

    @Override
    Rectangle setWidth(int width) {
        if (this.height == width) {
            return new Square(width);
        }

        return new Rectangle(width, this.height);
    }

    @Override
    Rectangle setHeight(int height) {
        if (this.width == height) {
            return new Square(height);
        }

        return new Rectangle(this.width, height);
    }
}
