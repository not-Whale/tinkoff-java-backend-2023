package edu.project4.fractal;

import edu.project4.colors.Color;

public class Pixel {
    private Color color;

    private int hitCount;

    public Pixel(Color color, int hitCount) {
        this.color = color;
        this.hitCount = hitCount;
    }

    public Pixel() {
        this(new Color(), 0);
    }

    public Color color() {
        return color;
    }

    public int hitCount() {
        return hitCount;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void mixColor(Color other) {
        color = color.mix(other);
    }

    public void hit() {
        hitCount++;
    }
}
