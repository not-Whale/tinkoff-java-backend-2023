package edu.homework1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("Проверка на вложенность массивов.")
public class NestedArrayTest {
    @Test
    @DisplayName("Happy path. Ввод: ([1, 2, 3, 4], [0, 6]), вывод: true")
    void isNestablePassedTrue() {
        // given
        int[] firstArray = new int[]{1, 2, 3, 4};
        int[] secondArray = new int[]{0, 6};

        // when
        boolean isNestable = NestedArray.isNestable(firstArray, secondArray);

        // then
        assertThat(isNestable).isEqualTo(true);

    }

    @Test
    @DisplayName("Happy path. Ввод: ([5, 7, 9], [6, 8]), вывод: false")
    void isNestablePassedFalse() {
        // given
        int[] firstArray = new int[]{5, 7, 9};
        int[] secondArray = new int[]{6, 8};

        // when
        boolean isNestable = NestedArray.isNestable(firstArray, secondArray);

        // then
        assertThat(isNestable).isEqualTo(false);

    }

    @Test
    @DisplayName("Одинаковые массивы. Ввод: ([1, 2, 3, 4, 5], [1, 2, 3, 4, 5]), вывод: false")
    void isNestableEqualArrays() {
        // given
        int[] firstArray = new int[]{1, 2, 3, 4, 5};
        int[] secondArray = new int[]{1, 2, 3, 4, 5};

        // when
        boolean isNestable = NestedArray.isNestable(firstArray, secondArray);

        // then
        assertThat(isNestable).isEqualTo(false);

    }

    @Test
    @DisplayName("Одинаковые минимум и максимум. Ввод: ([0, 3, 4, 5], [0, 1, 2, 5]), вывод: false")
    void isNestableEqualMinMax() {
        // given
        int[] firstArray = new int[]{0, 3, 4, 5};
        int[] secondArray = new int[]{0, 1, 2, 5};

        // when
        boolean isNestable = NestedArray.isNestable(firstArray, secondArray);

        // then
        assertThat(isNestable).isEqualTo(false);

    }

    @Test
    @DisplayName("Пустой первый массив. Ввод: ([], [0, 3]), вывод: false")
    void isNestableEmptyFirstArray() {
        // given
        int[] firstArray = new int[]{};
        int[] secondArray = new int[]{0, 3};

        // when
        boolean isNestable = NestedArray.isNestable(firstArray, secondArray);

        // then
        assertThat(isNestable).isEqualTo(false);

    }

    @Test
    @DisplayName("Пустой второй массив. Ввод: ([1, 2, 3], []), вывод: true")
    void isNestableEmptySecondArray() {
        // given
        int[] firstArray = new int[]{1, 2, 3};
        int[] secondArray = new int[]{};

        // when
        boolean isNestable = NestedArray.isNestable(firstArray, secondArray);

        // then
        assertThat(isNestable).isEqualTo(true);

    }

    @Test
    @DisplayName("Первый массив не задан. Ввод: (null, [0, 5]). Ожидается IllegalArgumentException.")
    void isNestableNullFirstArray() {
        // given
        int[] firstArray = null;
        int[] secondArray = new int[]{0, 5};

        // when
        Exception exception = Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> NestedArray.isNestable(firstArray, secondArray)
        );

        // then
        assertThat(exception.getMessage()).isEqualTo("Null array!");
    }

    @Test
    @DisplayName("Второй массив не задан. Ввод: ([10, 20], null). Ожидается IllegalArgumentException.")
    void isNestableNullSecondArray() {
        // given
        int[] firstArray = new int[]{10, 20};
        int[] secondArray = null;

        // when
        Exception exception = Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> NestedArray.isNestable(firstArray, secondArray)
        );

        // then
        assertThat(exception.getMessage()).isEqualTo("Null array!");
    }
}
