package edu.homework3;

import java.util.HashMap;

public class AtbashCipher {
    private static final HashMap<String, String> UPPERCASE_ALPHABET;

    private static final HashMap<String, String> LOWERCASE_ALPHABET;

    static {
        UPPERCASE_ALPHABET = new HashMap<>();
        UPPERCASE_ALPHABET.put("A", "Z");
        UPPERCASE_ALPHABET.put("B", "Y");
        UPPERCASE_ALPHABET.put("C", "X");
        UPPERCASE_ALPHABET.put("D", "W");
        UPPERCASE_ALPHABET.put("E", "V");
        UPPERCASE_ALPHABET.put("F", "U");
        UPPERCASE_ALPHABET.put("G", "T");
        UPPERCASE_ALPHABET.put("H", "S");
        UPPERCASE_ALPHABET.put("I", "R");
        UPPERCASE_ALPHABET.put("J", "Q");
        UPPERCASE_ALPHABET.put("K", "P");
        UPPERCASE_ALPHABET.put("L", "O");
        UPPERCASE_ALPHABET.put("M", "N");
        UPPERCASE_ALPHABET.put("N", "M");
        UPPERCASE_ALPHABET.put("O", "L");
        UPPERCASE_ALPHABET.put("P", "K");
        UPPERCASE_ALPHABET.put("Q", "J");
        UPPERCASE_ALPHABET.put("R", "I");
        UPPERCASE_ALPHABET.put("S", "H");
        UPPERCASE_ALPHABET.put("T", "G");
        UPPERCASE_ALPHABET.put("U", "F");
        UPPERCASE_ALPHABET.put("V", "E");
        UPPERCASE_ALPHABET.put("W", "D");
        UPPERCASE_ALPHABET.put("X", "C");
        UPPERCASE_ALPHABET.put("Y", "B");
        UPPERCASE_ALPHABET.put("Z", "A");

        LOWERCASE_ALPHABET = new HashMap<>();
        generateUppercaseMap();
    }

    private static void generateUppercaseMap() {
        for (var key : UPPERCASE_ALPHABET.keySet()) {
            LOWERCASE_ALPHABET.put(key.toLowerCase(), UPPERCASE_ALPHABET.get(key).toLowerCase());
        }
    }

    private AtbashCipher() {}

    public static String decodeWordWithMap(String inputWord) throws IllegalArgumentException {
        if (inputWord == null) {
            throw new IllegalArgumentException();
        }

        StringBuilder outputWord = new StringBuilder();

        for (Character currentCharacter : inputWord.toCharArray()) {
            if (UPPERCASE_ALPHABET.get(currentCharacter.toString()) != null) {
                outputWord.append(UPPERCASE_ALPHABET.get(currentCharacter.toString()));
            } else if (LOWERCASE_ALPHABET.get(currentCharacter.toString()) != null) {
                outputWord.append(LOWERCASE_ALPHABET.get(currentCharacter.toString()));
            } else {
                outputWord.append(currentCharacter);
            }
        }

        return outputWord.toString();
    }
}
