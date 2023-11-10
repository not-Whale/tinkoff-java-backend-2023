package edu.homework5;

public class PasswordValidator {
    private PasswordValidator() {}

    public static boolean hasOnlyOneSymbol(String password) {
        String regex = "^(\\w)*[~!@#$%^&*|](\\w)*$";
        return password.matches(regex);
    }

    public static boolean hasAtLeastOneSymbol(String password) {
        String regex = "((\\w)*[~!@#$%^&*|](\\w)*)*";
        return password.matches(regex);
    }
}
