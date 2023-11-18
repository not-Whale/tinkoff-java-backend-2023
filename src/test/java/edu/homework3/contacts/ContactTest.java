package edu.homework3.contacts;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Контакты.")
class ContactTest {
    @Test
    @DisplayName("Null-аргумент.")
    void contactNullName() {
        // given
        String name = null;

        // when
        Exception exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Contact(name)
        );

        // then
        assertThat(exception).isNotNull().isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Перевод объекта в строку.")
    void contactFullToString() {
        // given
        String name = "John";
        String surname = "Cena";
        String answer = "John Cena";

        // when
        Contact contact = new Contact(name, surname);

        // then
        assertThat(contact.toString()).isEqualTo(answer);
    }

    @Test
    @DisplayName("Перевод объекта без фамилии в строку.")
    void contactWithoutSurnameToString() {
        // given
        String name = "John";
        String answer = "John";

        // when
        Contact contact = new Contact(name);

        // then
        assertThat(contact.toString()).isEqualTo(answer);
    }
}
