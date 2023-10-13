package edu.homework2;

public class Tracer {
    private Tracer() {}

    public static CallingInfo getCallingInfo() {
        return new CallingInfo();
    }
}
