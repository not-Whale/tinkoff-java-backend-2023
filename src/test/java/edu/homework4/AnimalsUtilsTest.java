package edu.homework4;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Список животных и операции над ним.")
public class AnimalsUtilsTest {
    @Nested
    @DisplayName("Сортировка по росту по возрастанию.")
    class SortByHeightTest {
        @Test
        @DisplayName("Сортировка по росту во возрастанию.")
        void sortByHeight() {
            // given
            List<Animal> animals = List.of(
                new Animal("Donald Jackson", Animal.Type.FISH, Animal.Sex.MALE, 2, 2, 50, false),
                new Animal("Tamara Burton", Animal.Type.CAT, Animal.Sex.FEMALE, 10, 25, 10000, false),
                new Animal("Albert Burgess", Animal.Type.DOG, Animal.Sex.MALE, 5, 35, 25000, false),
                new Animal("Michael Austin", Animal.Type.BIRD, Animal.Sex.MALE, 3, 5, 500, false),
                new Animal("John Morton", Animal.Type.SPIDER, Animal.Sex.MALE, 1, 1, 10, false)
            );

            // when
            List<Animal> animalsSorted = AnimalsUtils.sortByHeight(animals);

            // then
            assertThat(animalsSorted).isNotNull().isEqualTo(List.of(
                new Animal("John Morton", Animal.Type.SPIDER, Animal.Sex.MALE, 1, 1, 10, false),
                new Animal("Donald Jackson", Animal.Type.FISH, Animal.Sex.MALE, 2, 2, 50, false),
                new Animal("Michael Austin", Animal.Type.BIRD, Animal.Sex.MALE, 3, 5, 500, false),
                new Animal("Tamara Burton", Animal.Type.CAT, Animal.Sex.FEMALE, 10, 25, 10000, false),
                new Animal("Albert Burgess", Animal.Type.DOG, Animal.Sex.MALE, 5, 35, 25000, false)
            ));
        }

        @Test
        @DisplayName("Сортировка по росту во возрастанию. Пустой список.")
        void sortByHeightEmptyList() {
            // given
            List<Animal> animals = List.of();

            // when
            List<Animal> animalsSorted = AnimalsUtils.sortByHeight(animals);

            // then
            assertThat(animalsSorted).isNotNull().isEmpty();
        }

        @Test
        @DisplayName("Сортировка по росту во возрастанию. Null-список.")
        void sortByHeightNullList() {
            // given
            List<Animal> animals = null;

            // when
            List<Animal> animalsSorted = AnimalsUtils.sortByHeight(animals);

            // then
            assertThat(animalsSorted).isNull();
        }
    }

    @Nested
    @DisplayName("Сортировка по весу по убыванию.")
    class SortByWeightWithLimitDesc {
        @Test
        @DisplayName("Сортировка по весу по убыванию.")
        void sortByWeightWithLimitDesc() {
            // given
            List<Animal> animals = List.of(
                new Animal("Donald Jackson", Animal.Type.FISH, Animal.Sex.MALE, 2, 2, 50, false),
                new Animal("Tamara Burton", Animal.Type.CAT, Animal.Sex.FEMALE, 10, 25, 10000, false),
                new Animal("Albert Burgess", Animal.Type.DOG, Animal.Sex.MALE, 5, 35, 25000, false),
                new Animal("Michael Austin", Animal.Type.BIRD, Animal.Sex.MALE, 3, 5, 500, false),
                new Animal("John Morton", Animal.Type.SPIDER, Animal.Sex.MALE, 1, 1, 10, false)
            );

            // when
            List<Animal> animalsSorted = AnimalsUtils.sortByWeightWithLimitDesc(animals, 3);

            // then
            assertThat(animalsSorted)
                .isNotNull()
                .isEqualTo(List.of(
                    new Animal("Albert Burgess", Animal.Type.DOG, Animal.Sex.MALE, 5, 35, 25000, false),
                    new Animal("Tamara Burton", Animal.Type.CAT, Animal.Sex.FEMALE, 10, 25, 10000, false),
                    new Animal("Michael Austin", Animal.Type.BIRD, Animal.Sex.MALE, 3, 5, 500, false)
                ));
        }

        @Test
        @DisplayName("Сортировка по весу по убыванию. Пустой список.")
        void sortByWeightWithLimitDescEmptyList() {
            // given
            List<Animal> animals = List.of();

            // when
            List<Animal> animalsSorted = AnimalsUtils.sortByWeightWithLimitDesc(animals, 5);

            // then
            assertThat(animalsSorted).isNotNull().isEmpty();
        }

        @Test
        @DisplayName("Сортировка по весу по убыванию. Null-список.")
        void sortByWeightWithLimitDescNullList() {
            // given
            List<Animal> animals = null;

            // when
            List<Animal> animalsSorted = AnimalsUtils.sortByWeightWithLimitDesc(animals, 5);

            // then
            assertThat(animalsSorted).isNull();
        }

