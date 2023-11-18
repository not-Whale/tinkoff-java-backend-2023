package edu.homework3.parenthesis_cluster;

import java.util.ArrayList;

public class ParenthesisCluster {
    private static final Character LEFT_PAREN = '(';

    private static final Character RIGHT_PAREN = ')';

    private ParenthesisCluster() {}

    public static String[] clusterize(String parentSeq) throws IllegalArgumentException {
        if (parentSeq == null) {
            throw new IllegalArgumentException();
        }

        long balance = 0;
        String inputParentSeq = parentSeq.strip();
        StringBuilder currentElem = new StringBuilder();
        ArrayList<String> clusteredParenSeq = new ArrayList<>();

        for (Character elem : inputParentSeq.toCharArray()) {
            if (elem.equals(LEFT_PAREN)) {
                currentElem.append(LEFT_PAREN);
                balance++;
            }

            if (elem.equals(RIGHT_PAREN)) {
                if (balance == 0) {
                    return new String[0];
                }
                currentElem.append(RIGHT_PAREN);
                balance--;
            }

            if (balance == 0) {
                clusteredParenSeq.add(currentElem.toString());
                currentElem = new StringBuilder();
            }
        }

        if (balance != 0) {
            return new String[0];
        }

        return clusteredParenSeq.toArray(new String[0]);
    }
}
