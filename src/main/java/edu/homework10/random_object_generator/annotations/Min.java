package edu.homework10.random_object_generator.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = ElementType.PARAMETER)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Min {
    byte byteValue() default Byte.MIN_VALUE;

    short shortValue() default Short.MIN_VALUE;

    int intValue() default Integer.MIN_VALUE;

    long longValue() default Long.MIN_VALUE;

    float floatValue() default Float.MIN_VALUE;

    double doubleValue() default Double.MIN_VALUE;

    char charValue() default Character.MIN_VALUE;
}
