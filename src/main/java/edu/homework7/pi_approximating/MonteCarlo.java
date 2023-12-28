package edu.homework7.pi_approximating;

import java.security.SecureRandom;

public class MonteCarlo {
    private static final int RADIUS = 1;

    private static final int X_CENTER = 1;

    private static final int Y_CENTER = 1;

    private static final int PI_CALC_COEFFICIENT = 4;

    private MonteCarlo() {}

    public static double calcPi(long iterations) {
        long circleCount = 0;
        SecureRandom random = new SecureRandom();
        for (long i = 0; i < iterations; i++) {
            double xDistance = random.nextDouble(0.0, RADIUS * 2) - X_CENTER;
            double yDistance = random.nextDouble(0.0, RADIUS * 2) - Y_CENTER;
            double module = Math.sqrt(xDistance * xDistance + yDistance * yDistance);
            if (module < RADIUS) {
                circleCount++;
            }
        }
        return PI_CALC_COEFFICIENT * (circleCount / (double) iterations);
    }
}
