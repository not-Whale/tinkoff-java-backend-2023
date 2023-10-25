package edu.homework3.parenthesis_cluster;

import java.util.ArrayList;
import java.util.Stack;

public class ParenthesisCluster {
    private static final Character LEFT_PAREN = '(';

    private static final Character RIGHT_PAREN = ')';

    private ParenthesisCluster() {}

    public static String[] clusterize(String parentSeq) throws IllegalArgumentException {
        if (parentSeq == null) {
            throw new IllegalArgumentException();
        }

        Stack<Integer> stack = new Stack<>();
        String inputParentSeq = parentSeq.strip();
        StringBuilder currentElem = new StringBuilder();
        ArrayList<String> clusteredParenSeq = new ArrayList<>();

        for (Character elem : inputParentSeq.toCharArray()) {
            if (elem.equals(LEFT_PAREN)) {
                stack.push(1);
                currentElem.append(LEFT_PAREN);
            }

            if (elem.equals(RIGHT_PAREN)) {
                if (stack.empty()) {
                    return null;
                }
                stack.pop();
                currentElem.append(RIGHT_PAREN);
            }

            if (stack.empty()) {
                clusteredParenSeq.add(currentElem.toString());
                currentElem = new StringBuilder();
            }
        }

        if (!stack.empty()) {
            return null;
        }

        return clusteredParenSeq.toArray(new String[0]);
    }
}
