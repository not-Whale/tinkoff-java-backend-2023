package edu.homework5;

public class RegexEx {
    private RegexEx() {}

    // Строка содержит не менее 3 символов, причем третий символ равен 0.
    public static boolean task1(String s) {
        String regex = "^[01]{2}0[01]*";
        return s.matches(regex);
    }

    // Строка начинается и заканчивается одним и тем же символом.
    public static boolean task2(String s) {
        String regex = "(^0(1*0)*$)|(^1(0*1)*$)";
        return s.matches(regex);
    }

    // Длина строки не менее 1 и не более 3
    public static boolean task3(String s) {
        String regex = "^[01]{1,3}$";
        return s.matches(regex);
    }
}
