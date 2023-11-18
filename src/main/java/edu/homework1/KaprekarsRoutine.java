package edu.homework1;

import java.util.Arrays;

public final class KaprekarsRoutine {
    private static final int MIN_VALUE = 1001;
    private static final int MAX_VALUE = 9998;
    private static final int KAPREKARS_CONSTANT = 6174;
    private static final int NUMBER_LENGTH = 4;

    private KaprekarsRoutine() {}

    public static int countKaprekar(int number) {
        if (number < MIN_VALUE || number > MAX_VALUE) {
            return -1;
        }

        return countKaprekarRec(number, 0);
    }

    private static int countKaprekarRec(int number, int count) {
        // условие выхода из рекурсии
        if (number == KAPREKARS_CONSTANT) {
            return count;
        }

        int[] digitArray = numberToDigitArray(number);

        // проверка на то что не все цифры одинаковые
        boolean isEqual = true;
        for (int i = 0; i < digitArray.length; i++) {
            if (digitArray[i] != digitArray[(i + 1) % digitArray.length]) {
                isEqual = false;
                break;
            }
        }
        if (isEqual) {
            return -1;
        }

        // получение возрастающего числа
        Arrays.sort(digitArray);
        int increaseSeqNumber = digitArrayToNumber(digitArray);

        // получение убывающего числа
        digitArray = reverseArray(digitArray);
        int decreaseSeqNumber = digitArrayToNumber(digitArray);

        // шаг рекурсии
        return countKaprekarRec(decreaseSeqNumber - increaseSeqNumber, count + 1);
    }

    private static int[] reverseArray(int[] array) {
        int len = array.length;
        int[] reversedArray = new int[len];

        for (int i = 0; i < len; i++) {
            reversedArray[len - 1 - i] = array[i];
        }

        return reversedArray;
    }

    @SuppressWarnings("MagicNumber")
    private static int[] numberToDigitArray(int number) {
        int[] digitArray = new int[NUMBER_LENGTH];
        int currentNumber = number;

        int idx = 0;
        while (currentNumber != 0) {
            digitArray[NUMBER_LENGTH - 1 - idx] = currentNumber % 10;
            currentNumber /= 10;
            idx++;
        }

        return digitArray;
    }

    @SuppressWarnings("MagicNumber")
    private static int digitArrayToNumber(int[] digitArray) {
        int number = 0;
        int len = digitArray.length;

        for (int i = 0; i < len; i++) {
            number += (int) (digitArray[len - 1 - i] * Math.pow(10, i));
        }

        return number;
    }
}
