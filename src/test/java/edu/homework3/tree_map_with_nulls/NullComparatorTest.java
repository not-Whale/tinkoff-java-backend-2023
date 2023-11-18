package edu.homework3.tree_map_with_nulls;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.TreeMap;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Дерево и null.")
public class NullComparatorTest {
    @Test
    @DisplayName("Добавление null-значений в дерево.")
    void treeMapWithNulls() {
        // given
        TreeMap<String, String> tree = new TreeMap<>(new NullComparator<>());

        // when
        tree.put(null, "test");

        // then
        assertThat(tree.containsKey(null)).isTrue();
    }
}
