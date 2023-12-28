package edu.homework10.random_object_generator;

import edu.homework10.random_object_generator.classes.FactoryCreatedClass;
import edu.homework10.random_object_generator.classes.MaxAnnotatedClass;
import edu.homework10.random_object_generator.classes.MinAnnotatedClass;
import edu.homework10.random_object_generator.classes.MinMaxAnnotatedClass;
import edu.homework10.random_object_generator.classes.POJO;
import edu.homework10.random_object_generator.classes.Record;
import edu.homework10.random_object_generator.classes.StringFieldClassWithNull;
import edu.homework10.random_object_generator.classes.StringFieldClassWithoutNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DisplayName("Генератор объектов на основе рефлексии.")
public class RandomObjectGeneratorTest {
    @Test
    @DisplayName("Генерация POJO.")
    void generatePOJO() {
        // given
        RandomObjectGenerator generator = new RandomObjectGenerator();

        // when
        assertDoesNotThrow(() -> {
            Object pojo = generator.nextObject(POJO.class);

            // then
            assertThat(pojo).isNotNull();
        });
    }

    @Test
    @DisplayName("Генерация record.")
    void generateRecord() {
        // given
        RandomObjectGenerator generator = new RandomObjectGenerator();

        // when
        assertDoesNotThrow(() -> {
            Object record = generator.nextObject(Record.class);

            // then
            assertThat(record).isNotNull();
        });
    }

    @Test
    @DisplayName("Генерация с помощью фабричного метода.")
    void generateByFactory() {
        // given
        RandomObjectGenerator generator = new RandomObjectGenerator();

        // when
        assertDoesNotThrow(() -> {
            Object factoryCreatedClass = generator.nextObject(FactoryCreatedClass.class, "create");

            // then
            assertThat(factoryCreatedClass).isNotNull();
        });
    }

    @Test
    @DisplayName("Аннотация NotNull.")
    void generateClassWithNotNullAnnotation() {
        // given
        RandomObjectGenerator generator = new RandomObjectGenerator();

        // when
        assertDoesNotThrow(() -> {
            Object stringFieldClassWithNull = generator.nextObject(StringFieldClassWithNull.class);
            Object stringFieldClassWithoutNull = generator.nextObject(StringFieldClassWithoutNull.class);

            // then
            assertThat(((StringFieldClassWithNull) stringFieldClassWithNull).getStringField()).isNull();
            assertThat(((StringFieldClassWithoutNull) stringFieldClassWithoutNull).getStringField()).isNotNull();
        });
    }

    @Test
    @DisplayName("Аннотация Min.")
    void generateClassWithMinAnnotation() {
        // given
        RandomObjectGenerator generator = new RandomObjectGenerator();

        // when
        assertDoesNotThrow(() -> {
            Object minAnnotatedClass = generator.nextObject(MinAnnotatedClass.class);

            // then
            assertThat(minAnnotatedClass).isNotNull();
            assertThat(((MinAnnotatedClass) minAnnotatedClass).getValue()).isGreaterThan(100);
        });
    }

    @Test
    @DisplayName("Аннотация Max.")
    void generateClassWithMaxAnnotation() {
        // given
        RandomObjectGenerator generator = new RandomObjectGenerator();

        // when
        assertDoesNotThrow(() -> {
            Object maxAnnotatedClass = generator.nextObject(MaxAnnotatedClass.class);

            // then
            assertThat(maxAnnotatedClass).isNotNull();
            assertThat(((MaxAnnotatedClass) maxAnnotatedClass).getValue()).isLessThan(100);
        });
    }

    @Test
    @DisplayName("Аннотации Min и Max вместе.")
    void generateClassWithMinMaxAnnotations() {
        // given
        RandomObjectGenerator generator = new RandomObjectGenerator();

        // when
        assertDoesNotThrow(() -> {
            Object minMaxAnnotatedClass = generator.nextObject(MinMaxAnnotatedClass.class);

            // then
            assertThat(minMaxAnnotatedClass).isNotNull();
            assertThat(((MinMaxAnnotatedClass) minMaxAnnotatedClass).getValue())
                .isGreaterThan(-10)
                .isLessThan(10);
        });
    }
}
