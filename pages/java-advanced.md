# Продвинутая Java
### Содержание
1. [Домашнее задание 10](#домашнее-задание-10)
2. [Домашнее задание 11](#домашнее-задание-11)
3. [Проект 5: Быстрая рефлексия](#проект-5-быстрая-рефлексия)

### Домашнее задание 10
##### Задание 1
Часто при разработке для тестирования требуются случайные данные. В этом задании мы будем писать генератор объектов 
на основе рефлексии.

Пример вызова может выглядеть следующим образом:
```java
RandomObjectGenerator rog = ...;

var myClass = rog.nextObject(MyClass.class, "create");
var myRecord = rog.nextObject(MyRecord.class);
```

Полезные ссылки: https://docs.oracle.com/en/java/javase/21/docs//api/java.base/java/lang/reflect/Proxy.html

Реализуйте поддержку генерации `record` и `POJO`. Поддерживать создание объектов нужно только через конструкторы или 
фабричный метод (если такой есть, например, `MyClass#create`).

Реализовывать поддержку списков, словарей и других сложных структур не требуется, но нужно написать код так, чтобы 
добавить новый тип было не очень сложно.

После этого создайте несколько аннотаций:
```
@NotNull
@Min(value)
@Max(value)
```

Сделайте так, чтобы ваш генератор учитывал эти аннотации, если они присутствуют на полях конструктора или фабричного 
метода.

Решение: [../homework10/random_object_generator/](../src/main/java/edu/homework10/random_object_generator)

##### Задание 2
Реализуйте кэширующих прокси для возвращаемых значений:
```java
public interface FibCalculator {
    @Cache(persist = true) 
    public long fib(int number);
}

FibCalculator c = ...;
FibCalculator proxy = CacheProxy.create(c, c.getClass());
```

У аннотации `@Cache` есть опциональный параметр `persist`, который сохраняет результаты на диск, например, во 
временный каталог.

Интерфейс `FibCalculator` приведён для примера, `CacheProxy` должен уметь работать и с другими интерфейсами.

В задании может пригодиться интерфейс `InvocationHandler`.

Решение: [../homework10/cache_proxy/](../src/main/java/edu/homework10/cache_proxy)

### Домашнее задание 11
В этом задании мы будем работать с генерацией и редактированием байткода JVM.

Понятно, что делать это мы будем не на уровне битов и байтов, а использовать готовые методы и классы.

Для задания нам потребуется библиотека `ByteByddy`, которую нужно подключить как зависимость в pom.xml:
```xml
<properties>
    <bytebuddy.version>1.14.8</bytebuddy.version>
    <asm.version>9.6</asm.version>
</properties>

<dependencies>
    <dependency>
        <groupId>org.ow2.asm</groupId>
        <artifactId>asm</artifactId>
        <version>${asm.version}</version>
    </dependency>

    <dependency>
        <groupId>net.bytebuddy</groupId>
        <artifactId>byte-buddy-agent</artifactId>
        <version>${bytebuddy.version}</version>
    </dependency>
    <dependency>
        <groupId>net.bytebuddy</groupId>
        <artifactId>byte-buddy</artifactId>
        <version>${bytebuddy.version}</version>
    </dependency>
</dependencies>
```

Большинство заданий можно реализовывать сразу в виде тестов.

* примеры использования: https://github.com/raphw/byte-buddy/blob/master/byte-buddy-dep/src/test/java/net/bytebuddy/ByteBuddyTutorialExamplesTest.java
* официальные уроки: http://bytebuddy.net/#/tutorial

##### Задание 1
При помощи библиотеки `ByteBuddy` создайте новый класс, метод `toString` которого выводит `"Hello, ByteBuddy!"`.

Решение: [../homework11/hello_world/](../src/test/java/edu/homework11/hello_world)

##### Задание 2
При помощи библиотеки `ByteBuddy` напишите функцию, которая изменяет поведение существующего класса на лету. 
Например, в классе:
```java
class ArithmeticUtils { 
    public int sum(int a, int b) {
        return a + b;
    }
}
```

вместо + будет производиться операция *.

Решение: [../homework11/arithmetic_utils/](../src/test/java/edu/homework11/arithmetic_utils)

##### Задание 3
При помощи библиотеки `ByteBuddy` cоздайте новый класс и метод с сигнатурой `long fib(int n)`, для генерации кода 
требуется использовать класс `ByteCodeAppender`.

В задании может пригодиться официальная документация: https://asm.ow2.io/developer-guide.html.

Вы не должны использовать делегаты к методам других классов. Функцию нужно написать при помощи классов библиотеки ASM:
```java
MethodVisitor mv = new MethodVisitor(...);
mv.visitVarInsn(Opcodes.ILOAD,1);
mv.visitJumpInsn(Opcodes.IFEQ,...);
// ...
mv.visitInsn(Opcodes.LRETURN);
```

Решение: [../homework11/fibonacci/](../src/test/java/edu/homework11/fibonacci)

### Проект 5: Быстрая рефлексия
Подробное описание проекта можно посмотреть [тут](./project5.md).

Представим, что мы пишем программу для сериализации/десериализации данных, например, из `POJO` в `JSON`.

Другими словами, мы хотим чтобы класс
```java
public record Student(String name, String surname) { }
```

автоматически конвертировался в
```json
{
    "name": "...", 
    "surname": "..."
}
```
Конвертер такого типа нужно писать максимально общим, потому что мы можем не знать, что нам приходит на вход, из-за 
полиморфных коллекций, в которых происходит стирание типов, т.е. метод должен всегда принимать `Object`:
```java
JsonMapper mapper = ...;
Object o = new Student("Alexander","Biryukov");
var json = mapper.convert(o);
```

Тратить ресурсы на сканирование объекта, поиск полей и т.п. каждый раз очень дорого, поэтому конвертеры кэшируют такую 
информацию.

Но даже с кэшированием рефлексия не будет такой же быстрой, как если бы мы написали код руками, потому что 
`java.lang.reflect.Method` перед каждым вызовом будет проверять права на вызов (см справочную информацию).

Поэтому в Java появилось несколько альтернативных API для вызова методов через рефлексию.

Цикл статей про рефлексию:
* https://blogs.oracle.com/javamagazine/post/java-reflection-introduction
* https://blogs.oracle.com/javamagazine/post/java-reflection-performance
* https://blogs.oracle.com/javamagazine/post/java-reflection-method-handles

Полезные ссылки:
* https://github.com/openjdk/jmh/tree/master/jmh-samples/src/main/java/org/openjdk/jmh/samples
* https://habr.com/ru/articles/318418/
* https://habr.com/ru/companies/haulmont/articles/431922/
* https://habr.com/ru/companies/haulmont/articles/432418/

Альтернативным способом является кодогенерация. Например, так сделано во фреймворке Kora от Тинькофф.

Плюсы:
* не нужно тратить время в runtime на анализ классов (рефлексия)
* максимальная производительность

Минусы:
* требуется как-то встроить кодогенерацию
* не поддерживаются типы из сторонних библиотек

В этом задании вам потребуется реализовать и сравнить производительность 4 способов обращения к методу `Student#name()` 
(или любого другого класса/интерфейса):
* Прямой доступ
* java.lang.reflect.Method
* java.lang.invoke.MethodHandles
* java.lang.invoke.LambdaMetafactory

Скорость обращения к полю обычно изменяется в наносекундах и сильно зависит от текущего состояния операционной системы, 
поэтому такие замеры делаются при помощи специальной библиотеки под названием JMH - Java Microbenchmark Harness.

Ваше задание состоит в том, чтобы написать набор JMH-тестов для каждого сценария и сравнить, насколько медленнее 
работает каждый из способов по сравнению с прямым доступом.

Помимо кода в MR приложите финальную таблицу JMH-тестов (в описание).

На что обратить внимание:
* Вся подготовительная работа (получение `Method`, вызовы `LambdaMetafactory` и т.п.) должны производиться в @Setup-методе (!), это важно, потому что мы замеряем производительность вызова.
* Не забудьте про Blackhole-параметр и вызов consume. Без него оптимизирующий компилятор JVM может посчитать код "мертвым" и вы получите некорректные данные.
* Для получения финальных результатов нужно оставить компьютер без фоновой нагрузки и увеличить время выполнения бенчмарка до пары минут

Как подключить JMH:
```xml
<properties>
<jmh.version>1.37</jmh.version>
</properties>

<dependencies>
   <dependency>
      <groupId>org.openjdk.jmh</groupId>
      <artifactId>jmh-core</artifactId>
      <version>${jmh.version}</version>
   </dependency>
   <dependency>
      <groupId>org.openjdk.jmh</groupId>
      <artifactId>jmh-generator-annprocess</artifactId>
      <version>${jmh.version}</version>
      <scope>provided</scope>
   </dependency>
</dependencies>
```

Пример теста:
```java
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

@State(Scope.Thread)
public class ReflectionBenchmark { 
    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
            .include(ReflectionBenchmark.class.getSimpleName())
            .shouldFailOnError(true)
            .shouldDoGC(true)
            .mode(Mode.AverageTime)
            .timeUnit(TimeUnit.NANOSECONDS)
            .forks(1)
            .warmupForks(1)
            .warmupIterations(1)
            .warmupTime(TimeValue.seconds(5))
            .measurementIterations(1)
            .measurementTime(TimeValue.seconds(5))
            .build();
        new Runner(options).run();
    }

    record Student(String name, String surname) { }

    private Student student;
    private Method method;

    @Setup
    public void setup() throws NoSuchMethodException {
        student = new Student("Alexander", "Biryukov");
        method = ...;
    }

    @Benchmark
    public void directAccess(Blackhole bh) {
        String name = student.name();
        bh.consume(name);
    }

    @Benchmark
    public void reflection(Blackhole bh) throws InvocationTargetException, IllegalAccessException {
        // TODO
    }
}
```

Решение: [ReflectionBenchmark.java](../src/test/java/edu/project5/ReflectionBenchmark.java)
