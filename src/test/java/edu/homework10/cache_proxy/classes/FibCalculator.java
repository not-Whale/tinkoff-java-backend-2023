package edu.homework10.cache_proxy.classes;

import edu.homework10.cache_proxy.annotations.Cache;

public interface FibCalculator {
    @Cache(persist = false)
    long fib(int number);
}
