package edu.homework11.hello_world;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.FixedValue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

import static net.bytebuddy.matcher.ElementMatchers.named;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Создание класса, метод toString которого выводит \"Hello, ByteBuddy!\".")
public class HelloWorldExTest {
    @Test
    void helloWorld() {
        try {
            Class<?> HelloByteBuddy = new ByteBuddy()
                .subclass(Object.class)
                .name("HelloByteBuddy")
                .method(named("toString")).intercept(FixedValue.value("Hello, ByteBuddy!"))
                .make()
                .load(getClass().getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
                .getLoaded();

            Object helloByteBuddy = HelloByteBuddy.getDeclaredConstructor().newInstance();
            assertThat(helloByteBuddy.toString()).isEqualTo("Hello, ByteBuddy!");
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
