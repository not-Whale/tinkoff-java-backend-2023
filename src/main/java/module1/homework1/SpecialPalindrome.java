package module1.homework1;

public class SpecialPalindrome {
    private SpecialPalindrome() {}

    public static boolean isPalindromeDescendant(int number) {
        return isPalindromeDescendantRec(numberToDigitArray(number));
    }

    private static boolean isPalindromeDescendantRec(int[] digitArray) {
        boolean isPalindrome = isPalindrome(digitArray);

        // выход как только нашли
        if (isPalindrome) {
            return isPalindrome;
        }

        // конец рекурсии
        if (digitArray.length % 2 == 1 || getDescendant(digitArray).length < 2) {
            return isPalindrome;
        }

        // шаг рекурсии
        return isPalindromeDescendantRec(getDescendant(digitArray));
    }

    private static int[] getDescendant(int[] parent) {
        int descendantLen = parent.length;

        for (int i = 0; i < parent.length; i += 2) {
            if (parent[i] + parent[i + 1] < 10) {
                descendantLen--;
            }
        }

        int idx = 0;
        int[] descendant = new int[descendantLen];

        for (int i = 0; i < parent.length; i += 2) {
            int newValue = parent[i] + parent[i + 1];
            if (newValue < 10) {
                descendant[idx++] = newValue;
            } else {
                descendant[idx++] = newValue / 10;
                descendant[idx++] = newValue % 10;
            }
        }

        return descendant;
    }

    private static boolean isPalindrome(int[] digitArray) {
        int len = digitArray.length;
        int med = len / 2;

        for (int i = 0; i < med; i++) {
            if (digitArray[i] != digitArray[len - 1 - i]) {
                return false;
            }
        }

        return true;
    }

    private static int[] numberToDigitArray(int number) {
        int len = NumberOfDigits.countDigits(number);
        int[] digitArray = new int[len];

        int idx = 0;
        while (number != 0) {
            digitArray[len - 1 - idx] = number % 10;
            number /= 10;
            idx++;
        }

        return digitArray;
    }
}
