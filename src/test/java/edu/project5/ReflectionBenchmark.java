package edu.project5;

import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

@State(Scope.Benchmark)
public class ReflectionBenchmark {
    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
            .include(ReflectionBenchmark.class.getSimpleName())
            .shouldFailOnError(true)
            .shouldDoGC(true)
            .mode(Mode.AverageTime)
            .timeUnit(TimeUnit.NANOSECONDS)
            .forks(1)
            .warmupForks(1)
            .warmupIterations(1)
            .warmupTime(TimeValue.seconds(5))
            .measurementIterations(1)
            .measurementTime(TimeValue.seconds(5))
            .build();

        new Runner(options).run();
    }

    public record Student(String name, String surname) {}

    private Student student;

    private Method method;

    private MethodHandle methodHandle;

    private StudentNameGetter getter;

    @Setup
    public void setup() throws NoSuchMethodException {
        student = new Student("Nikita", "Rezepin");
        method = Student.class.getMethod("name");

        try {
            MethodHandles.Lookup lookup = MethodHandles.lookup();
            methodHandle = lookup.findVirtual(Student.class, "name", MethodType.methodType(String.class));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        try {
            MethodHandles.Lookup caller = MethodHandles.lookup();
            MethodType factoryType = MethodType.methodType(StudentNameGetter.class);
            MethodType interfaceMethodType = MethodType.methodType(
                String.class,
                Student.class
            );
            MethodHandle implementation = caller.findVirtual(
                Student.class,
                "name",
                MethodType.methodType(String.class)
            );
            MethodType dynamicMethodType = MethodType.methodType(
                String.class,
                Student.class
            );
            getter = (StudentNameGetter) LambdaMetafactory.metafactory(
                caller,
                "get",
                factoryType,
                interfaceMethodType,
                implementation,
                dynamicMethodType
            ).getTarget().invokeExact();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
    
    @Benchmark
    public void directAccess(Blackhole bh) {
        String name = student.name();
        bh.consume(name);
    }

    @Benchmark
    public void reflection(Blackhole bh) throws InvocationTargetException, IllegalAccessException {
        String name = (String) method.invoke(student);
        bh.consume(name);
    }

    @Benchmark
    public void handling(Blackhole bh) throws Throwable {
        String name = (String) methodHandle.invoke(student);
        bh.consume(name);
    }

    @Benchmark
    public void metafactoring(Blackhole bh) {
        String name = (String) getter.get(student);
        bh.consume(name);
    }

    public interface StudentNameGetter {
        String get(Student student);
    }
}
