package edu.homework10.random_object_generator.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = ElementType.PARAMETER)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Max {
    byte byteValue() default Byte.MAX_VALUE;

    short shortValue() default Short.MAX_VALUE;

    int intValue() default Integer.MAX_VALUE;

    long longValue() default Long.MAX_VALUE;

    float floatValue() default Float.MAX_VALUE;

    double doubleValue() default Double.MAX_VALUE;

    char charValue() default Character.MAX_VALUE;
}
