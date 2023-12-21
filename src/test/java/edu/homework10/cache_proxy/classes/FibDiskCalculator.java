package edu.homework10.cache_proxy.classes;

import edu.homework10.cache_proxy.annotations.Cache;

public interface FibDiskCalculator {
    @Cache(persist = true)
    public long fib(int number);
}
