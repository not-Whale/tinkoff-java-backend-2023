package edu.homework5;

public class SubstringRegex {
    private SubstringRegex() {}

    public static boolean isSubstring(String string, String substring) {
        String regex = "^.*" + substring + ".*$";
        return string.matches(regex);
    }
}
