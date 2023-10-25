package edu.homework3.contacts;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Список контактов.")
public class ContactsTest {
    @Nested
    @DisplayName("Парсер контактов.")
    class ContactsParserTest {
        @Test
        @DisplayName("Сортировка по возрастанию.")
        void parseContactsAsc() {
            // given
            String[] contactsList = new String[] {
                "John Locke",
                "Thomas Aquinas",
                "David Hume",
                "Rene Descartes"
            };
            SortType sortType = SortType.ASC;
            Contact[] answer = new Contact[] {
                new Contact("Thomas", "Aquinas"),
                new Contact("Rene", "Descartes"),
                new Contact("David", "Hume"),
                new Contact("John", "Locke")
            };

            // when
            Contact[] contacts = ContactParser.parseContacts(contactsList, sortType);

            // then
            assertThat(contacts).isNotNull().isEqualTo(answer);
        }

        @Test
        @DisplayName("Сортировка по убыванию.")
        void parseContactsDesc() {
            // given
            String[] contactsList = new String[] {
                "Paul Erdos",
                "Leonhard Euler",
                "Carl Gauss"
            };
            SortType sortType = SortType.DESC;
            Contact[] answer = new Contact[] {
                new Contact("Carl", "Gauss"),
                new Contact("Leonhard",  "Euler"),
                new Contact("Paul", "Erdos")
            };

            // when
            Contact[] contacts = ContactParser.parseContacts(contactsList, sortType);

            // then
            assertThat(contacts).isNotNull().isEqualTo(answer);
        }

        @Test
        @DisplayName("Сортировка по именам и фамилиям.")
        void parseContactsWithNames() {
            // given
            String[] contactsList = new String[] {
                "Bradley Pitt",
                "Peter",
                "Christian Bale",
                "Matthew",
                "Jonah Hill",
                "Billy Nogummy"
            };
            Contact[] answer = new Contact[] {
                new Contact("Christian", "Bale"),
                new Contact("Jonah", "Hill"),
                new Contact("Matthew"),
                new Contact("Billy", "Nogummy"),
                new Contact("Peter"),
                new Contact("Bradley", "Pitt"),

            };

            // when
            Contact[] contacts = ContactParser.parseContacts(contactsList);

            // then
            assertThat(contacts).isNotNull().isEqualTo(answer);
        }

        @Test
        @DisplayName("Сортировка пустого массива.")
        void parseContactsEmptyInput() {
            // given
            String[] contactsList = new String[] {};
            Contact[] answer = new Contact[] {};

            // when
            Contact[] contacts = ContactParser.parseContacts(contactsList);

            // then
            assertThat(contacts).isNotNull().isEqualTo(answer);
        }

        @Test
        @DisplayName("Null-аргумент.")
        void parseContactsNullInput() {
            // given
            String[] contactsList = null;
            Contact[] answer = new Contact[] {};

            // when
            Contact[] contacts = ContactParser.parseContacts(contactsList);

            // then
            assertThat(contacts).isNotNull().isEqualTo(answer);
        }

        @Test
        @DisplayName("Null-строка в массиве.")
        void parseContactsNullStringInput() {
            // given
            String[] contactsList = new String[] {
                "Dwayne Johnson",
                null,
                "Jason Statham"
            };

            // when
            Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> ContactParser.parseContacts(contactsList)
            );

            // then
            assertThat(exception).isNotNull().isInstanceOf(IllegalArgumentException.class);
        }

        @ParameterizedTest
        @ValueSource(strings = {"", "Matthew David McConaughey"})
        @DisplayName("Имя не из 1 или 2 слов.")
        void parseContactsIncorrectNameLength(String input) {
            // given
            String[] contactsList = new String[] {
                "Arnold Schwarzenegger",
                input
            };

            // when
            Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> ContactParser.parseContacts(contactsList)
            );

            // then
            assertThat(exception).isNotNull().isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
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
}
