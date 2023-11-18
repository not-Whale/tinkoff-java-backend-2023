package edu.homework2.tracer;

public record CallingInfo(String className, String methodName) {
    public CallingInfo() {
        this(new Throwable().getStackTrace()[2].getClassName(), new Throwable().getStackTrace()[2].getMethodName());
    }
}
