package module1.homework1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("Определение, могут ли кони бить друг друга из текущего положения.")
public class KnightsTest {
    @Test
    @DisplayName("Кони не пересекаются. Вывод: true.")
    void knightBoardCapturePassedTrue() {
        // given
        int[][] board = new int[][] {
            {0, 0, 0, 1, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 0, 0, 0, 1, 0, 0},
            {0, 0, 0, 0, 1, 0, 1, 0},
            {0, 1, 0, 0, 0, 1, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 0, 0, 0, 0, 0, 1},
            {0, 0, 0, 0, 1, 0, 0, 0}
        };

        // when
        boolean isCaptured = Knights.knightBoardCapture(board);

        // then
        assertThat(isCaptured).isEqualTo(true);
    }

    @Test
    @DisplayName("Кони пересекаются. Вывод: false.")
    void knightBoardCapturePassedFalse() {
        // given
        int[][] board = new int[][] {
            {1, 0, 1, 0, 1, 0, 1, 0},
            {0, 1, 0, 1, 0, 1, 0, 1},
            {0, 0, 0, 0, 1, 0, 1, 0},
            {0, 0, 1, 0, 0, 1, 0, 1},
            {1, 0, 0, 0, 1, 0, 1, 0},
            {0, 0, 0, 0, 0, 1, 0, 1},
            {1, 0, 0, 0, 1, 0, 1, 0},
            {0, 0, 0, 1, 0, 1, 0, 1}
        };

        // when
        boolean isCaptured = Knights.knightBoardCapture(board);

        // then
        assertThat(isCaptured).isEqualTo(false);
    }

    @Test
    @DisplayName("Пустое поле. Вывод: true.")
    void knightBoardCaptureAllZeros() {
        // given
        int[][] board = new int[][] {
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0}
        };

        // when
        boolean isCaptured = Knights.knightBoardCapture(board);

