package edu.homework10.random_object_generator;

import edu.homework10.random_object_generator.annotations.Max;
import edu.homework10.random_object_generator.annotations.Min;
import edu.homework10.random_object_generator.annotations.NotNull;
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

    private static class MinAnnotatedClass {
        private int value;

        public MinAnnotatedClass(@Min(intValue = 100) int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    private static class MaxAnnotatedClass {
        private long value;

        public MaxAnnotatedClass(@Max(longValue = 100) long value) {
            this.value = value;
        }

        public long getValue() {
            return value;
        }
    }

    private static class MinMaxAnnotatedClass {
        private float value;

        public MinMaxAnnotatedClass(@Min(floatValue = -10) @Max(floatValue = 10) float value) {
            this.value = value;
        }

        public float getValue() {
            return value;
        }
    }

    private static class FactoryCreatedClass {
        private final boolean booleanField;

        private final char charField;

        private FactoryCreatedClass(boolean booleanField, char charField) {
            this.booleanField = booleanField;
            this.charField = charField;
        }

        public static FactoryCreatedClass create(boolean booleanField, char charField) {
            return new FactoryCreatedClass(booleanField, charField);
        }

        public boolean getBooleanField() {
            return booleanField;
        }

        public char getCharField() {
            return charField;
        }
    }

    private static class StringFieldClassWithNull {
        private final String stringField;

        public StringFieldClassWithNull(String stringField) {
            this.stringField = stringField;
        }

        public String getStringField() {
            return stringField;
        }
    }

    private static class StringFieldClassWithoutNull {
        private final String stringField;

        public StringFieldClassWithoutNull(@NotNull String stringField) {
            this.stringField = stringField;
        }

        public String getStringField() {
            return stringField;
        }
    }

    private record Record(float floatField, double doubleField) {}

    private static class POJO {
        private byte byteField;

        private short shortField;

        private final int intField;

        private final long longField;

        public POJO(byte byteField, short shortField, int intField, long longField) {
            this.byteField = byteField;
            this.shortField = shortField;
            this.intField = intField;
            this.longField = longField;
        }

        public byte getByteField() {
            return byteField;
        }

        public short getShortField() {
            return shortField;
        }

        public int getIntField() {
            return intField;
        }

        public long getLongField() {
            return longField;
        }
    }
}
