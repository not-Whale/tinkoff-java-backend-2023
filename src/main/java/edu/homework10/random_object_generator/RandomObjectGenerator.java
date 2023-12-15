package edu.homework10.random_object_generator;

import edu.homework10.random_object_generator.annotations.Max;
import edu.homework10.random_object_generator.annotations.Min;
import edu.homework10.random_object_generator.annotations.NotNull;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import org.apache.commons.lang.RandomStringUtils;

public class RandomObjectGenerator {
    private static final int RANDOM_STRING_LENGTH = 10;

    public RandomObjectGenerator() {}

    public Object nextObject(Class<?> targetClass) {
        Constructor<?>[] constructors = targetClass.getConstructors();
        Object instance = generateInstance(constructors);
        if (instance == null) {
            constructors = targetClass.getDeclaredConstructors();
            return generateInstance(constructors);
        }
        return instance;
    }

    public Object nextObject(Class<?> targetClass, String targetFactory) {
        Method[] factories = Arrays.stream(targetClass.getMethods())
            .filter(method -> method.getName().equals(targetFactory))
            .toArray(Method[]::new);
        return generateFactoryInstance(factories);
    }

    private Object generateFactoryInstance(Method[] factories) {
        Object instance = null;
        int factoryNumber = 0;
        while (instance == null && factoryNumber < factories.length) {
            Method currentFactory = factories[factoryNumber];
            Object[] randomParams = generateRandomParams(currentFactory.getParameters());
            try {
                instance = currentFactory.invoke(null, randomParams);
            } catch (InvocationTargetException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return instance;
    }

    private Object generateInstance(Constructor<?>[] constructors) {
        Object instance = null;
        int constructorNumber = 0;
        while (instance == null && constructorNumber < constructors.length) {
            Constructor<?> currentConstructor = constructors[constructorNumber];
            Object[] randomParams = generateRandomParams(currentConstructor.getParameters());
            try {
                if (Modifier.isPrivate(currentConstructor.getModifiers())) {
                    currentConstructor.setAccessible(true);
                    instance = currentConstructor.newInstance(randomParams);
                    currentConstructor.setAccessible(false);
                } else {
                    instance = currentConstructor.newInstance(randomParams);
                }
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
        return instance;
    }

    private Object[] generateRandomParams(Parameter[] parameters) {
        Object[] randomParams = new Object[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            Parameter currentParameter = parameters[i];
            randomParams[i] = getRandomValue(currentParameter);
        }
        return randomParams;
    }

    private Object getRandomValue(Parameter parameter) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        Min minAnnotation = parameter.getAnnotation(Min.class);
        Max maxAnnotation = parameter.getAnnotation(Max.class);
        NotNull notNullAnnotation = parameter.getAnnotation(NotNull.class);
        return switch (parameter.getParameterizedType().getTypeName()) {
            case "boolean", "java.lang.Boolean" -> random.nextBoolean();
            case "byte", "java.lang.Byte" -> getRandomByte(minAnnotation, maxAnnotation, random);
            case "short", "java.lang.Short" -> getRandomShort(minAnnotation, maxAnnotation, random);
            case "int", "java.lang.Integer" -> getRandomInt(minAnnotation, maxAnnotation, random);
            case "long", "java.lang.Long" -> getRandomLong(minAnnotation, maxAnnotation, random);
            case "float", "java.lang.Float" -> getRandomFloat(minAnnotation, maxAnnotation, random);
            case "double", "java.lang.Double" -> getRandomDouble(minAnnotation, maxAnnotation, random);
            case "char", "java.lang.Character" -> getRandomChar(minAnnotation, maxAnnotation, random);
            case "java.lang.String" -> getRandomString(notNullAnnotation);
            default -> null;
        };
    }

    private static Object getRandomString(NotNull notNullAnnotation) {
        return notNullAnnotation != null
            ? RandomStringUtils.random(RANDOM_STRING_LENGTH, true, false)
            : null;
    }

    private static char getRandomChar(Min minAnnotation, Max maxAnnotation, ThreadLocalRandom random) {
        char min = minAnnotation != null ? minAnnotation.charValue() : Character.MIN_VALUE;
        char max = maxAnnotation != null ? maxAnnotation.charValue() : Character.MAX_VALUE;
        return (char) random.nextInt(min, max);
    }

    private static double getRandomDouble(Min minAnnotation, Max maxAnnotation, ThreadLocalRandom random) {
        double min = minAnnotation != null ? minAnnotation.doubleValue() : Double.MIN_VALUE;
        double max = maxAnnotation != null ? maxAnnotation.doubleValue() : Double.MAX_VALUE;
        return random.nextDouble(min, max);
    }

    private static float getRandomFloat(Min minAnnotation, Max maxAnnotation, ThreadLocalRandom random) {
        float min = minAnnotation != null ? minAnnotation.floatValue() : Float.MIN_VALUE;
        float max = maxAnnotation != null ? maxAnnotation.floatValue() : Float.MAX_VALUE;
        return random.nextFloat(min, max);
    }

    private static long getRandomLong(Min minAnnotation, Max maxAnnotation, ThreadLocalRandom random) {
        long min = minAnnotation != null ? minAnnotation.longValue() : Long.MIN_VALUE;
        long max = maxAnnotation != null ? maxAnnotation.longValue() : Long.MAX_VALUE;
        return random.nextLong(min, max);
    }

    private static int getRandomInt(Min minAnnotation, Max maxAnnotation, ThreadLocalRandom random) {
        int min = minAnnotation != null ? minAnnotation.intValue() : Integer.MIN_VALUE;
        int max = maxAnnotation != null ? maxAnnotation.intValue() : Integer.MAX_VALUE;
        return random.nextInt(min, max);
    }

    private static short getRandomShort(Min minAnnotation, Max maxAnnotation, ThreadLocalRandom random) {
        short min = minAnnotation != null ? minAnnotation.shortValue() : Short.MIN_VALUE;
        short max = maxAnnotation != null ? maxAnnotation.shortValue() : Short.MAX_VALUE;
        return (short) random.nextInt(min, max);
    }

    private static byte getRandomByte(Min minAnnotation, Max maxAnnotation, ThreadLocalRandom random) {
        byte min = minAnnotation != null ? minAnnotation.byteValue() : Byte.MIN_VALUE;
        byte max = maxAnnotation != null ? maxAnnotation.byteValue() : Byte.MAX_VALUE;
        return (byte) random.nextInt(min, max);
    }
}
