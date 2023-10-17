package edu.homework2.tracer;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TracerTest {
    @Test
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
