# Основы Java
### Содержание
1. [Домашнее задание 1](#домашнее-задание-1)
2. [Домашнее задание 2](#домашнее-задание-2)
3. [Проект 1: Виселица]

### Домашнее задание 1
##### 0. Привет, мир!
Напишите функцию, которая выводит в консоль фразу "Привет, мир!" при помощи метода LOGGER.info().

Тестировать задание не нужно :)

Решение: [HelloWorld.java](../src/main/java/edu/homework1/HelloWorld.java)

##### 1. Длина видео
Дана строка с длиной видео в формате mm:ss, например 12:44. Напишите функцию, которая возвращает общую длину 
видео в секундах.

Примеры:
```
minutesToSeconds("01:00") -> 60 
minutesToSeconds("13:56") -> 836 
minutesToSeconds("10:60") -> -1 
```

Решение: [VideoLength.java](../src/main/java/edu/homework1/VideoLength.java)

##### 2. Количество цифр
Напишите функцию, которая возвращает количество цифр в десятичной форме числа.

Пользоваться преобразованием в строку запрещено.

Примеры:
```
countDigits(4666) -> 4
countDigits(544) -> 3
countDigits(0) -> 1 
```

Решение: [NumberOfDigits.java](../src/main/java/edu/homework1/NumberOfDigits.java)

##### 3. Вложенный массив
Напишите функцию, которая возвращает true, если первый массив может быть вложен во второй, и false в противном случае.

Массив может быть вложен, если:
* min(a1) больше чем min(a2)
* max(a1) меньше, чем max(a2)

Примеры:
```
isNestable([1, 2, 3, 4], [0, 6]) -> true
isNestable([3, 1], [4, 0]) -> true
isNestable([9, 9, 8], [8, 9]) -> false
isNestable([1, 2, 3, 4], [2, 3]) -> false
```

Решение: [NestedArray.java](../src/main/java/edu/homework1/NestedArray.java)

##### 4. Сломанная строка
оПомигети псаривьтс ртко!и

Ой, имелось ввиду: "Помогите исправить строки!"

Все мои строки перепутались и каждая пара символов поменялась местами.

Напишите функцию, которая исправляет такие строки и возвращает правильный порядок.

Примеры:
```
fixString("123456") -> "214365"
fixString("hTsii  s aimex dpus rtni.g") -> "This is a mixed up string."
fixString("badce") -> "abcde"
```

Решение: [BrokenString.java](../src/main/java/edu/homework1/BrokenString.java)

##### 5. Особый палиндром
Будем называть потомком числа новое число, которое создается путем суммирования каждой пары соседних цифр.

Например, число 123312 не является палиндромом, но его потомок 363 - является:
```
3 = 1 + 2
6 = 3 + 3
3 = 1 + 2
```

Напишите функцию, которая будет возвращать true, если число является палиндромом или если любой из его потомков 
длиной > 1 (как минимум 2 цифры) является палиндромом.

Примеры:
```
isPalindromeDescendant(11211230) -> true // 11211230 -> 2333 -> 56 -> 11
isPalindromeDescendant(13001120) -> true // 13001120 -> 4022 ➞ 44
isPalindromeDescendant(23336014) -> true // 23336014 -> 5665
isPalindromeDescendant(11) -> true
```

Решение: [SpecialPalindrome.java](../src/main/java/edu/homework1/SpecialPalindrome.java)

##### 6. Постоянная Капрекара
Выберем любое четырёхзначное число n, больше 1000, в котором не все цифры одинаковы.

Расположим цифры сначала в порядке возрастания, затем в порядке убывания. Вычтем из большего меньшее. Производя 
перестановки цифр и вычитания, нули следует сохранять. Описанное действие назовём функцией Капрекара K(n).

Повторяя этот процесс с получающимися разностями, не более чем за семь шагов мы получим число 6174, которое будет 
затем воспроизводить само себя.

Это свойство числа 6174 было открыто в 1949 году. индийским математиком Д. Р. Капрекаром, в честь которого оно и 
получило своё название.

Пример выполнения K(3524):
```
5432 – 2345 = 3087
8730 – 0378 = 8352
8532 – 2358 = 6174
7641 – 1467 = 6174
```

Требуется написать рекурсивную функцию, которая для заданного числа будет возвращать количество шагов, которые 
нужно сделать чтобы получить 6174.

Например, для числа выше ответ будет равен 3.

Другие примеры:
```
countK(6621) -> 5
countK(6554) -> 4
countK(1234) -> 3
```

Решение: [KaprekarsRoutine.java](../src/main/java/edu/homework1/KaprekarsRoutine.java)

##### 7. Циклический битовый сдвиг
В Java есть базовые битовые операции, но нет циклического сдвига битов.

Напишите 2 функции:
```java
int rotateLeft(int n, int shift);
int rotateRight(int n, int shift);
```

где
* n -- целое число положительное число
* shift -- размер циклического сдвига

Примеры:
```
rotateRight(8, 1) -> 4 // 1000 -> 0100
rotateLeft(16, 1) -> 1 // 10000 -> 00001
rotateLeft(17, 2) -> 6 // 10001 -> 00110
```

Решение: [CyclicBitShift.java](../src/main/java/edu/homework1/CyclicBitShift.java)

##### 8. Кони на доске
Напишите функцию, которая возвращает true, если кони расставлены на шахматной доске так, что ни один конь не
может захватить другого коня.

На вход подаётся двумерный массив размера 8х8, где 0 означает пустую клетку, а 1 - занятую конём клетку.

Примеры:
```
knightBoardCapture([
[0, 0, 0, 1, 0, 0, 0, 0],
[0, 0, 0, 0, 0, 0, 0, 0],
[0, 1, 0, 0, 0, 1, 0, 0],
[0, 0, 0, 0, 1, 0, 1, 0],
[0, 1, 0, 0, 0, 1, 0, 0],
[0, 0, 0, 0, 0, 0, 0, 0],
[0, 1, 0, 0, 0, 0, 0, 1],
[0, 0, 0, 0, 1, 0, 0, 0]
]) -> true

knightBoardCapture([
[1, 0, 1, 0, 1, 0, 1, 0],
[0, 1, 0, 1, 0, 1, 0, 1],
[0, 0, 0, 0, 1, 0, 1, 0],
[0, 0, 1, 0, 0, 1, 0, 1],
[1, 0, 0, 0, 1, 0, 1, 0],
[0, 0, 0, 0, 0, 1, 0, 1],
[1, 0, 0, 0, 1, 0, 1, 0],
[0, 0, 0, 1, 0, 1, 0, 1]
]) -> false

knightBoardCapture([
[0, 0, 0, 0, 1, 0, 0, 0],
[0, 0, 0, 0, 0, 1, 0, 0],
[0, 0, 0, 1, 0, 0, 0, 0],
[1, 0, 0, 0, 0, 0, 0, 0],
[0, 0, 0, 0, 1, 0, 0, 0],
[0, 0, 0, 0, 0, 1, 0, 0],
[0, 0, 0, 0, 0, 1, 0, 0],
[1, 0, 0, 0, 0, 0, 0, 0]
]) -> false
```

Решение: [Knights.java](../src/main/java/edu/homework1/Knights.java)

### Домашнее задание 2
##### 1. Калькулятор выражений
Напишите иерархию классов для вычисления математических выражений.

```java
public sealed interface Expr {
double evaluate();
    public record Constant implements Expr {}
    public record Negate implements Expr {}
    public record Exponent implements Expr {}
    public record Addition implements Expr {}
    public record Multiplication implements Expr {}
}
```

Разбор строковых представлений выражений не требуется, достаточно чтобы работал код вида:
```java
var two = new Constant(2);
var four = new Constant(4);
var negOne = new Negate(new Constant(1));
var sumTwoFour = new Addition(two, four);
var mult = new Multiplication(sumTwoFour, negOne);
var exp = new Exponent(mult, 2);
var res = new Addition(exp, new Constant(1));

System.out.println(res + " = " + res.evaluate());
```

Решение: [../homework2/calculator/](../src/main/java/edu/homework2/calculator)

##### 2. Квадрат и прямоугольник
Что может быть проще наследования... Думают все начинающие программисты.

На практике оказывается, что применение наследования очень ограничено и в реальности почти всегда лучше использовать 
композицию или относительно "глупые" sealed-интерфейсы (ADT).

Чтобы продемонстрировать утверждение выше, попробуем разрешить классический парадокс прямоугольника и квадрата.

Допустим, у вас есть классы `Rectangle` и `Square` с методами `setWidth`, `setHeight` и `area`:
```java
public class Rectangle {  
private int width;  
private int height;

    void setWidth(int width) {  
        this.width = width;  
    }  
  
    void setHeight(int height) {  
        this.height = height;  
    }  
  
    double area() {  
        return width * height;  
    }  
}

public class Square extends Rectangle {  
@Override  
void setWidth(int width) {  
super.setHeight(width);  
super.setWidth(width);  
}

    @Override  
    void setHeight(int height) {  
        super.setHeight(height);  
        super.setWidth(height);  
    }  
}
```

И есть следующий тест:
```java
static Arguments[] rectangles() {
    return new Arguments[] {
        Arguments.of(new Rectangle()), 
        Arguments.of(new Square())
    };
}

@ParameterizedTest  
@MethodSource("rectangles")  
void rectangleArea(Rectangle rect) {
    rect.setWidth(20);
    rect.setHeight(10);
    assertThat(rect.area()).isEqualTo(200.0);  
}
```

Если вы запустите этот код, то вы увидите, что один из тестов падает.

Проблема этого кода заключается в нарушении принципа подстановки Лисков: если можно написать хоть какой-то осмысленный 
код, в котором замена объекта базового класса на объекта класса потомка, его сломает, то тогда не стоит их друг от 
друга-то наследовать.

Мы должны расширять поведение базового класса в потомках, а не существенным образом изменять его. Функции, которые 
используют базовый класс, должны иметь возможность использовать объекты подклассов, не зная об этом.
К сожалению проблемы с LSP встречаются постоянно даже в крупных проектах, например, в JDK:
```java
static Arguments[] lists() {
    return new Arguments[] {
        Arguments.of(new ArrayList<Integer>()), 
        Arguments.of(Collections.unmodifiableList(new ArrayList<Integer>()))
    };  
}

@ParameterizedTest  
@MethodSource("lists")  
void addElement(List<Integer> list) {
    list.add(1);
    assertThat(list).hasSize(1).containsExactly(1);  
}
```

Найдите решение проблемы, удовлетворяющее следующим ограничениям:
* Не нарушается математическая логика, то есть квадрат является прямоугольником с точки зрения типизации
* При этом не нарушается принцип подстановки
* Все так же присутствует понятие высоты и ширины, а также операция вычисления площади
* Реализация класса Rectangle не должна предполагать существование класса Square, иными словами, не нарушен принцип открытости-закрытости

Решение: [../homework2/rectangle/](../src/main/java/edu/homework2/rectangle)

##### 3. Удаленный сервер
Программист Иван хочет выполнять часто используемые консольные команды на удаленном сервере из Java-программы.

В распоряжении Ивана следующий набор интерфейсов:
```java
public interface Connection extends AutoCloseable {
void execute(String command);
}

public interface ConnectionManager {
Connection getConnection();
}

public class ConnectionException extends RuntimeException {}

public final class PopularCommandExecutor {
private final ConnectionManager manager;
private final int maxAttempts;

    public void updatePackages() {
	    tryExecute("apt update && apt upgrade -y");
    }

	void tryExecute(String command) { ... }
}
```

Пояснение:
* работа с сервером происходит через `Connection`, у которого есть метод `execute`
* чтобы получить соединение используется `ConnectionManager`
* при выполнении команды может возникнуть исключение `ConnectionException`

Помогите Ивану и реализуйте:
* 2 типа соединений: `StableConnection` / `FaultyConnection`, стабильное соединение работает всегда, проблемное соединение иногда бросает `ConnectionException`
* `DefaultConnectionManager`, который с некоторой вероятностью возвращает проблемное соединение
* `FaultyConnectionManager`, который всегда возвращает проблемное соединение
* Метод `tryExecute`, который должен попытаться выполнить переданную команду `maxAttempts` раз
* Если `tryExecute` не смог выполнить команду (превышено количество попыток), то нужно вернуть `ConnectionException`, при этом сохранив оригинальное исключение в параметре `cause`
* Обратите внимание, что `Connection` требуется закрывать (интерфейс `AutoCloseable`)

Решение: [../homework2/server/](../src/main/java/edu/homework2/server)

##### 4. Кто вызвал функцию?
Напишите статическую функцию `callingInfo`, которая возвращает

```java
public record CallingInfo(String className, String methodName) {}
```

Для получения доступа к стеку вызова используйте метод `Throwable#getStackTrace`.

Решение: [../homework2/tracer/](../src/main/java/edu/homework2/tracer)

### Проект 1: Виселица
Требуется написать консольную версию игры Виселица.

Подробное описание проекта можно посмотреть [тут](./project1.md).

Слово загадывается случайным образом из заранее заданного словаря, в словаре достаточно разместить всего несколько 
слов для демонстрации.

Также в игре должна быть возможность сдаться не дожидаясь конца игры, например, спец. команда или поддержка Ctrl+D 
(достаточно реализовать что-то одно).

Вы можете использовать любой промт, формат вывода и т.п.

Пример выигрыша:
```shell
> Guess a letter:
< a
> Missed, mistake 1 out of 5.
>
> The word: *****
>
> Guess a letter:
< b
> Missed, mistake 2 out of 5.
>
> The word: *****
>
> Guess a letter:
< e
> Hit!
>
> The word: *e***
>
> Guess a letter:
< o
> Hit!
>
> The word: *e**o
>
> Guess a letter:
< l
> Hit!
>
> The word: *ello
>
> Guess a letter:
< h
> Hit!
>
> The word: hello
>
> You won!
```

Пример проигрыша:
```shell
> Guess a letter:
< x
> Missed, mistake 1 out of 5.
>
> The word: ******
>
> Guess a letter:
< y
> Missed, mistake 2 out of 5.
>
> The word: ******
>
> Guess a letter:
< z
> Missed, mistake 3 out of 5.
>
> The word: ******
>
> Guess a letter:
< n
> Hit!
>
> The word: **n*n*
>
> Guess a letter:
< m
> Missed, mistake 4 out of 5.
>
> The word: **n*n*
>
> Guess a letter:
< o
> Missed, mistake 5 out of 5.
>
> The word: **n*n*
>
> You lost!
```

Примеры тестов:
* Игра не запускается, если загадываемое слово имеет некорректную длину
* После превышения заданного количества попыток игра всегда возвращает поражение
* Проверка, что состояние игры корректно изменяется (при угадывании/не угадывании)
* Проверка, что при отгадывании ввод строки длиной больше чем 1 (опечатка) приводит к повторному вводу, без изменения состояния

Решение: [../project1/](../src/main/java/edu/project1)
