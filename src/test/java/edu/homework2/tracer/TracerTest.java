package edu.homework2.tracer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Кто вызвал функцию?")
public class TracerTest {
    @Test
    @DisplayName("Вызов функции из тестового метода")
    void callingInfoTest() {
        // given
        // --

        // when
        CallingInfo callingInfo = Tracer.getCallingInfo();

        // then
        assertThat(callingInfo.className()).isEqualTo("edu.homework2.tracer.TracerTest");
        assertThat(callingInfo.methodName()).isEqualTo("callingInfoTest");
    }

    @Test
    @DisplayName("Вызов функции из статического метода класса")
    void callingInfoStaticMethod() {
        // given
        class StaticMethodCaller {
            public static CallingInfo staticMethod() {
                return Tracer.getCallingInfo();
            }
        }

        // when
        CallingInfo callingInfo = StaticMethodCaller.staticMethod();

        // then
        assertThat(callingInfo.className()).isEqualTo("edu.homework2.tracer.TracerTest$1StaticMethodCaller");
        assertThat(callingInfo.methodName()).isEqualTo("staticMethod");
    }

    @Test
    @DisplayName("Вызов функции из нестатического метода класса")
    void callingInfoNonStaticMethod() {
        // given
        class NonStaticMethodCaller {
            public CallingInfo nonStaticMethod() {
                return Tracer.getCallingInfo();
            }
        }

        // when
        NonStaticMethodCaller nonStaticMethodCaller = new NonStaticMethodCaller();
        CallingInfo callingInfo = nonStaticMethodCaller.nonStaticMethod();

        // then
        assertThat(callingInfo.className()).isEqualTo("edu.homework2.tracer.TracerTest$1NonStaticMethodCaller");
        assertThat(callingInfo.methodName()).isEqualTo("nonStaticMethod");
    }
}
