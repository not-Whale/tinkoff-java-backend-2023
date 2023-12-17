package edu.homework11.fibonacci;

import java.lang.reflect.InvocationTargetException;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.dynamic.scaffold.InstrumentedType;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bytecode.ByteCodeAppender;
import net.bytebuddy.jar.asm.Label;
import net.bytebuddy.jar.asm.MethodVisitor;
import net.bytebuddy.jar.asm.Opcodes;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static net.bytebuddy.matcher.ElementMatchers.named;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Создание нового класса и метода в нем с сигнатурой long fib(int n).")
public class FibonacciExTest {
    @ParameterizedTest
    @CsvSource(value = {
        "1, 1",
        "2, 1",
        "3, 2",
        "4, 3",
        "5, 5",
        "6, 8",
        "7, 13",
        "8, 21",
        "9, 34",
        "10, 55",
        "11, 89",
        "12, 144",
        "13, 233",
        "14, 377",
        "15, 610",
        "16, 987",
        "17, 1597",
        "18, 2584",
        "19, 4181",
        "20, 6765"
    })
    @DisplayName("Для генерации кода используется ByteCodeAppender.")
    void generateFibonacciClass(int n, long f)
        throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> Fibonacci = new ByteBuddy()
            .subclass(FibonacciCalculator.class)
            .name("Fibonacci")
            .method(named("fib"))
            .intercept(FibonacciImplementation.INSTANCE)
            .make()
            .load(getClass().getClassLoader())
            .getLoaded();

        FibonacciCalculator fibonacciCalculator = (FibonacciCalculator) Fibonacci.getDeclaredConstructor().newInstance();
        assertThat(fibonacciCalculator.fib(n)).isEqualTo(f);
    }

    public interface FibonacciCalculator {
        long fib(int n);
    }

    public enum FibonacciImplementation implements Implementation {
        INSTANCE;

        public @NotNull InstrumentedType prepare(@NotNull InstrumentedType instrumentedType) {
            return instrumentedType;
        }

        public @NotNull ByteCodeAppender appender(@NotNull Target implementationTarget) {
            return FibonacciMethod.INSTANCE;
        }
    }

    public enum FibonacciMethod implements ByteCodeAppender {
        INSTANCE;

        public @NotNull Size apply(
            @NotNull MethodVisitor visitor,
            Implementation.@NotNull Context context,
            MethodDescription description) {
            if (!description.getReturnType().asErasure().represents(long.class)) {
                throw new IllegalArgumentException(description + " must return long!");
            }
            if (description.getParameters().size() != 1
                || !description.getParameters().getFirst().getType().asErasure().represents(int.class)) {
                throw new IllegalArgumentException(description + " must have one int argument!");
            }

            Label l8 = new Label();
            Label l33 = new Label();

            // 0
            visitor.visitInsn(Opcodes.LCONST_1);
            // 1
            visitor.visitVarInsn(Opcodes.LSTORE, 2);
            // 2
            visitor.visitInsn(Opcodes.LCONST_1);
            // 3
            visitor.visitVarInsn(Opcodes.LSTORE, 4);
            // 5
            visitor.visitInsn(Opcodes.ICONST_2);
            // 6
            visitor.visitVarInsn(Opcodes.ISTORE, 6);

            // 7
            visitor.visitLabel(l8);

            // 8
            visitor.visitFrame(
                Opcodes.F_APPEND,
                3,
                new Object[] { Opcodes.LONG, Opcodes.LONG, Opcodes.INTEGER },
                0,
                null
            );
            visitor.visitVarInsn(Opcodes.ILOAD, 6);

            // 10
            visitor.visitVarInsn(Opcodes.ILOAD, 1);
            // 11
            visitor.visitJumpInsn(Opcodes.IF_ICMPGE, l33);
            // 14
            visitor.visitVarInsn(Opcodes.LLOAD, 2);
            // 15
            visitor.visitVarInsn(Opcodes.LLOAD, 4);
            // 17
            visitor.visitInsn(Opcodes.LADD);
            // 18
            visitor.visitVarInsn(Opcodes.LSTORE, 7);
            // 20
            visitor.visitVarInsn(Opcodes.LLOAD, 4);
            // 22
            visitor.visitVarInsn(Opcodes.LSTORE, 2);
            // 23
            visitor.visitVarInsn(Opcodes.LLOAD, 7);
            // 25
            visitor.visitVarInsn(Opcodes.LSTORE, 4);
            // 27
            visitor.visitIincInsn(6, 1);
            // 30
            visitor.visitJumpInsn(Opcodes.GOTO, l8);

            // 32
            visitor.visitLabel(l33);

            // 33
            visitor.visitFrame(
                Opcodes.F_CHOP,
                0,
                null,
                0,
                null
            );
            visitor.visitVarInsn(Opcodes.LLOAD, 4);
            // 35
            visitor.visitInsn(Opcodes.LRETURN);

            return new Size(4, 9);
        }
    }
}