        @Test
        @DisplayName("Сортировка по весу по убыванию. Отрицательный лимит.")
        void sortByWeightWithLimitDescNegativeLimit() {
            // given
            List<Animal> animals = List.of(
                new Animal("Albert Burgess", Animal.Type.DOG, Animal.Sex.MALE, 5, 35, 25000, false),
                new Animal("Michael Austin", Animal.Type.BIRD, Animal.Sex.MALE, 3, 5, 500, false)
            );

            // when
            List<Animal> animalsSorted = AnimalsUtils.sortByWeightWithLimitDesc(animals, -5);

            // then
            assertThat(animalsSorted).isNotNull().isEmpty();
        }
    }

    @Nested
    @DisplayName("Сортировка по весу по убыванию.")
    class CountAnimalByTypes {
        @Test
        @DisplayName("Сортировка по весу по убыванию.")
        void countAnimalByTypes() {
            // given
            List<Animal> animals = List.of(
                new Animal("Donald Jackson", Animal.Type.FISH, Animal.Sex.MALE, 2, 2, 50, false),
                new Animal("Tamara Burton", Animal.Type.FISH, Animal.Sex.FEMALE, 10, 3, 55, false),
                new Animal("Albert Burgess", Animal.Type.DOG, Animal.Sex.MALE, 5, 35, 25000, false),
                new Animal("Michael Austin", Animal.Type.BIRD, Animal.Sex.MALE, 3, 5, 250, false),
                new Animal("John Morton", Animal.Type.BIRD, Animal.Sex.MALE, 1, 3, 200, false)
            );

            // when
            Map<Animal.Type, Long> typesCounted = AnimalsUtils.countAnimalByTypes(animals);

            // then
            assertThat(typesCounted)
                .isNotNull()
                .isEqualTo(new HashMap<Animal.Type, Long>() {{
                    put(Animal.Type.FISH, 2L);
                    put(Animal.Type.DOG, 1L);
                    put(Animal.Type.BIRD, 2L);
                }});
        }

        @Test
        @DisplayName("Сортировка по весу по убыванию. Пустой список.")
        void countAnimalByTypesEmptyList() {
            // given
            List<Animal> animals = List.of();

            // when
            Map<Animal.Type, Long> typesCounted = AnimalsUtils.countAnimalByTypes(animals);

            // then
            assertThat(typesCounted).isNotNull().isEmpty();
        }

        @Test
        @DisplayName("Сортировка по весу по убыванию. Null-список.")
        void countAnimalByTypesNullList() {
            // given
            List<Animal> animals = null;

            // when
            Map<Animal.Type, Long> typesCounted = AnimalsUtils.countAnimalByTypes(animals);

            // then
            assertThat(typesCounted).isNull();
        }
    }

    @Nested
    @DisplayName("Поиск самого длинного имени.")
    class LongestNameTest {
        @Test
        @DisplayName("Поиск самого длинного имени.")
        void longestName() {
            // given
            List<Animal> animals = List.of(
                new Animal("Donald Jackson", Animal.Type.FISH, Animal.Sex.MALE, 2, 2, 50, false),
                new Animal("Tamara Burton", Animal.Type.FISH, Animal.Sex.FEMALE, 10, 3, 55, false),
                new Animal("Albert Burgess", Animal.Type.DOG, Animal.Sex.MALE, 5, 35, 25000, false),
                new Animal("Michael Austin", Animal.Type.BIRD, Animal.Sex.MALE, 3, 5, 250, false),
                new Animal("John Morton", Animal.Type.BIRD, Animal.Sex.MALE, 1, 3, 200, false)
            );

            // when
            Animal longestNameAnimal = AnimalsUtils.longestName(animals);

            // then
            assertThat(longestNameAnimal)
                .isNotNull()
                .isEqualTo(
                    new Animal("Donald Jackson", Animal.Type.FISH, Animal.Sex.MALE, 2, 2, 50, false)
                );
        }

        @Test
        @DisplayName("Поиск самого длинного имени. Пустой список.")
        void longestNameEmptyList() {
            // given
            List<Animal> animals = List.of();

            // when
            Animal longestNameAnimal = AnimalsUtils.longestName(animals);

            // then
            assertThat(longestNameAnimal).isNull();
        }

        @Test
        @DisplayName("Поиск самого длинного имени. Null-список.")
        void longestNameNullList() {
            // given
            List<Animal> animals = null;

            // when
            Animal longestNameAnimal = AnimalsUtils.longestName(animals);

            // then
            assertThat(longestNameAnimal).isNull();
        }
    }

