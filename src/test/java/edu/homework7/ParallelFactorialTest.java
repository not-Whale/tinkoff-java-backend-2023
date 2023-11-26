package edu.homework7;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Вычисление факториала числа с помощью parallelStream.")
public class ParallelFactorialTest {
    @Test
    @DisplayName("Факториал 0.")
    void factorialOf0() {
        // given
        int base = 0;

        // when
        long factorial = ParallelFactorial.calc(base);

        // then
        assertThat(factorial).isEqualTo(1);
    }

    @Test
    @DisplayName("Факториал 1.")
    void factorialOf1() {
        // given
        int base = 1;

        // when
        long factorial = ParallelFactorial.calc(base);

        // then
        assertThat(factorial).isEqualTo(1);
    }

    @ParameterizedTest
    @CsvSource(value = {
        "5, 120",
        "6, 720",
        "7, 5_040",
        "8, 40_320",
        "9, 362_880",
        "10, 3_628_800",
        "11, 39_916_800",
        "12, 479_001_600",
        "13, 6_227_020_800",
        "14, 87_178_291_200",
        "15, 1_307_674_368_000",
        "16, 20_922_789_888_000",
        "17, 355_687_428_096_000",
        "18, 6_402_373_705_728_000",
        "19, 121_645_100_408_832_000",
        "20, 2_432_902_008_176_640_000"
    })
    @DisplayName("Факториал различных положительных чисел.")
    void factorialPositive(int base, long answer) {
        // given

        // when
        long factorial = ParallelFactorial.calc(base);

        // then
        assertThat(factorial).isEqualTo(answer);
    }

    @Test
    @DisplayName("Отрицательные значения.")
    void factorialNegative() {
        // given
        int base = -10;

        // when
        long factorial = ParallelFactorial.calc(base);

        // then
        assertThat(factorial).isEqualTo(0);
    }
}
