package edu.homework3.frequency_dictionary;

import edu.homework3.frequency_dictionary.FreqDict;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Частотный словарь.")
public class FreqDictTest {
    @Test
    @DisplayName("Составление частотного словаря строк.")
    void freqDictFromString() {
        // given
        String[] inputSeq = new String[] {"this", "and", "that", "and"};
        HashMap<String, Integer> answer = new HashMap<>() {{
            put("this", 1); put("and", 2); put("that", 1);
        }};

        // when
        FreqDict<String> freqDict = new FreqDict<>(inputSeq);
        HashMap<String, Integer> map = freqDict.getDict();

        // then
        assertThat(map).isNotNull().isEqualTo(answer);
    }

    @Test
    @DisplayName("Составление частного словаря численного типа.")
    void freqDictFromNumeric() {
        // given
        Integer[] inputSeq = new Integer[] {1, 1, 2, 2};
        HashMap<Integer, Integer> answer = new HashMap<>() {{
            put(1, 2); put(2, 2);
        }};

        // when
        FreqDict<Integer> freqDict = new FreqDict<>(inputSeq);
        HashMap<Integer, Integer> map = freqDict.getDict();

        // then
        assertThat(map).isNotNull().isEqualTo(answer);
    }

    @Test
    @DisplayName("Возврат пустого словаря на пустой вход.")
    void freqDictEmptyInput() {
        // given
        Character[] inputSeq = new Character[0];
        HashMap<Character, Integer> answer = new HashMap<>();

        // when
        FreqDict<Character> freqDict = new FreqDict<>(inputSeq);
        HashMap<Character, Integer> map = freqDict.getDict();

        // then
        assertThat(map).isNotNull().isEqualTo(answer);
    }

    @Test
    @DisplayName("Null-аргумент.")
    void freqDictNullInput() {
        // given
        Byte[] inputSeq = null;

        // when
        Exception exception = assertThrows(
            IllegalArgumentException.class,
            () -> new FreqDict<Byte>(inputSeq)
        );

        // then
        assertThat(exception).isNotNull().isInstanceOf(IllegalArgumentException.class);
    }
}