    @Nested
    @DisplayName("Больше самок или самцов?")
    class CommonSexTest {
        @Test
        @DisplayName("Больше самок или самцов?")
        void commonSex() {
            // given
            List<Animal> animals = List.of(
                new Animal("Donald Jackson", Animal.Type.FISH, Animal.Sex.MALE, 2, 2, 50, false),
                new Animal("Tamara Burton", Animal.Type.FISH, Animal.Sex.FEMALE, 10, 3, 55, false),
                new Animal("Albert Burgess", Animal.Type.DOG, Animal.Sex.MALE, 5, 35, 25000, false),
                new Animal("Michael Austin", Animal.Type.BIRD, Animal.Sex.MALE, 3, 5, 250, false),
                new Animal("John Morton", Animal.Type.BIRD, Animal.Sex.MALE, 1, 3, 200, false)
            );

            // when
            Animal.Sex commonSex = AnimalsUtils.commonSex(animals);

            // then
            assertThat(commonSex)
                .isNotNull()
                .isEqualTo(Animal.Sex.MALE);
        }

        @Test
        @DisplayName("Больше самок или самцов? Пустой список.")
        void commonSexEmptyList() {
            // given
            List<Animal> animals = List.of();

            // when
            Animal.Sex commonSex = AnimalsUtils.commonSex(animals);

            // then
            assertThat(commonSex).isNull();
        }

        @Test
        @DisplayName("Больше самок или самцов? Null-список.")
        void commonSexNullList() {
            // given
            List<Animal> animals = null;

            // when
            Animal.Sex commonSex = AnimalsUtils.commonSex(animals);

            // then
            assertThat(commonSex).isNull();
        }
    }

    @Nested
    @DisplayName("Самое тяжелое животное каждого вида.")
    class HeaviestAnimalsByTypesTest {
        @Test
        @DisplayName("Самое тяжелое животное каждого вида.")
        void heaviestAnimalsByTypes() {
            // given
            List<Animal> animals = List.of(
                new Animal("Donald Jackson", Animal.Type.FISH, Animal.Sex.MALE, 2, 2, 50, false),
                new Animal("Tamara Burton", Animal.Type.FISH, Animal.Sex.FEMALE, 10, 3, 55, false),
                new Animal("Albert Burgess", Animal.Type.DOG, Animal.Sex.MALE, 5, 35, 25000, false),
                new Animal("Michael Austin", Animal.Type.BIRD, Animal.Sex.MALE, 3, 5, 250, false),
                new Animal("John Morton", Animal.Type.BIRD, Animal.Sex.MALE, 1, 3, 200, false)
            );

            // when
            Map<Animal.Type, Animal> commonSex = AnimalsUtils.heaviestAnimalsByTypes(animals);

            // then
            assertThat(commonSex)
                .isNotNull()
                .isEqualTo(new HashMap<>(){{
                    put(Animal.Type.FISH, new Animal("Tamara Burton", Animal.Type.FISH, Animal.Sex.FEMALE, 10, 3, 55, false));
                    put(Animal.Type.DOG, new Animal("Albert Burgess", Animal.Type.DOG, Animal.Sex.MALE, 5, 35, 25000, false));
                    put(Animal.Type.BIRD, new Animal("Michael Austin", Animal.Type.BIRD, Animal.Sex.MALE, 3, 5, 15, false));
                }});
        }

        @Test
        @DisplayName("Самое тяжелое животное каждого вида. Пустой список.")
        void heaviestAnimalsByTypesEmptyList() {
            // given
            List<Animal> animals = List.of();

            // when
            Map<Animal.Type, Animal> commonSex = AnimalsUtils.heaviestAnimalsByTypes(animals);

            // then
            assertThat(commonSex).isNull();
        }

        @Test
        @DisplayName("Самое тяжелое животное каждого вида. Null-список.")
        void heaviestAnimalsByTypesNullList() {
            // given
            List<Animal> animals = null;

            // when
            Map<Animal.Type, Animal> commonSex = AnimalsUtils.heaviestAnimalsByTypes(animals);

            // then
            assertThat(commonSex).isNull();
        }
    }

    @Nested
    @DisplayName("Самое старое животное.")
    class OldestAnimalTest {
        @Test
        @DisplayName("Самое старое животное.")
        void oldestAnimal() {
            // given
            List<Animal> animals = List.of(
                new Animal("Donald Jackson", Animal.Type.FISH, Animal.Sex.MALE, 2, 2, 50, false),
                new Animal("Tamara Burton", Animal.Type.FISH, Animal.Sex.FEMALE, 10, 3, 55, false),
                new Animal("Albert Burgess", Animal.Type.DOG, Animal.Sex.MALE, 5, 35, 25000, false),
                new Animal("Michael Austin", Animal.Type.BIRD, Animal.Sex.MALE, 3, 5, 250, false),
                new Animal("John Morton", Animal.Type.BIRD, Animal.Sex.MALE, 1, 3, 200, false)
            );

            // when
            Animal oldestAnimal = AnimalsUtils.oldestAnimal(animals);

            // then
            assertThat(oldestAnimal)
                .isNotNull()
                .isEqualTo(new Animal("Tamara Burton", Animal.Type.FISH, Animal.Sex.FEMALE, 10, 25, 55, false));
        }

