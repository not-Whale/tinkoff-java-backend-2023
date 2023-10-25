package edu.homework3.frequency_dictionary;

import java.util.HashMap;

public class FreqDict<T> {
    private final HashMap<T, Integer> frequencyDictionary = new HashMap<>();

    public FreqDict(T[] inputSeq) throws IllegalArgumentException {
        if (inputSeq == null) {
            throw new IllegalArgumentException();
        }

        for (T elem : inputSeq) {
            addElem(elem);
        }
    }

    public HashMap<T, Integer> getDict() {
        return frequencyDictionary;
    }

    private void addElem(T elem) {
        if (frequencyDictionary.containsKey(elem)) {
            int freq = frequencyDictionary.get(elem);
            frequencyDictionary.replace(elem, freq, freq + 1);
        } else {
            frequencyDictionary.put(elem, 1);
        }
    }
}