        // then
        assertThat(isCaptured).isEqualTo(true);
    }

    @Test
    @DisplayName("На всех клетках кони. Вывод: false.")
    void knightBoardCaptureAllOnes() {
        // given
        int[][] board = new int[][] {
            {1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1}
        };

        // when
        boolean isCaptured = Knights.knightBoardCapture(board);

        // then
        assertThat(isCaptured).isEqualTo(false);
    }

    @Test
    @DisplayName("Вложенный массив имеет длину меньше 8. Ожидается IllegalArgumentException.")
    void knightBoardCaptureLineLessThan8() {
        // given
        int[][] board = new int[][] {
            {1, 0, 1, 0, 1, 0, 1, 0},
            {0, 1},
            {1, 0, 1, 0, 1, 0, 1, 0},
            {0, 1, 0, 1, 0, 1, 0, 1},
            {1, 0, 1, 0, 1, 0, 1, 0},
            {0, 1, 0, 1, 0, 1, 0, 1},
            {1, 0, 1, 0, 1, 0, 1, 0},
            {0, 1, 0, 1, 0, 1, 0, 1}
        };

        // when
        Exception exception = Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> Knights.knightBoardCapture(board)
        );

        // then
        assertThat(exception.getMessage()).isEqualTo("Null or incorrect size board!");
    }

    @Test
    @DisplayName("Вложенный массив имеет длину больше 8. Ожидается IllegalArgumentException.")
    void knightBoardCaptureLineMoreThan8() {
        // given
        int[][] board = new int[][] {
            {1, 0, 1, 0, 1, 0, 1, 0},
            {0, 1, 0, 1, 0, 1, 0, 1},
            {1, 0, 1, 0, 1, 0, 1, 0},
            {0, 1, 0, 1, 0, 1, 0, 1, 0, 1},
            {1, 0, 1, 0, 1, 0, 1, 0},
            {0, 1, 0, 1, 0, 1, 0, 1},
            {1, 0, 1, 0, 1, 0, 1, 0},
            {0, 1, 0, 1, 0, 1, 0, 1}
        };

        // when
        Exception exception = Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> Knights.knightBoardCapture(board)
        );

        // then
        assertThat(exception.getMessage()).isEqualTo("Null or incorrect size board!");
    }

    @Test
    @DisplayName("Вложенный массив пустой. Ожидается IllegalArgumentException.")
    void knightBoardCaptureEmptyLine() {
        // given
        int[][] board = new int[][] {
            {1, 0, 1, 0, 1, 0, 1, 0},
            {0, 1, 0, 1, 0, 1, 0, 1},
            {1, 0, 1, 0, 1, 0, 1, 0},
            {0, 1, 0, 1, 0, 1, 0, 1},
            {1, 0, 1, 0, 1, 0, 1, 0},
            {},
            {1, 0, 1, 0, 1, 0, 1, 0},
            {0, 1, 0, 1, 0, 1, 0, 1}
        };

        // when
        Exception exception = Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> Knights.knightBoardCapture(board)
        );

        // then
        assertThat(exception.getMessage()).isEqualTo("Null or incorrect size board!");
    }

    @Test
    @DisplayName("Вложенный null-массив. Ожидается IllegalArgumentException.")
    void knightBoardCaptureNullLine() {
        // given
        int[][] board = new int[][] {
            {1, 0, 1, 0, 1, 0, 1, 0},
            {0, 1, 0, 1, 0, 1, 0, 1},
            null,
            {0, 1, 0, 1, 0, 1, 0, 1},
            {1, 0, 1, 0, 1, 0, 1, 0},
            {0, 1, 0, 1, 0, 1, 0, 1},
            {1, 0, 1, 0, 1, 0, 1, 0},
            {0, 1, 0, 1, 0, 1, 0, 1}
        };

        // when
        Exception exception = Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> Knights.knightBoardCapture(board)
        );

        // then
        assertThat(exception.getMessage()).isEqualTo("Null or incorrect size board!");
    }

    @Test
    @DisplayName("Количество столбцов меньше 8. Ожидается IllegalArgumentException.")
    void knightBoardCaptureRowsLessThan8() {
        // given
        int[][] board = new int[][] {
            {1, 0, 1, 0, 1, 0, 1, 0},
            {0, 1, 0, 1, 0, 1, 0, 1},
            {1, 0, 1, 0, 1, 0, 1, 0},
            {0, 1, 0, 1, 0, 1, 0, 1},
            {1, 0, 1, 0, 1, 0, 1, 0},
            {0, 1, 0, 1, 0, 1, 0, 1}
        };

        // when
        Exception exception = Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> Knights.knightBoardCapture(board)
        );

        // then
        assertThat(exception.getMessage()).isEqualTo("Null or incorrect size board!");
    }

    @Test
    @DisplayName("Количество столбцов больше 8. Ожидается IllegalArgumentException.")
    void knightBoardCaptureRowsMoreThan8() {
        // given
        int[][] board = new int[][] {
            {1, 0, 1, 0, 1, 0, 1, 0},
            {0, 1, 0, 1, 0, 1, 0, 1},
            {1, 0, 1, 0, 1, 0, 1, 0},
            {0, 1, 0, 1, 0, 1, 0, 1},
            {1, 0, 1, 0, 1, 0, 1, 0},
            {0, 1, 0, 1, 0, 1, 0, 1},
            {1, 0, 1, 0, 1, 0, 1, 0},
            {0, 1, 0, 1, 0, 1, 0, 1},
            {1, 0, 1, 0, 1, 0, 1, 0},
            {0, 1, 0, 1, 0, 1, 0, 1}
        };

        // when
        Exception exception = Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> Knights.knightBoardCapture(board)
        );

        // then
        assertThat(exception.getMessage()).isEqualTo("Null or incorrect size board!");
    }

    @Test
    @DisplayName("Null-массив вместо доски. Ожидается IllegalArgumentException.")
    void knightBoardCaptureNullBoard() {
        // given
        int[][] board = null;

        // when
        Exception exception = Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> Knights.knightBoardCapture(board)
        );

        // then
        assertThat(exception.getMessage()).isEqualTo("Null or incorrect size board!");
    }
}
