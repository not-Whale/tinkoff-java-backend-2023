package edu.homework5.regex_excercies;

public class RegexExPro {
    private RegexExPro() {}

    // Строка нечетной длины
    public static boolean task1(String s) {
        String regex = "^[01]([01]{2})*$";
        return s.matches(regex);
    }

    // Строка начинается с 0 и имеет нечетную длину, или начинается с 1 и имеет четную длину
    public static boolean task2(String s) {
        String regex = "(^0([01]{2})*$)|(^1[01]([01]{2})*$)";
        return s.matches(regex);
    }

    // Количество 0 в строке кратно 3
    public static boolean task3(String s) {
        String regex = "^((1*01*){3})*$";
        return s.matches(regex);
    }

    // Любая строка, кроме 11 или 111
    public static boolean task4(String s) {
        String regex = "^(?!(11|111)$)[01]*$";
        return s.matches(regex);

        // return !s.matches("^11|111$");
    }

    // Каждый нечетный символ строки равен 1
    public static boolean task5(String s) {
        String regex = "^1([01]1)*[01]?$";
        return s.matches(regex);
    }

    // Строка содержит не менее двух 0 и не более одной 1
    public static boolean task6(String s) {
        String regex = "^(100+|0+10+|0+01|00+)$";
        return s.matches(regex);
    }

    // В строке нет последовательных 1
    public static boolean task7(String s) {
        String regex = "^0*(10+)*1?$";
        return s.matches(regex);

        // return s.matches("^(?![01]*11)";
    }
}
