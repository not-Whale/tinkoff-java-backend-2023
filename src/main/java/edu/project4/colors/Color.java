package edu.project4.colors;

public record Color(int r, int g, int b) {
    public Color() {
        this(0, 0, 0);
    }

    public Color mix(Color other) {
        int newR = (r + other.r) / 2;
        int newG = (g + other.g) / 2;
        int newB = (b + other.b) / 2;
        return new Color(newR, newG, newB);
    }
}
