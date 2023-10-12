package edu.homework2;

public class Square extends Rectangle {
    private final int dimension;

    public Square(int dimension) {
        super(dimension, dimension);
        this.dimension = dimension;
    }

    Square setDimension(int dimension) {
        return new Square(dimension);
    }

    @Override
    Rectangle setWidth(int width) {
        if (this.dimension == width) {
            return new Square(width);
        }

        return new Rectangle(width, this.dimension);
    }

    @Override
    Rectangle setHeight(int height) {
        if (this.dimension == height) {
            return new Square(height);
        }

        return new Rectangle(this.dimension, height);
    }
}
