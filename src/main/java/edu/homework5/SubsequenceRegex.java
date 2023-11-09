package edu.homework5;

public class SubsequenceRegex {
    private SubsequenceRegex() {}

    public static boolean isSubsequence(String sequence, String subsequence) {
        StringBuilder stringBuilder = new StringBuilder("^.*");
        for (String symbol : subsequence.split("")) {
            stringBuilder.append(symbol).append(".*");
        }
        stringBuilder.append("$");
        String regex = stringBuilder.toString();
        return sequence.matches(regex);
    }
}
