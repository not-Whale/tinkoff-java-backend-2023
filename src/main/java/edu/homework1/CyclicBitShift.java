package edu.homework1;

import java.util.Arrays;

public final class CyclicBitShift {
    private CyclicBitShift() {}

    public static int rotateLeft(int n, int shift) {
        if (n < 0) {
            return -1;
        }

        if (shift < 0) {
            return rotateRight(n, -shift);
        }

        String[] binaryN = Integer.toBinaryString(n).split("");

        // двигаем влево через отображение правого сдвига
        binaryN = reverseArray(binaryN);
        binaryN = rightShift(binaryN, shift);
        binaryN = reverseArray(binaryN);

        return binaryStringToNumber(binaryN);
    }

    public static int rotateRight(int n, int shift) {
        if (n < 0) {
            return -1;
        }

        if (shift < 0) {
            return rotateLeft(n, -shift);
        }

        String[] binaryN = Integer.toBinaryString(n).split("");

        binaryN = rightShift(binaryN, shift);
        return binaryStringToNumber(binaryN);
    }

    private static String[] rightShift(String[] binaryArray, int shift) {
        int len = binaryArray.length;
        String[] shiftedArray = Arrays.copyOf(binaryArray, len);

        for (int i = 0; i < len; i++) {
            shiftedArray[(i + shift) % len] = binaryArray[i];
        }

        return shiftedArray;
    }

    private static String[] reverseArray(String[] array) {
        int len = array.length;
        String[] reversedArray = new String[len];

        for (int i = 0; i < len; i++) {
            reversedArray[len - 1 - i] = array[i];
        }

        return reversedArray;
    }

    private static int binaryStringToNumber(String[] binaryString) {
        int number = 0;
        int len = binaryString.length;

        for (int i = 0; i < len; i++) {
            number += (int) (Integer.parseInt(binaryString[len - 1 - i]) * Math.pow(2, i));
        }

        return number;
    }
}
