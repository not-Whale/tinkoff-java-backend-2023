package edu.homework2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Калькулятор выражений")
public class ExprTest {
    @Test
    @DisplayName("Передача числа в конструктор константы")
    void constantFromNumber() {
        // given
        Expr.Constant constant = new Expr.Constant(15.2);

        // when
        double eval = constant.evaluate();

        // then
        assertThat(eval).isEqualTo(15.2);
    }

    @Test
    @DisplayName("Передача числа в конструктор отрицания")
    void negateFromNumber() {
        // given
        Expr.Negate negate = new Expr.Negate(6);

        // when
        double eval = negate.evaluate();

        // then
        assertThat(eval).isEqualTo(-6);
    }

    @Test
    @DisplayName("Передача двух чисел в конструктор возведения в степень")
    void exponentFromNumbers() {
        // given
        Expr.Exponent exponent = new Expr.Exponent(2, 6);

        // when
        double eval = exponent.evaluate();

        // then
        assertThat(eval).isEqualTo(64);
    }

    @Test
    @DisplayName("Передача двух чисел в конструктор сложения")
    void additionFromNumbers() {
        // given
        Expr.Addition addition = new Expr.Addition(2, 2);

        // when
        double eval = addition.evaluate();

        // then
        assertThat(eval).isEqualTo(4);
    }

    @Test
    @DisplayName("Передача двух чисел в конструктор вычитания")
    void subtractionFromNumbers() {
        // given
        Expr.Subtraction subtraction = new Expr.Subtraction(10, 5);

        // when
        double eval = subtraction.evaluate();

        // then
        assertThat(eval).isEqualTo(5);
    }

    @Test
    @DisplayName("Передача двух чисел в конструктор умножения")
    void multiplicationFromNumbers() {
        // given
        Expr.Multiplication multiplication = new Expr.Multiplication(8, 3);

        // when
        double eval = multiplication.evaluate();

        // then
        assertThat(eval).isEqualTo(24);
    }

    @Test
    @DisplayName("Передача двух чисел в конструктор деления")
    void divisionFromNumbers() {
        // given
        Expr.Division division = new Expr.Division(51, 3);

        // when
        double eval = division.evaluate();

        // then
        assertThat(eval).isEqualTo(17);
    }

    @Test
    @DisplayName("Передача числа в базу в возведении в степень")
    void exponentBaseFromNumber() {
        // given
        Expr.Exponent exponent = new Expr.Exponent(3, new Expr.Constant(4));

        // when
        double eval = exponent.evaluate();

        // then
        assertThat(eval).isEqualTo(81);
    }

    @Test
    @DisplayName("Передача числа в степень в возведении в степень")
    void exponentPowerFromNumber() {
        // given
        Expr.Exponent exponent = new Expr.Exponent(new Expr.Constant(3), 4);

        // when
        double eval = exponent.evaluate();

        // then
        assertThat(eval).isEqualTo(81);
    }

    @Test
    @DisplayName("Передача числа в первое слагаемое суммы")
    void additionLeftTermFromNumber() {
        // given
        Expr.Addition addition = new Expr.Addition(10, new Expr.Constant(20));

        // when
        double eval = addition.evaluate();

        // then
        assertThat(eval).isEqualTo(30);
    }

    @Test
    @DisplayName("Передача числа во второе слагаемое суммы")
    void additionRightTermFromNumber() {
        // given
        Expr.Addition addition = new Expr.Addition(new Expr.Constant(10), 20);

        // when
        double eval = addition.evaluate();

        // then
        assertThat(eval).isEqualTo(30);
    }

    @Test
    @DisplayName("Передача числа в первый операнд вычитания")
    void subtractionLeftTermFromNumber() {
        // given
        Expr.Subtraction subtraction = new Expr.Subtraction(5, new Expr.Constant(2));

        // when
        double eval = subtraction.evaluate();

        // then
        assertThat(eval).isEqualTo(3);
    }

    @Test
    @DisplayName("Передача числа во второй операнд вычитания")
    void subtractionRightTermFromNumber() {
        // given
        Expr.Subtraction subtraction = new Expr.Subtraction(new Expr.Constant(5), 2);

        // when
        double eval = subtraction.evaluate();

        // then
        assertThat(eval).isEqualTo(3);
    }

    @Test
    @DisplayName("Передача числа в первый операнд умножения")
    void multiplicationLeftMulFromNumber() {
        // given
        Expr.Multiplication multiplication = new Expr.Multiplication(9, new Expr.Constant(11));

        // when
        double eval = multiplication.evaluate();

        // then
        assertThat(eval).isEqualTo(99);
    }

    @Test
    @DisplayName("Передача числа во второй операнд умножения")
    void multiplicationRightMulFromNumber() {
        // given
        Expr.Multiplication multiplication = new Expr.Multiplication(new Expr.Constant(9), 11);

        // when
        double eval = multiplication.evaluate();

        // then
        assertThat(eval).isEqualTo(99);
    }

    @Test
    @DisplayName("Передача числа в первый операнд деления")
    void divisionLeftMulFromNumber() {
        // given
        Expr.Division division = new Expr.Division(82, new Expr.Constant(41));

        // when
        double eval = division.evaluate();

        // then
        assertThat(eval).isEqualTo(2);
    }

    @Test
    @DisplayName("Передача числа во второй операнд деления")
    void divisionRightMulFromNumber() {
        // given
        Expr.Division division = new Expr.Division(new Expr.Constant(82), 41);

        // when
        double eval = division.evaluate();

        // then
        assertThat(eval).isEqualTo(2);
    }

    @Test
    @DisplayName("Передача объекта Expr в конструктор константы")
    void constantFromExpr() {
        // given
        Expr.Constant constant = new Expr.Constant(new Expr.Addition(2, 2));

        // when
        double eval = constant.evaluate();

        // then
        assertThat(eval).isEqualTo(4);
    }
}
