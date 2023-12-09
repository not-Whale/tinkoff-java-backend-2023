package edu.project4.fractal;

public record FractalImage(Pixel[] data, int width, int height) {
    public static FractalImage create(int width, int height) {
        int size = width * height;
        Pixel[] data = new Pixel[size];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                data[i * width + j] = new Pixel();
            }
        }
        return new FractalImage(data, width, height);
    }

    public boolean contains(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public Pixel pixel(int x, int y) {
        return data[y * width + x];
    }
}
