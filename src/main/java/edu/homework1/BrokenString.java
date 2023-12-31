package edu.homework1;

public final class BrokenString {
    private BrokenString() {}

    public static String fixString(String brokenString) throws IllegalArgumentException {
        if (brokenString == null) {
            throw new IllegalArgumentException("Null string!");
        }

        int len = brokenString.length();

        StringBuilder fixedString = new StringBuilder();

        int idx = 0;
        while (idx < len - 1) {
            fixedString
                .append(brokenString.charAt(idx + 1))
                .append(brokenString.charAt(idx));
            idx += 2;
        }

        // если строка нечетная после цикла idx = len - 1
        // таким образом записывается последний символ
        if (idx < len) {
            fixedString.append(brokenString.charAt(idx));
        }

        return fixedString.toString();
    }
}