        @Test
        @DisplayName("Самое старое животное. Пустой список.")
        void oldestAnimalEmptyList() {
            // given
            List<Animal> animals = List.of();

            // when
            Animal oldestAnimal = AnimalsUtils.oldestAnimal(animals);

            // then
            assertThat(oldestAnimal).isNull();
        }

        @Test
        @DisplayName("Самое старое животное. Null-список")
        void oldestAnimalNullList() {
            // given
            List<Animal> animals = null;

            // when
            Animal oldestAnimal = AnimalsUtils.oldestAnimal(animals);

            // then
            assertThat(oldestAnimal).isNull();
        }
    }

    @Nested
    @DisplayName("Самое тяжелое животное среди животных ниже k см")
    class HeaviestAnimalWithHeightBelowKTest {
        @Test
        @DisplayName("Самое тяжелое животное среди животных ниже k см.")
        void heaviestAnimalWithHeightBelowK() {
            // given
            List<Animal> animals = List.of(
                new Animal("Donald Jackson", Animal.Type.FISH, Animal.Sex.MALE, 2, 2, 50, false),
                new Animal("Tamara Burton", Animal.Type.FISH, Animal.Sex.FEMALE, 10, 3, 55, false),
                new Animal("Albert Burgess", Animal.Type.DOG, Animal.Sex.MALE, 5, 35, 25000, false),
                new Animal("Michael Austin", Animal.Type.BIRD, Animal.Sex.MALE, 3, 5, 250, false),
                new Animal("John Morton", Animal.Type.BIRD, Animal.Sex.MALE, 1, 3, 200, false)
            );

            // when
            Optional<Animal> heaviestAnimalWithHeightBelowK = AnimalsUtils.heaviestAnimalWithHeightBelowK(animals, 20);

            // then
            assertThat(heaviestAnimalWithHeightBelowK)
                .isNotNull()
                .contains(new Animal("Michael Austin", Animal.Type.BIRD, Animal.Sex.MALE, 3, 5, 250, false));
        }

        @Test
        @DisplayName("Самое тяжелое животное среди животных ниже k см. Пустой список.")
        void heaviestAnimalWithHeightBelowKEmptyList() {
            // given
            List<Animal> animals = List.of();

            // when
            Optional<Animal> heaviestAnimalWithHeightBelowK = AnimalsUtils.heaviestAnimalWithHeightBelowK(animals, 20);

            // then
            assertThat(heaviestAnimalWithHeightBelowK).isNull();
        }

        @Test
        @DisplayName("Самое тяжелое животное среди животных ниже k см. Null-список.")
        void heaviestAnimalWithHeightBelowKNullList() {
            // given
            List<Animal> animals = null;

            // when
            Optional<Animal> heaviestAnimalWithHeightBelowK = AnimalsUtils.heaviestAnimalWithHeightBelowK(animals, 20);

            // then
            assertThat(heaviestAnimalWithHeightBelowK).isNull();
        }
    }

    @Nested
    @DisplayName("Сколько в сумме лап у животных в списке.")
    class CountPawsTest {
        @Test
        @DisplayName("Сколько в сумме лап у животных в списке")
        void countPaws() {
            // given
            List<Animal> animals = List.of(
                new Animal("Donald Jackson", Animal.Type.FISH, Animal.Sex.MALE, 2, 2, 50, false),
                new Animal("Tamara Burton", Animal.Type.FISH, Animal.Sex.FEMALE, 10, 3, 55, false),
                new Animal("Albert Burgess", Animal.Type.DOG, Animal.Sex.MALE, 5, 35, 25000, false),
                new Animal("Michael Austin", Animal.Type.BIRD, Animal.Sex.MALE, 3, 5, 250, false),
                new Animal("John Morton", Animal.Type.BIRD, Animal.Sex.MALE, 1, 3, 200, false)
            );

            // when
            Integer paws = AnimalsUtils.countPaws(animals);

            // then
            assertThat(paws).isNotNull().isEqualTo(8);
        }

        @Test
        @DisplayName("Сколько в сумме лап у животных в списке. Пустой список.")
        void countPawsEmptyList() {
            // given
            List<Animal> animals = List.of();

            // when
            Integer paws = AnimalsUtils.countPaws(animals);

            // then
            assertThat(paws).isNotNull().isEqualTo(0);
        }

        @Test
        @DisplayName("Сколько в сумме лап у животных в списке. Null-список.")
        void countPawsNullList() {
            // given
            List<Animal> animals = null;

            // when
            Integer paws = AnimalsUtils.countPaws(animals);

            // then
            assertThat(paws).isNull();
        }
    }

