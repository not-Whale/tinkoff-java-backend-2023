package edu.homework1;

public final class NumberOfDigits {
    private NumberOfDigits() {}

    @SuppressWarnings("MagicNumber")
    public static int countDigits(int inputNumber) {
        int numberOfDigits = 0;
        int currentNumber = inputNumber;

        while (currentNumber != 0) {
            currentNumber /= 10;
            numberOfDigits++;
        }

        return numberOfDigits;
    }
}
