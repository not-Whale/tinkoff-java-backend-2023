package edu.homework6.hacker_news;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Запрос статей с HackerNews.")
public class HackerNewsTest {
    @Test
    @DisplayName("Поиск названия статьи по номеру.")
    void getNews() {
        // given
        long item = 37570037;
        String requiredTitle = "JDK 21 Release Notes";

        // when
        String title = HackerNews.news(item);

        // then
        assertThat(title).isNotNull().isEqualTo(requiredTitle);
    }

    @Test
    @DisplayName("Поиск названия статьи по номеру. Некорректный номер.")
    void getNewsIncorrectNumber() {
        // given
        long item = -37570037;
        String requiredTitle = "";

        // when
        String title = HackerNews.news(item);

        // then
        assertThat(title).isNotNull().isEqualTo(requiredTitle);
    }

    @Test
    @DisplayName("Получение топа статей.")
    void getTopStories() {
        // given

        // when
        Long[] topStories = HackerNews.hackerNewsTopStories();

        // then
        assertThat(topStories).isNotNull().isNotEmpty();
    }
}
