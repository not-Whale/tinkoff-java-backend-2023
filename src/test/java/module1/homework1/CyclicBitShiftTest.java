package module1.homework1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("Циклический битовый сдвиг.")
public class CyclicBitShiftTest {
    @Test
    @DisplayName("Сдвиг вправо. Happy path. Ввод: (17, 2), вывод: 12.")
    void rotateRightPassed() {
        // given
        int number = 17;
        int shift = 2;

        // when
        int rotatedRight = CyclicBitShift.rotateRight(number, shift);

        // then
        assertThat(rotatedRight).isEqualTo(12);
    }

    @Test
    @DisplayName("Сдвиг вправо. Нулевой сдвиг. Ввод: (20, 0), вывод: 20.")
    void rotateRightZeroShift() {
        // given
        int number = 20;
        int shift = 0;

        // when
        int rotatedRight = CyclicBitShift.rotateRight(number, shift);

        // then
        assertThat(rotatedRight).isEqualTo(20);
    }

    @Test
    @DisplayName("Сдвиг вправо. Сдвиг нуля. Ввод: (0, 3), вывод: 0.")
    void rotateRightZeroNumber() {
        // given
        int number = 0;
        int shift = 3;

        // when
        int rotatedRight = CyclicBitShift.rotateRight(number, shift);

        // then
        assertThat(rotatedRight).isEqualTo(0);
    }

    @Test
    @DisplayName("Сдвиг вправо. Отрицательное число. Ввод: (-5, 5), вывод: -1.")
    void rotateRightNegNumber() {
        // given
        int number = -5;
        int shift = 5;

        // when
        int rotatedRight = CyclicBitShift.rotateRight(number, shift);

        // then
        assertThat(rotatedRight).isEqualTo(-1);
    }

    @Test
    @DisplayName("Сдвиг вправо. Отрицательный сдвиг. Ввод: (16, -2), вывод: 2.")
    void rotateRightNegShift() {
        // given
        int number = 16;
        int shift = -2;

        // when
        int rotatedRight = CyclicBitShift.rotateRight(number, shift);

        // then
        assertThat(rotatedRight).isEqualTo(2);
    }

    @Test
    @DisplayName("Сдвиг влево. Happy path. Ввод: (33, 3), вывод: 12.")
    void rotateLeftPassed() {
        // given
        int number = 33;
        int shift = 3;

        // when
        int rotatedLeft = CyclicBitShift.rotateLeft(number, shift);

        // then
        assertThat(rotatedLeft).isEqualTo(12);
    }

    @Test
    @DisplayName("Сдвиг влево. Нулевой сдвиг. Ввод: (10, 0), вывод: 10.")
    void rotateLeftZeroShift() {
        // given
        int number = 10;
        int shift = 0;

        // when
        int rotatedLeft = CyclicBitShift.rotateLeft(number, shift);

        // then
        assertThat(rotatedLeft).isEqualTo(10);
    }

    @Test
    @DisplayName("Сдвиг влево. Сдвиг нуля. Ввод: (0, 6), вывод: 0.")
    void rotateLeftZeroNumber() {
        // given
        int number = 0;
        int shift = 6;

        // when
        int rotatedLeft = CyclicBitShift.rotateLeft(number, shift);

        // then
        assertThat(rotatedLeft).isEqualTo(0);
    }

    @Test
    @DisplayName("Сдвиг влево. Отрицательное число. Ввод: (-25, 4), вывод: -1.")
    void rotateLeftNegNumber() {
        // given
        int number = -25;
        int shift = 4;

        // when
        int rotatedLeft = CyclicBitShift.rotateLeft(number, shift);

        // then
        assertThat(rotatedLeft).isEqualTo(-1);
    }

    @Test
    @DisplayName("Сдвиг влево. Отрицательный сдвиг. Ввод: (8, -1), вывод: 4.")
    void rotateLeftNegShift() {
        // given
        int number = 8;
        int shift = -1;

        // when
        int rotatedLeft = CyclicBitShift.rotateLeft(number, shift);

        // then
        assertThat(rotatedLeft).isEqualTo(4);
    }
}
