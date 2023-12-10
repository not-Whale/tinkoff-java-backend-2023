package edu.project4.coordinates;

public record Rect(double x, double y, double width, double height) {
    public boolean contains(Point p) {
        return p.x() >= x && p.x() < x + width && p.y() >= y && p.y() < y + height;
    }
}

// TODO: накинуть ограничения для [-1; 1] диапазон
