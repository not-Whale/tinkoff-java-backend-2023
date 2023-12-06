package edu.homework9.stats_collector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class StatsCollector {
    private final ReadWriteLock lock;

    private final List<Metric> stats = new ArrayList<>();

    public StatsCollector(boolean fair) {
        this.lock = new ReentrantReadWriteLock(fair);
    }

    public void push(MetricType type, double[] data) {
        double metricValue = switch (type) {
            case AVG -> average(data);
            case SUM -> sum(data);
            case MIN -> min(data);
            case MAX -> max(data);
        };
        Metric metric = new Metric(type, data, metricValue);
        lock.writeLock().lock();
        stats.add(metric);
        lock.writeLock().unlock();
    }

    public List<Metric> stats() {
        lock.readLock().lock();
        try {
            return List.copyOf(stats);
        } finally {
            lock.readLock().unlock();
        }
    }

    private Double average(double[] data) {
        return Arrays.stream(data).average().orElse(0.0);
    }

    private Double sum(double[] data) {
        return Arrays.stream(data).sum();
    }

    private Double min(double[] data) {
        if (data == null || data.length == 0) {
            return null;
        }
        return Arrays.stream(data).min().getAsDouble();
    }

    private Double max(double[] data) {
        if (data == null || data.length == 0) {
            return null;
        }
        return Arrays.stream(data).max().getAsDouble();
    }
}
