package edu.homework11.arithmetic_utils;

import java.lang.reflect.InvocationTargetException;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;
import net.bytebuddy.implementation.MethodDelegation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static net.bytebuddy.matcher.ElementMatchers.named;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Функция, которая изменяет поведение существующего класса на лету.")
public class ArithmeticUtilsExTest {
    @Test
    @DisplayName("В классе ArithmeticUtils вместо операции \"+\" будет производиться операция \"*\".")
    void delegateRedefineSum() {
        try {
            ArithmeticUtils original = new ArithmeticUtils();
            assertThat(original.sum(5, 5)).isEqualTo(10);

            ByteBuddyAgent.install();
            Class<?> Redefined = new ByteBuddy()
                .redefine(ArithmeticUtils.class)
                .method(named("sum"))
                .intercept(MethodDelegation.to(RedefinedArithmeticUtils.class))
                .make()
                .load(ArithmeticUtils.class.getClassLoader(), ClassReloadingStrategy.fromInstalledAgent())
                .getLoaded();
            assertThat(Redefined).isEqualTo(ArithmeticUtils.class);

            ArithmeticUtils redefined = (ArithmeticUtils) Redefined.getDeclaredConstructor().newInstance();
            assertThat(redefined.sum(5, 5)).isEqualTo(25);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private static class RedefinedArithmeticUtils {
        public static int sum(int a, int b) {
            return a * b;
        }
    }

    private static class ArithmeticUtils {
        public int sum(int a, int b) {
            return a + b;
        }
    }
}
