package edu.homework5;

public class PasswordValidator {
    private PasswordValidator() {}

    public static boolean validate(String password) {
        String regex = "^(\\w)*[~!@#$%^&*|](\\w)*$";
        return password.matches(regex);
    }
}
