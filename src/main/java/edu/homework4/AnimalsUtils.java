package edu.homework4;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class AnimalsUtils {

    private static final int CENTIMETERS_IN_METER = 100;
    private static final int CAT_LIFETIME = 17;
    private static final int DOG_LIFETIME = 15;
    private static final int BIRD_LIFETIME = 20;
    private static final int FISH_LIFETIME = 7;
    private static final int SPIDER_LIFETIME = 2;

    private static final String AGE_FIELD = "Возраст";

    private static final String SEX_FIELD = "Пол";

    private static final String NAME_FIELD = "Имя";

    private static final String TYPE_FIELD = "Тип";

    private AnimalsUtils() {}

    public static List<Animal> sortByHeight(List<Animal> animals) {
        if (animals == null) {
            return null;
        }

        return animals.stream()
            .sorted(Comparator.comparingInt(Animal::height))
            .toList();
    }

    public static List<Animal> sortByWeightWithLimitDesc(List<Animal> animals, int limit) {
        if (animals == null) {
            return null;
        }

        if (limit < 0) {
            return List.of();
        }

        return animals.stream()
            .sorted(Comparator.comparingInt(Animal::weight).reversed())
            .limit(limit)
            .toList();
    }

    public static Map<Animal.Type, Long> countAnimalByTypes(List<Animal> animals) {
        if (animals == null) {
            return null;
        }

        return animals.stream()
            .collect(
                Collectors.groupingBy(Animal::type,
                    Collectors.counting()
                ));
    }

    public static Animal longestName(List<Animal> animals) {
        if (animals == null) {
            return null;
        }

        return animals.stream()
            .max(Comparator.comparingInt(a -> a.name().length()))
            .orElse(null);
    }

    public static Animal.Sex commonSex(List<Animal> animals) {
        if (animals == null) {
            return null;
        }

        Map<Animal.Sex, Long> countSex = animals.stream()
            .collect(Collectors.groupingBy(
                Animal::sex,
                Collectors.counting()
            ));

        return countSex.entrySet().stream()
            .max(Comparator.comparingLong(Map.Entry::getValue))
            .map(Map.Entry::getKey)
            .orElse(null);
    }

    public static Map<Animal.Type, Animal> heaviestAnimalsByTypes(List<Animal> animals) {
        if (animals == null) {
            return null;
        }

        return animals.stream()
            .collect(Collectors.toMap(
                Animal::type,
                item -> item,
                (oldItem, newItem) -> oldItem.weight() >= newItem.weight() ? oldItem : newItem));
    }

    public static Animal oldestAnimal(List<Animal> animals) {
        if (animals == null) {
            return null;
        }

        return animals.stream()
            .max(Comparator.comparingInt(Animal::age))
            .orElse(null);
    }

    public static Optional<Animal> heaviestAnimalWithHeightBelowK(List<Animal> animals, int k) {
        if (animals == null) {
            return Optional.empty();
        }

        return animals.stream()
            .filter(a -> a.height() < k)
            .max(Comparator.comparingInt(Animal::weight));
    }

    public static Integer countPaws(List<Animal> animals) {
        if (animals == null) {
            return null;
        }

        return animals.stream()
            .mapToInt(Animal::paws)
            .sum();
    }

    public static List<Animal> animalsWithAgeNotEqualToPaws(List<Animal> animals) {
        if (animals == null) {
            return null;
        }

        return animals.stream()
            .filter(a -> a.paws() != a.age())
            .toList();
    }

    public static List<Animal> animalsThatBitesAndHigherThanMeter(List<Animal> animals) {
        if (animals == null) {
            return null;
        }

        return animals.stream()
            .filter(Animal::bites)
            .filter(a -> a.height() > CENTIMETERS_IN_METER)
            .toList();
    }

    public static Long countAnimalsWithWeightMoreThanHeight(List<Animal> animals) {
        if (animals == null) {
            return 0L;
        }

        return animals.stream()
            .filter(a -> a.weight() > a.height())
            .count();
    }

    public static List<Animal> animalsWithMoreThanTwoWordNames(List<Animal> animals) {
        if (animals == null) {
            return null;
        }

        return animals.stream()
            .filter(a -> a.name().split(" ").length > 2)
            .toList();
    }

    public static boolean hasDogHigherThanK(List<Animal> animals, int height) {
        if (animals == null) {
            return false;
        }

        return animals.stream()
            .filter(a -> a.type().equals(Animal.Type.DOG))
            .anyMatch(a -> a.height() > height);
    }

    public static Map<Animal.Type, Integer> sumWeightByTypeBetweenKL(List<Animal> animals, int k, int l) {
        if (animals == null) {
            return null;
        }

        return animals.stream()
            .filter(a -> a.age() > k)
            .filter(a -> a.age() < l)
            .collect(Collectors.groupingBy(
                Animal::type,
                Collectors.summingInt(Animal::weight)
            ));
    }

    public static List<Animal> sortByTypeSexName(List<Animal> animals) {
        if (animals == null) {
            return null;
        }

        return animals.stream()
            .sorted(Comparator.nullsLast(Comparator.comparing(Animal::type))
                .thenComparing(Comparator.nullsLast(Comparator.comparing(Animal::sex)))
                .thenComparing(Comparator.nullsLast(Comparator.comparing(Animal::name))))
            .toList();
    }

    public static boolean isSpidersBitesOftenThanDogs(List<Animal> animals) {
        if (animals == null) {
            return false;
        }

        Map<Animal.Type, Double> avgSpiderAndDogsBites = animals.stream()
            .filter(a -> a.type() == Animal.Type.SPIDER || a.type() == Animal.Type.DOG)
            .collect(Collectors.groupingBy(
                Animal::type,
                Collectors.averagingInt(a -> a.bites() ? 1 : 0)
            ));

        return avgSpiderAndDogsBites.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse(Animal.Type.DOG)
            .equals(Animal.Type.SPIDER);
    }

    public static Animal heaviestFishMoreThanInTwoLists(List<List<Animal>> animalsMatrix) {
        if (animalsMatrix == null) {
            return null;
        }

        Map<Animal, Integer> heaviestFishesInEveryList = animalsMatrix.stream()
            .map(animals -> animals.stream()
                .filter(a -> a.type() == Animal.Type.FISH)
                .max(Comparator.comparingInt(Animal::weight))
                .orElse(null))
            .collect(Collectors.toMap(
                animal -> animal,
                item -> 1,
                Integer::sum
            ));

        return heaviestFishesInEveryList.entrySet().stream()
            .filter(a -> a.getValue() >= 2)
            .max(Comparator.comparingInt(Map.Entry::getValue))
            .map(Map.Entry::getKey)
            .orElse(null);
    }

    public static Map<String, Set<ValidationError>> validateAnimalRecords(List<Animal> animals) {
        if (animals == null) {
            return null;
        }

        Map<String, Set<ValidationError>> errors = animals.stream()
            .collect(Collectors.toMap(
                Animal::name,
                AnimalsUtils::validateAnimal,
                (a, b) -> a
            ));

        return errors.entrySet().stream()
            .filter(a -> !a.getValue().isEmpty())
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue
            ));
    }

    public static Map<String, String> validateAnimalRecordsPretty(List<Animal> animals) {
        if (animals == null) {
            return null;
        }

        Map<String, Set<ValidationError>> validation = validateAnimalRecords(animals);
        Map<String, String> convertSetToString =  validation.entrySet().stream()
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                elem -> elem.getValue().stream()
                    .map(error -> new StringBuilder(
                        error.field())
                        .append(": ")
                        .append(error.message())
                        .append(", ")
                        .toString()
                    )
                    .reduce(String::concat)
                    .orElse("")
            ));

        return convertSetToString.entrySet().stream()
            .filter(elem -> !elem.getValue().isEmpty())
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                entry -> {
                    String entryValue = entry.getValue();
                    return entryValue.substring(0, entryValue.length() - 2);
                }
        ));
    }

    private static Set<ValidationError> validateAnimal(Animal animal) {
        Set<ValidationError> errorsSet = new HashSet<>();

        if (animal.name() == null) {
            errorsSet.add(new ValidationError("имя не может быть пустым!", NAME_FIELD));
        }

        if (animal.sex() == null) {
            errorsSet.add(new ValidationError("пол не может быть пустой!", SEX_FIELD));
        }

        switch (animal.type()) {
            case CAT -> {
                if (animal.age() > CAT_LIFETIME) {
                    errorsSet.add(new ValidationError("кошки столько не живут =(", AGE_FIELD));
                }
            }
            case DOG -> {
                if (animal.age() > DOG_LIFETIME) {
                    errorsSet.add(new ValidationError("собачки столько не живут =(", AGE_FIELD));
                }
            }
            case BIRD -> {
                if (animal.age() > BIRD_LIFETIME) {
                    errorsSet.add(new ValidationError("птички столько не живут =(", AGE_FIELD));
                }
            }
            case FISH -> {
                if (animal.age() > FISH_LIFETIME) {
                    errorsSet.add(new ValidationError("рыбки столько не живут =(", AGE_FIELD));
                }
            }
            case SPIDER -> {
                if (animal.age() > SPIDER_LIFETIME) {
                    errorsSet.add(new ValidationError("пауки столько не живут =(", AGE_FIELD));
                }
            }
            case null -> errorsSet.add(new ValidationError("тип не может быть пустой!", TYPE_FIELD));
            default -> errorsSet.add(new ValidationError("такой тип не определен!", TYPE_FIELD));
        }

        return errorsSet;
    }
}
