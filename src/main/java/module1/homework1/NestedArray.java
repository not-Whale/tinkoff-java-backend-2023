package module1.homework1;

public class NestedArray {
    private NestedArray() {}

    public static boolean isNestable(int[] first, int[] second) {
        if (first == null || second == null) {
            return false;
        }

        if (first.length == 0) {
            return false;
        }

        if (second.length == 0) {
            return true;
        }

        int firstMin = getMin(first);
        int firstMax = getMax(first);

        int secondMin = getMin(second);
        int secondMax = getMax(second);

        return firstMin > secondMin && firstMax < secondMax;
    }

    private static int getMin(int[] array) {
        int min = array[0];

        for (int elem : array) {
            if (elem < min) {
                min = elem;
            }
        }

        return min;
    }

    private static int getMax(int[] array) {
        int max = array[0];

        for (int elem : array) {
            if (elem > max) {
                max = elem;
            }
        }

        return max;
    }
}
