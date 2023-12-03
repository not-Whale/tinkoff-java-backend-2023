package edu.homework8.brute_force.generators;

import java.util.List;

public class Generator {
    private final List<String> alphabet;

    private final int alphabetSize;

    private final int passwordSize;

    private final int[] positions;

    public Generator(List<String> alphabet, int passwordSize) {
        this.alphabet = alphabet;
        this.alphabetSize = alphabet.size();
        this.passwordSize = passwordSize;
        positions = new int[passwordSize];
    }

    public boolean hasNext() {
        for (int i = 0; i < passwordSize; i++) {
            if (positions[i] >= alphabetSize) {
                return false;
            }
        }
        return true;
    }

    public String getNext() {
        if (!hasNext()) {
            return null;
        }
        String password = getCombination();
        int i = passwordSize - 1;
        positions[i] += 1;
        while (positions[i] > alphabetSize - 1 && i > 0) {
            positions[i] = 0;
            i -= 1;
            positions[i] += 1;
        }
        return password;
    }

    private String getCombination() {
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < passwordSize; i++) {
            password.append(alphabet.get(positions[i]));
        }
        return password.toString();
    }
}
