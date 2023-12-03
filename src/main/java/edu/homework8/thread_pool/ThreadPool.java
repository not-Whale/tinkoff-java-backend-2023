package edu.homework8.thread_pool;

public interface ThreadPool extends AutoCloseable {
    void start();

    void execute(Runnable runnable);
}
