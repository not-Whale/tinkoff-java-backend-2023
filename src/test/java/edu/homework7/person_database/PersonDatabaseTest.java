package edu.homework7.person_database;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Stream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Кэширующий сервис для поиска людей по атрибутам. С учетом чтения-записи.")
public class PersonDatabaseTest {
    private static final Logger LOGGER = LogManager.getLogger();

    @Test
    @DisplayName("Многопоточный поиск по атрибутам.")
    void find() {
        // given
        Database database = new Database();
        for (int i = 0; i < 1000; i++) {
            database.add(new Person(
                i,
                "name" + (i % 10),
                "address" + (i % 20),
                "phone" + (i % 50)
            ));
        }
        assertThat(database.size()).isEqualTo(1000);

        // when
        Callable<Void> find = () -> {
            for (int i = 0; i < 1_000_000; i++) {
                List<Person> names = database.findByName("name" + (i % 10));
                assertThat(names).isNotNull().isNotEmpty();
                assertThat(names.size()).isEqualTo(100);

                List<Person> addresses = database.findByAddress("address" + (i % 20));
                assertThat(addresses).isNotNull().isNotEmpty();
                assertThat(addresses.size()).isEqualTo(50);

                List<Person> phones = database.findByPhone("phone" + (i % 50));
                assertThat(phones).isNotNull().isNotEmpty();
                assertThat(phones.size()).isEqualTo(20);
            }
            return null;
        };

        var tasks = Stream.generate(() -> find).limit(8).toList();
        try (ExecutorService executorService = Executors.newCachedThreadPool()) {
            List<Future<Void>> futures = executorService.invokeAll(tasks);
            for (var future : futures) {
                future.get();
            }
        } catch (ExecutionException | InterruptedException e) {
            LOGGER.info("Something went wrong" + e);
        }
    }

    @Test
    @DisplayName("Добавление некорректных записей. Люди с null полями не хранятся.")
    void addIncorrectPerson() {
        // given
        Database database = new Database();
        database.add(new Person(
            1,
            null,
            "address",
            "phone"
        ));
        database.add(new Person(
            2,
            "name",
            null,
            "phone"
        ));
        database.add(new Person(
            3,
            "name",
            "address",
            null
        ));

        // when
        int size = database.size();

        // then
        assertThat(size).isEqualTo(0);
    }

    @Test
    @DisplayName("Добавление и удаление записей.")
    void addAndRemovePersons() {
        // given
        Database database = new Database();
        for (int i = 0; i < 100_000; i++) {
            database.add(new Person(
                i,
                "name" + i,
                "address" + i,
                "number" + i
            ));
        }

        // when
        Callable<Void> addTask = () -> {
            for (int i = 0; i < 10_000; i++) {
                database.add(new Person(
                    (100_000 + i),
                    "name" + (100_000 + i),
                    "address" + (100_000 + i),
                    "number" + (100_000 + i)
                ));
            }
            return null;
        };

        Callable<Void> removeTask = () -> {
            for (int i = 0; i < 10_000; i++) {
                database.delete(i);
            }
            return null;
        };
        try (ExecutorService executorService = Executors.newCachedThreadPool()) {
            var futures = executorService.invokeAll(List.of(addTask, removeTask));
            for (var future : futures) {
                future.get();
            }
        } catch (ExecutionException | InterruptedException e) {
            LOGGER.info("Something went wrong" + e);
        }

        // then
        assertThat(database.size()).isEqualTo(100_000);
    }
}
