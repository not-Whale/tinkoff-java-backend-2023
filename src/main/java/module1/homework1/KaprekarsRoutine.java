package module1.homework1;

import java.util.Arrays;

public class KaprekarsRoutine {
    private KaprekarsRoutine() {}

    public static int countKaprekar(int number) {
        return countKaprekarRec(number, 0);
    }

    private static int countKaprekarRec(int number, int count) {
        // условие выхода из рекурсии
        if (number == 6174) {
            return count;
        }

        // получение возрастающего числа
        int[] digitArray = numberToDigitArray(number);
        Arrays.sort(digitArray);
        int increaseSeqNumber = digitArrayToNumber(digitArray);

        // получение убывающего числа
        digitArray = reverseArray(digitArray);
        int decreaseSeqNumber = digitArrayToNumber(digitArray);

        // шаг рекурсии
        return countKaprekarRec(decreaseSeqNumber - increaseSeqNumber, ++count);
    }

    private static int[] reverseArray(int[] array) {
        int len = array.length;
        int[] reversedArray = new int[len];

        for (int i = 0; i < len; i++) {
            reversedArray[len - 1 - i] = array[i];
        }

        return reversedArray;
    }

    private static int[] numberToDigitArray(int number) {
        int[] digitArray = new int[4];

        int idx = 0;
        while (number != 0) {
            digitArray[3 - idx] = number % 10;
            number /= 10;
            idx++;
        }

        return digitArray;
    }

    private static int digitArrayToNumber(int[] digitArray) {
        int number = 0;
        int len = digitArray.length;

        for (int i = 0; i < len; i++) {
            number += (int) (digitArray[len - 1 - i] * Math.pow(10, i));
        }

        return number;
    }
}
