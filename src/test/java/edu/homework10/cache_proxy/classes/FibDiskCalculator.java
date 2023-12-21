package edu.homework10.cache_proxy.classes;

import edu.homework10.cache_proxy.annotations.Cache;

public interface FibDiskCalculator {
    @Cache(persist = true)
    long fib(int number);
}