    @Nested
    @DisplayName("Список животных, возраст у которых не совпадает с количеством лап.")
    class AnimalsWithAgeNotEqualToPawsTest {
        @Test
        @DisplayName("Список животных, возраст у которых не совпадает с количеством лап.")
        void animalsWithAgeNotEqualToPaws() {
            // given
            List<Animal> animals = List.of(
                new Animal("Donald Jackson", Animal.Type.FISH, Animal.Sex.MALE, 2, 2, 50, false),
                new Animal("Tamara Burton", Animal.Type.FISH, Animal.Sex.FEMALE, 10, 3, 55, false),
                new Animal("Albert Burgess", Animal.Type.DOG, Animal.Sex.MALE, 4, 35, 25000, false),
                new Animal("Michael Austin", Animal.Type.BIRD, Animal.Sex.MALE, 2, 5, 250, false),
                new Animal("John Morton", Animal.Type.BIRD, Animal.Sex.MALE, 3, 3, 200, false)
            );

            // when
            List<Animal> animalsWithAgeNotEqualToPaws = AnimalsUtils.animalsWithAgeNotEqualToPaws(animals);

            // then
            assertThat(animalsWithAgeNotEqualToPaws)
                .isNotNull()
                .isEqualTo(List.of(
                    new Animal("Donald Jackson", Animal.Type.FISH, Animal.Sex.MALE, 2, 2, 50, false),
                    new Animal("Tamara Burton", Animal.Type.FISH, Animal.Sex.FEMALE, 10, 3, 55, false),
                    new Animal("John Morton", Animal.Type.BIRD, Animal.Sex.MALE, 3, 3, 200, false)
                ));
        }

        @Test
        @DisplayName("Список животных, возраст у которых не совпадает с количеством лап. Пустой список.")
        void animalsWithAgeNotEqualToPawsEmptyList() {
            // given
            List<Animal> animals = List.of();

            // when
            List<Animal> animalsWithAgeNotEqualToPaws = AnimalsUtils.animalsWithAgeNotEqualToPaws(animals);

            // then
            assertThat(animalsWithAgeNotEqualToPaws).isNotNull().isEmpty();
        }

        @Test
        @DisplayName("Список животных, возраст у которых не совпадает с количеством лап. Пустой список.")
        void animalsWithAgeNotEqualToPawsNullList() {
            // given
            List<Animal> animals = null;

            // when
            List<Animal> animalsWithAgeNotEqualToPaws = AnimalsUtils.animalsWithAgeNotEqualToPaws(animals);

            // then
            assertThat(animalsWithAgeNotEqualToPaws).isNull();
        }
    }

    @Nested
    @DisplayName("Список животных, которые могут укусить и рост которых превышает 100 см.")
    class AnimalsThatBitesAndHigherThanMeterTest {
        @Test
        @DisplayName("Список животных, которые могут укусить и рост которых превышает 100 см.")
        void animalsThatBitesAndHigherThanMeter() {
            // given
            List<Animal> animals = List.of(
                new Animal("Donald Jackson", Animal.Type.FISH, Animal.Sex.MALE, 2, 2, 50, false),
                new Animal("Tamara Burton", Animal.Type.FISH, Animal.Sex.FEMALE, 10, 3, 55, true),
                new Animal("Albert Burgess", Animal.Type.DOG, Animal.Sex.MALE, 4, 135, 25000, true),
                new Animal("Michael Austin", Animal.Type.BIRD, Animal.Sex.MALE, 2, 5, 250, true),
                new Animal("John Morton", Animal.Type.BIRD, Animal.Sex.MALE, 3, 3, 200, false)
            );

            // when
            List<Animal> animalsThatBitesAndHigherThanMeter = AnimalsUtils.animalsThatBitesAndHigherThanMeter(animals);

            // then
            assertThat(animalsThatBitesAndHigherThanMeter)
                .isNotNull()
                .isEqualTo(List.of(new Animal("Albert Burgess", Animal.Type.DOG, Animal.Sex.MALE, 4, 135, 25000, true)));
        }

        @Test
        @DisplayName("Список животных, которые могут укусить и рост которых превышает 100 см. Пустой список.")
        void animalsThatBitesAndHigherThanMeterEmptyList() {
            // given
            List<Animal> animals = List.of();

            // when
            List<Animal> animalsThatBitesAndHigherThanMeter = AnimalsUtils.animalsThatBitesAndHigherThanMeter(animals);

            // then
            assertThat(animalsThatBitesAndHigherThanMeter).isNotNull().isEmpty();
        }

