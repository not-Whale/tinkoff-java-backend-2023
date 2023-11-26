package edu.homework7.pi_approximating;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

public class MonteCarloParallel {
    private static final int RADIUS = 1;

    private static final int X_CENTER = 1;

    private static final int Y_CENTER = 1;

    private static final int CORE_NUMBER = 8;

    private static final int PI_CALC_COEFFICIENT = 4;

    private MonteCarloParallel() {}

    public static double calcPi(int iter) {
        int circleCount = 0;
        Callable<Integer> calcPiPartCalc = getPartCalculation(iter);
        ExecutorService executorService = Executors.newCachedThreadPool();
        var tasks = Stream.generate(() -> calcPiPartCalc).limit(CORE_NUMBER).toList();
        try {
            List<Future<Integer>> futures = executorService.invokeAll(tasks);
            for (Future<Integer> future : futures) {
                circleCount += future.get();
            }
        } catch (InterruptedException | ExecutionException e) {
            return 0;
        } finally {
            executorService.shutdown();
        }
        return PI_CALC_COEFFICIENT * (circleCount / (double) iter);
    }

    private static Callable<Integer> getPartCalculation(int iter) {
        return () -> {
            ThreadLocalRandom random = ThreadLocalRandom.current();
            int circlePartCount = 0;
            int iterPart = iter / CORE_NUMBER;
            for (int i = 0; i < iterPart; i++) {
                double xDistance = random.nextDouble(0.0, RADIUS * 2) - X_CENTER;
                double yDistance = random.nextDouble(0.0, RADIUS * 2) - Y_CENTER;
                double module = Math.sqrt(xDistance * xDistance + yDistance * yDistance);
                if (module < RADIUS) {
                    circlePartCount++;
                }
            }
            return circlePartCount;
        };
    }
}
