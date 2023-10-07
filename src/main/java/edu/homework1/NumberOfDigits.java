package edu.homework1;

public final class NumberOfDigits {
    private NumberOfDigits() {}

    public static int countDigits(int inputNumber) {
        int numberOfDigits = 0;

        while (inputNumber != 0) {
            inputNumber /= 10;
            numberOfDigits++;
        }

        return numberOfDigits;
    }
}