        @Test
        @DisplayName("Список животных, которые могут укусить и рост которых превышает 100 см. Null-список.")
        void animalsThatBitesAndHigherThanMeterNullList() {
            // given
            List<Animal> animals = null;

            // when
            List<Animal> animalsThatBitesAndHigherThanMeter = AnimalsUtils.animalsThatBitesAndHigherThanMeter(animals);

            // then
            assertThat(animalsThatBitesAndHigherThanMeter).isNull();
        }
    }

    @Nested
    @DisplayName("Сколько в списке животных, вес которых превышает рост.")
    class CountAnimalsWithWeightMoreThanHeightTest {
        @Test
        @DisplayName("Сколько в списке животных, вес которых превышает рост.")
        void countAnimalsWithWeightMoreThanHeight() {
            // given
            List<Animal> animals = List.of(
                new Animal("Donald Jackson", Animal.Type.FISH, Animal.Sex.MALE, 2, 2, 50, false),
                new Animal("Tamara Burton", Animal.Type.FISH, Animal.Sex.FEMALE, 10, 3, 55, true),
                new Animal("Albert Burgess", Animal.Type.DOG, Animal.Sex.MALE, 4, 135, 25000, true),
                new Animal("Michael Austin", Animal.Type.BIRD, Animal.Sex.MALE, 2, 5, 250, true),
                new Animal("John Morton", Animal.Type.BIRD, Animal.Sex.MALE, 3, 3, 200, false),
                new Animal("Maxine Weber", Animal.Type.SPIDER, Animal.Sex.MALE, 1, 2, 1, true)
            );

            // when
            Integer countAnimalsWithWeightMoreThanHeight = AnimalsUtils.countAnimalsWithWeightMoreThanHeight(animals);

            // then
            assertThat(countAnimalsWithWeightMoreThanHeight).isNotNull().isEqualTo(1);
        }

        @Test
        @DisplayName("Сколько в списке животных, вес которых превышает рост. Пустой список.")
        void countAnimalsWithWeightMoreThanHeightEmptyList() {
            // given
            List<Animal> animals = List.of();

            // when
            Integer countAnimalsWithWeightMoreThanHeight = AnimalsUtils.countAnimalsWithWeightMoreThanHeight(animals);

            // then
            assertThat(countAnimalsWithWeightMoreThanHeight).isNotNull().isEqualTo(0);
        }

        @Test
        @DisplayName("Сколько в списке животных, вес которых превышает рост. Null-список.")
        void countAnimalsWithWeightMoreThanHeightNullList() {
            // given
            List<Animal> animals = null;

            // when
            Integer countAnimalsWithWeightMoreThanHeight = AnimalsUtils.countAnimalsWithWeightMoreThanHeight(animals);

            // then
            assertThat(countAnimalsWithWeightMoreThanHeight).isNotNull().isEqualTo(0);
        }
    }

    @Nested
    @DisplayName("Список животных, имена которых состоят из более чем двух слов.")
    class AnimalsWithMoreThanTwoWordNamesTest {
        @Test
        @DisplayName("Список животных, имена которых состоят из более чем двух слов.")
        void animalsWithMoreThanTwoWordNames() {
            // given
            List<Animal> animals = List.of(
                new Animal("Donald Jackson", Animal.Type.FISH, Animal.Sex.MALE, 2, 2, 50, false),
                new Animal("Tamara Burton", Animal.Type.FISH, Animal.Sex.FEMALE, 10, 3, 55, true),
                new Animal("Albert Junior Burgess", Animal.Type.DOG, Animal.Sex.MALE, 4, 135, 25000, true),
                new Animal("Michael Austin", Animal.Type.BIRD, Animal.Sex.MALE, 2, 5, 250, true),
                new Animal("John Von Morton", Animal.Type.BIRD, Animal.Sex.MALE, 3, 3, 200, false)
            );

            // when
            List<Animal> animalsWithMoreThanTwoWordNames = AnimalsUtils.animalsWithMoreThanTwoWordNames(animals);

            // then
            assertThat(animalsWithMoreThanTwoWordNames)
                .isNotNull()
                .isEqualTo(List.of(
                    new Animal("Albert Junior Burgess", Animal.Type.DOG, Animal.Sex.MALE, 4, 135, 25000, true),
                    new Animal("John Von Morton", Animal.Type.BIRD, Animal.Sex.MALE, 3, 3, 200, false)
                ));
        }

        @Test
        @DisplayName("Список животных, имена которых состоят из более чем двух слов. Пустой список.")
        void animalsWithMoreThanTwoWordNamesEmptyList() {
            // given
            List<Animal> animals = List.of();

            // when
            List<Animal> animalsWithMoreThanTwoWordNames = AnimalsUtils.animalsWithMoreThanTwoWordNames(animals);

            // then
            assertThat(animalsWithMoreThanTwoWordNames).isNotNull().isEmpty();
        }

