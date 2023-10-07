package module1.homework1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("Подсчет количества шагов до получения постоянной Капрекара.")
public class KaprekarsRoutineTest {
    @Test
    @DisplayName("Happy path. Ввод: 1234, вывод: 3.")
    void countKaprekarPassed() {
        // given
        int number = 1234;

        // when
        int count = KaprekarsRoutine.countKaprekar(number);

        // then
        assertThat(count).isEqualTo(3);
    }

    @Test
    @DisplayName("Одна итерация. Ввод: 7641, вывод: 1.")
    void countKaprekarOneIter() {
        // given
        int number = 7641;

        // when
        int count = KaprekarsRoutine.countKaprekar(number);

        // then
        assertThat(count).isEqualTo(1);
    }

    @Test
    @DisplayName("Только 1 цифра отличается. Ввод: 9998, вывод: 5.")
    void countKaprekarOnlyOneUnique() {
        // given
        int number = 9998;

        // when
        int count = KaprekarsRoutine.countKaprekar(number);

        // then
        assertThat(count).isEqualTo(5);
    }

    @Test
    @DisplayName("Уже постоянная Капрекара. Ввод: 6174, вывод: 0.")
    void countKaprekarAlreadyKaprekar() {
        // given
        int number = 6174;

        // when
        int count = KaprekarsRoutine.countKaprekar(number);

        // then
        assertThat(count).isEqualTo(0);
    }

    @Test
    @DisplayName("Число с нулями. Ввод: 1100, вывод: 4.")
    void countKaprekarWithZeros() {
        // given
        int number = 1100;

        // when
        int count = KaprekarsRoutine.countKaprekar(number);

        // then
        assertThat(count).isEqualTo(4);
    }

    @Test
    @DisplayName("Все цифры одинаковые. Ввод: 2222, вывод: -1.")
    void countKaprekarSimilarDigits() {
        // given
        int number = 2222;

        // when
        int count = KaprekarsRoutine.countKaprekar(number);

        // then
        assertThat(count).isEqualTo(-1);
    }

    @Test
    @DisplayName("Число короче 4 цифр. Ввод: 333, вывод: -1.")
    void countKaprekarNumberLessThen4Digits() {
        // given
        int number = 353;

        // when
        int count = KaprekarsRoutine.countKaprekar(number);

        // then
        assertThat(count).isEqualTo(-1);
    }

    @Test
    @DisplayName("Число длиннее 4 цифр. Ввод: 55555, вывод: -1.")
    void countKaprekarNumberMoreThen4Digits() {
        // given
        int number = 55355;

        // when
        int count = KaprekarsRoutine.countKaprekar(number);

        // then
        assertThat(count).isEqualTo(-1);
    }
}
