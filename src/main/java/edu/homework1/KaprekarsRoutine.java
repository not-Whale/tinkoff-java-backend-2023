package edu.homework1;

import java.util.Arrays;

public final class KaprekarsRoutine {
    private KaprekarsRoutine() {}

    public static int countKaprekar(int number) {
        if (number < 1001 || number > 9998) {
            return -1;
        }

        return countKaprekarRec(number, 0);
    }

    private static int countKaprekarRec(int number, int count) {
        // условие выхода из рекурсии
        if (number == 6174) {
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

    private static int[] numberToDigitArray(int number) {
        int[] digitArray = new int[4];
        int currentNumber = number;

        int idx = 0;
        while (currentNumber != 0) {
            digitArray[3 - idx] = currentNumber % 10;
            currentNumber /= 10;
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
