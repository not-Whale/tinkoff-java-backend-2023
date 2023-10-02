package module1.homework1;

public class NumberOfDigits {
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
