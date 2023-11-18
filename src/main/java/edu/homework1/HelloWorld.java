package edu.homework1;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class HelloWorld {
    private final static Logger LOGGER = LogManager.getLogger();

    private HelloWorld() {
    }

    public static void sayHello() {
        LOGGER.log(Level.INFO, "Hello world!");
    }
}
