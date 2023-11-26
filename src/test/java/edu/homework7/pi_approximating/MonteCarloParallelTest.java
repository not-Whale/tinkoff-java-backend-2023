package edu.homework7.pi_approximating;

import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Расчет числа Пи методом Монте-Карло. Параллельные вычисления.")
public class MonteCarloParallelTest {
    @RepeatedTest(10)
    @DisplayName("Расчет.")
    void calc() {
        // given
        double expected = Math.PI;

        // when
        double pi = MonteCarloParallel.calcPi(1_000_000);

        // then
        assertThat(pi).isCloseTo(expected, Percentage.withPercentage(0.15));
    }
}