        @Test
        @DisplayName("Список животных, имена которых состоят из более чем двух слов. Null-список.")
        void animalsWithMoreThanTwoWordNamesNullList() {
            // given
            List<Animal> animals = null;

            // when
            List<Animal> animalsWithMoreThanTwoWordNames = AnimalsUtils.animalsWithMoreThanTwoWordNames(animals);

            // then
            assertThat(animalsWithMoreThanTwoWordNames).isNull();
        }
    }

    @Nested
    @DisplayName("Есть ли в списке собака ростом более k см.")
    class HasDogHigherThanKTest {
        @Test
        @DisplayName("Есть ли в списке собака ростом более k см.")
        void hasDogHigherThanK() {
            // given
            List<Animal> animals = List.of(
                new Animal("Donald Jackson", Animal.Type.FISH, Animal.Sex.MALE, 2, 2, 50, false),
                new Animal("Tamara Burton", Animal.Type.FISH, Animal.Sex.FEMALE, 10, 3, 55, true),
                new Animal("Albert Junior Burgess", Animal.Type.DOG, Animal.Sex.MALE, 4, 135, 25000, true),
                new Animal("Michael Austin", Animal.Type.BIRD, Animal.Sex.MALE, 2, 5, 250, true),
                new Animal("John Von Morton", Animal.Type.BIRD, Animal.Sex.MALE, 3, 3, 200, false)
            );

            // when
            boolean hasDogHigherThanK = AnimalsUtils.hasDogHigherThanK(animals, 100);

            // then
            assertThat(hasDogHigherThanK).isTrue();
        }

        @Test
        @DisplayName("Есть ли в списке собака ростом более k см. Пустой список.")
        void hasDogHigherThanKEmptyList() {
            // given
            List<Animal> animals = List.of();

            // when
            boolean hasDogHigherThanK = AnimalsUtils.hasDogHigherThanK(animals, 100);

            // then
            assertThat(hasDogHigherThanK).isFalse();
        }

        @Test
        @DisplayName("Есть ли в списке собака ростом более k см. Null-список.")
        void hasDogHigherThanKNullList() {
            // given
            List<Animal> animals = null;

            // when
            boolean hasDogHigherThanK = AnimalsUtils.hasDogHigherThanK(animals, 100);

            // then
            assertThat(hasDogHigherThanK).isFalse();
        }
    }

    @Nested
    @DisplayName("Найти суммарный вес животных каждого вида, которым от k до l лет.")
    class SumWeightByTypeBetweenKLTest {
        @Test
        @DisplayName("Найти суммарный вес животных каждого вида, которым от k до l лет.")
        void sumWeightByTypeBetweenKL() {
            // given
            List<Animal> animals = List.of(
                new Animal("Donald Jackson", Animal.Type.FISH, Animal.Sex.MALE, 2, 2, 50, false),
                new Animal("Tamara Burton", Animal.Type.FISH, Animal.Sex.FEMALE, 10, 3, 55, true),
                new Animal("Albert Junior Burgess", Animal.Type.DOG, Animal.Sex.MALE, 4, 135, 25000, true),
                new Animal("Michael Austin", Animal.Type.BIRD, Animal.Sex.MALE, 2, 5, 250, true),
                new Animal("John Von Morton", Animal.Type.BIRD, Animal.Sex.MALE, 3, 3, 200, false)
            );

            // when
            Map<Animal.Type, Integer> sumWeightByTypeBetweenKL = AnimalsUtils.sumWeightByTypeBetweenKL(animals, 3, 8);

            // then
            assertThat(sumWeightByTypeBetweenKL)
                .isNotNull()
                .isEqualTo(new HashMap<>() {{
                    put(Animal.Type.FISH, 55);
                    put(Animal.Type.DOG, 25000);
                    put(Animal.Type.BIRD, 450);
                }});
        }

        @Test
        @DisplayName("Найти суммарный вес животных каждого вида, которым от k до l лет. Пустой список.")
        void sumWeightByTypeBetweenKLEmptyList() {
            // given
            List<Animal> animals = List.of();

            // when
            Map<Animal.Type, Integer> sumWeightByTypeBetweenKL = AnimalsUtils.sumWeightByTypeBetweenKL(animals, 52, 520);

            // then
            assertThat(sumWeightByTypeBetweenKL).isNotNull().isEmpty();
        }

        @Test
        @DisplayName("Найти суммарный вес животных каждого вида, которым от k до l лет. Null-список.")
        void sumWeightByTypeBetweenKLNullList() {
            // given
            List<Animal> animals = null;

            // when
            Map<Animal.Type, Integer> sumWeightByTypeBetweenKL = AnimalsUtils.sumWeightByTypeBetweenKL(animals, 52, 520);

            // then
            assertThat(sumWeightByTypeBetweenKL).isNull();
        }
    }

