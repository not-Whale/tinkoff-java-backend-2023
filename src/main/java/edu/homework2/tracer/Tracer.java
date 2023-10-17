package edu.homework2.tracer;

public class Tracer {
    private Tracer() {}

    public static CallingInfo getCallingInfo() {
        return new CallingInfo();
    }
}
