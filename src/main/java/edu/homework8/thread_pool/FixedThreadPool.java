package edu.homework8.thread_pool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class FixedThreadPool implements ThreadPool {
    private final int poolSize;

    private final Thread[] pool;

    private final BlockingQueue<Runnable> tasks;

    private final Runnable treadWork;

    private volatile boolean running = true;

    private FixedThreadPool(int nThreads) {
        this.poolSize = nThreads;
        this.pool = new Thread[poolSize];
        this.tasks = new LinkedBlockingQueue<>(poolSize);
        this.treadWork = () -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Runnable task = tasks.take();
                    task.run();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };
        for (int i = 0; i < poolSize; i++) {
            pool[i] = new Thread(treadWork);
        }
    }

    public static ThreadPool create(int nThreads) {
        return new FixedThreadPool(nThreads);
    }

    @Override
    public void start() {
        for (int i = 0; i < poolSize; i++) {
            pool[i].start();
        }
        new Thread(() -> {
            int position = 0;
            while (running) {
                if (pool[position].isInterrupted()) {
                    pool[position] = new Thread(treadWork);
                    pool[position].start();
                }
                position = (position + 1) % poolSize;
            }
        });
    }

    @Override
    public void execute(Runnable runnable) {
        try {
            tasks.put(runnable);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() throws InterruptedException {
        running = false;
        while (!tasks.isEmpty()) {}
        for (int i = 0; i < poolSize; i++) {
            tasks.put(() -> Thread.currentThread().interrupt());
        }
    }
}
