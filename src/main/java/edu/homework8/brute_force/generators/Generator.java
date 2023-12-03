package edu.homework8.brute_force.generators;

import java.util.List;

public class Generator {
    private final List<String> alphabet = List.of(
        "A", "B", "C", "D", "E", "F", "G", "H", "I", "K",
        "L", "M", "N", "O", "P", "Q", "R", "S", "T", "V",
        "X", "Y", "Z", "0", "1", "2", "3", "4", "5", "6",
        "7", "8", "9"
    );

    private final int alphabetSize = alphabet.size();

    private final int passwordSize;

    private final int[] positions;

    public Generator(int passwordSize) {
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
