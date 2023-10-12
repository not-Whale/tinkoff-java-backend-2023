package edu.homework2;

public class Square extends Rectangle {
    private final int width;
    private final int height;

    public Square(int width, int height) {
        super(width, height);
        this.width = width;
        this.height = height;
    }

    @Override
    Rectangle setWidth(int width) {
        if (this.height == width) {
            return new Square(width, this.height);
        }

        return new Rectangle(width, this.height);
    }

    @Override
    Rectangle setHeight(int height) {
        if (this.width == height) {
            return new Square(this.width, height);
        }

        return new Rectangle(this.width, height);
    }
}