    @Nested
    @DisplayName("Список животных, отсортированный по виду, затем по полу, затем по имени.")
    class SortByTypeSexNameTest {
        @Test
        @DisplayName("Список животных, отсортированный по виду, затем по полу, затем по имени.")
        void sortByTypeSexName() {
            // given
            List<Animal> animals = List.of(
                new Animal("Tamara Burton", Animal.Type.FISH, Animal.Sex.FEMALE, 10, 3, 55, true),
                new Animal("Donald Jackson", Animal.Type.FISH, Animal.Sex.MALE, 2, 2, 50, false),
                new Animal("Albert Junior Burgess", Animal.Type.DOG, Animal.Sex.MALE, 4, 135, 25000, true),
                new Animal("Michael Austin", Animal.Type.BIRD, Animal.Sex.MALE, 2, 5, 250, true),
                new Animal("John Von Morton", Animal.Type.BIRD, Animal.Sex.MALE, 3, 3, 200, false)
            );

            // when
            List<Animal> animalsSorted = AnimalsUtils.sortByTypeSexName(animals);

            // then
            assertThat(animalsSorted)
                .isNotNull()
                .isEqualTo(List.of(
                    new Animal("Albert Junior Burgess", Animal.Type.DOG, Animal.Sex.MALE, 4, 135, 25000, true),
                    new Animal("John Von Morton", Animal.Type.BIRD, Animal.Sex.MALE, 3, 3, 200, false),
                    new Animal("Michael Austin", Animal.Type.BIRD, Animal.Sex.MALE, 2, 5, 250, true),
                    new Animal("Donald Jackson", Animal.Type.FISH, Animal.Sex.MALE, 2, 2, 50, false),
                    new Animal("Tamara Burton", Animal.Type.FISH, Animal.Sex.FEMALE, 10, 3, 55, true)
                ));
        }

        @Test
        @DisplayName("Список животных, отсортированный по виду, затем по полу, затем по имени. Пустой список.")
        void sortByTypeSexNameEmptyList() {
            // given
            List<Animal> animals = List.of();

            // when
            List<Animal> animalsSorted = AnimalsUtils.sortByTypeSexName(animals);

            // then
            assertThat(animalsSorted).isNotNull().isEmpty();
        }

        @Test
        @DisplayName("Список животных, отсортированный по виду, затем по полу, затем по имени. Null-список.")
        void sortByTypeSexNameNullList() {
            // given
            List<Animal> animals = null;

            // when
            List<Animal> animalsSorted = AnimalsUtils.sortByTypeSexName(animals);

            // then
            assertThat(animalsSorted).isNull();
        }
    }

    @Nested
    @DisplayName("Правда ли, что пауки кусаются чаще, чем собаки.")
    class IsSpidersBitesOftenThanDogsTest {
        @Test
        @DisplayName("Правда ли, что пауки кусаются чаще, чем собаки.")
        void isSpidersBitesOftenThanDogs() {
            // given
            List<Animal> animals = List.of(
                new Animal("Tamara Burton", Animal.Type.FISH, Animal.Sex.FEMALE, 10, 3, 55, true),
                new Animal("Donald Jackson", Animal.Type.FISH, Animal.Sex.MALE, 2, 2, 50, false),
                new Animal("Albert Junior Burgess", Animal.Type.DOG, Animal.Sex.MALE, 4, 135, 25000, false),
                new Animal("Michael Austin", Animal.Type.SPIDER, Animal.Sex.MALE, 2, 2, 10, true),
                new Animal("John Von Morton", Animal.Type.SPIDER, Animal.Sex.MALE, 3, 3, 15, false)
            );

            // when
            boolean spidersBitesOftenThanDogs = AnimalsUtils.isSpidersBitesOftenThanDogs(animals);

            // then
            assertThat(spidersBitesOftenThanDogs).isTrue();
        }

        @Test
        @DisplayName("Правда ли, что пауки кусаются чаще, чем собаки. Пустой список.")
        void isSpidersBitesOftenThanDogsEmptyList() {
            // given
            List<Animal> animals = List.of();

            // when
            boolean spidersBitesOftenThanDogs = AnimalsUtils.isSpidersBitesOftenThanDogs(animals);

            // then
            assertThat(spidersBitesOftenThanDogs).isFalse();
        }

        @Test
        @DisplayName("Правда ли, что пауки кусаются чаще, чем собаки. Null-список.")
        void isSpidersBitesOftenThanDogsNullList() {
            // given
            List<Animal> animals = List.of();

            // when
            boolean spidersBitesOftenThanDogs = AnimalsUtils.isSpidersBitesOftenThanDogs(animals);

            // then
            assertThat(spidersBitesOftenThanDogs).isFalse();
        }
    }

    @Nested
    @DisplayName("")
    class HeaviestFishMoreThanInTwoListsTest {
        @Test
        @DisplayName("")
        void heaviestFishMoreThanInTwoLists() {

        }
    }
}
