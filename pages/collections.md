# Коллекции и последовательности
### Содержание
1. [Домашнее задание 3](#домашнее-задание-3)
2. [Домашнее задание 4](#домашнее-задание-4)
3. [Проект 2: Лабиринты](#проект-2-виселица)

### Домашнее задание 3
##### 1. Шифр Атбаш
Шифр Атбаша - это метод шифрования, при котором каждая буква слова заменяется на свою "зеркальную" букву в алфавите:
A <=> Z; B <=> Y; C <=> X и т.д.

Создайте функцию, которая принимает строку и применяет к ней шифр.

Замечания:

* используется латинский алфавит
* регистр букв нужно сохранить
* символы не латинского алфавита нужно писать как есть

Примеры (https://www.boxentriq.com/code-breaking/atbash-cipher):
```
atbash("Hello world!") -> "Svool dliow!"
atbash("Any fool can write code that a computer can understand. Good programmers write code that humans can understand. ― Martin Fowler") -> "Zmb ullo xzm dirgv xlwv gszg z xlnkfgvi xzm fmwvihgzmw. Tllw kiltiznnvih dirgv xlwv gszg sfnzmh xzm fmwvihgzmw. ― Nzigrm Uldovi"
```

Решение: [../homework3/atbash_cipher/](../src/main/java/edu/homework3/atbash_cipher)

##### 2. Кластеризация скобок
Напишите функцию, которая группирует строку в кластеры, заключенные в скобки. Каждый кластер должен быть
сбалансированным.

Примеры:
```
clusterize("()()()") -> ["()", "()", "()"]
clusterize("((()))") -> ["((()))"]
clusterize("((()))(())()()(()())") -> ["((()))", "(())", "()", "()", "(()())"]
clusterize("((())())(()(()()))") -> ["((())())", "(()(()()))"]
```

Решение: [../homework3/parenthesis_cluster/](../src/main/java/edu/homework3/parenthesis_cluster)

##### 3. Частота слов
На вход подаётся список объектов одного типа. Верните частотный словарь этого списка.

Примеры:
```
freqDict(["a", "bb", "a", "bb"]) → {"bb": 2, "a": 2}
freqDict(["this", "and", "that", "and"]) → {"that": 1, "and": 2, "this": 1}
freqDict(["код", "код", "код", "bug"]) → {"код": 3, "bug": 1}
freqDict([1, 1, 2, 2]) → {1: 2, 2: 2}
```

Решение: [../homework3/frequency_dictionary/](../src/main/java/edu/homework3/frequency_dictionary)

##### 4. Римские цифры
Создать функцию, которая принимает арабское число и преобразует его в римское.

Примеры:
```
convertToRoman(2) ➞ "II"
convertToRoman(12) ➞ "XII"
convertToRoman(16) ➞ "XVI"
```

Решение: [../homework3/roman_numerals/](../src/main/java/edu/homework3/roman_numerals)

##### 5. Список контактов
Напишите функцию сортировки, которая принимает массив имен, сортирует их по фамилии по возрастанию/убыванию (ASC/DESC)
и возвращает новый массив контактов с заданной сортировкой.

Замечания:
* если фамилия отсутствует, то нужно использовать имя
* возвращать нужно не строки, а объекты

Примеры:
```
parseContacts(["John Locke", "Thomas Aquinas", "David Hume", "Rene Descartes"], "ASC") -> ["Thomas Aquinas", "Rene Descartes", "David Hume", "John Locke"]
// Aquinas (A) < Descartes (D) < Hume (H) < Locke (L)

parseContacts(["Paul Erdos", "Leonhard Euler", "Carl Gauss"], "DESC") -> ["Carl Gauss", "Leonhard Euler", "Paul Erdos"]
// Gauss (G) > Erdos (ER) > Euler (EU)

parseContacts([], "DESC") -> []
parseContacts(null, "DESC") -> []
```

Решение: [../homework3/contacts/](../src/main/java/edu/homework3/contacts)

##### 6. Биржа
Реализуйте класс:

```java
interface StockMarket {
    /** Добавить акцию */
    void add(Stock stock);

    /** Удалить акцию */
    void remove(Stock stock);

    /** Самая дорогая акция */
    Stock mostValuableStock();
}
```

Внутри реализации используйте PriorityQueue для сортировки акций по цене.

Решение: [../homework3/stock_market/](../src/main/java/edu/homework3/stock_market)

##### 7. Дерево и null
Начиная с Java 8 в классе TreeMap нельзя использовать null в качестве ключа.

При этом, можно передать Comparator который будет обрабатывать null. Напишите и продемонстрируйте такой Comparator.

В результате у вас должен работать следующий код:
```java
TreeMap<String, String> tree = ...;
    tree.

add(null,"test");

assertThat(tree.contains(null)).

isTrue();
```

Решение: [../homework3/tree_map_with_nulls/](../src/main/java/edu/homework3/tree_map_with_nulls)

##### 8. Обратный итератор
Реализуйте Iterator<T>, который принимает коллекцию, но при этом двигается "назад".

То есть new `BackwardIterator<>(List.of(1,2,3))` должен сначала вернуть 3, потом 2, а потом 1.

Решение: [../homework3/backward_iterator/](../src/main/java/edu/homework3/backward_iterator)

### Домашнее задание 4
Для решения задачи используйте следующий класс:

```java
public record Animal(String name, Type type, Sex sex, int age, int height, int weight, boolean bites) {
    enum Type {
        CAT, DOG, BIRD, FISH, SPIDER
    }

    enum Sex {
        M, F
    }

    public int paws() {
        return switch (type) {
            case CAT, DOG -> 4;
            case BIRD -> 2;
            case FISH -> 0;
            case SPIDER -> 8;
        };
    }
}
```

##### Задача 1
Отсортировать животных по росту от самого маленького к самому большому -> List<Animal>

##### Задача 2
Отсортировать животных по весу от самого тяжелого к самому легкому, выбрать k первых -> List<Animal>

##### Задача 3
Сколько животных каждого вида -> Map<Animal.Type, Integer>

##### Задача 4
У какого животного самое длинное имя -> Animal

##### Задача 5
Каких животных больше: самцов или самок -> Sex

##### Задача 6
Самое тяжелое животное каждого вида -> Map<Animal.Type, Animal>

##### Задача 7
K-е самое старое животное -> Animal

##### Задача 8
Самое тяжелое животное среди животных ниже k см -> Optional<Animal>

##### Задача 9
Сколько в сумме лап у животных в списке -> Integer

##### Задача 10
Список животных, возраст у которых не совпадает с количеством лап -> List<Animal>

##### Задача 11
Список животных, которые могут укусить (bites == true) и рост которых превышает 100 см -> List<Animal>

##### Задача 12
Сколько в списке животных, вес которых превышает рост -> Integer

##### Задача 13
Список животных, имена которых состоят из более чем двух слов -> List<Animal>

##### Задача 14
Есть ли в списке собака ростом более k см -> Boolean

##### Задача 15
Найти суммарный вес животных каждого вида, которым от k до l лет -> Map<Animal.Type, Integer>

##### Задача 16
Список животных, отсортированный по виду, затем по полу, затем по имени -> List<Animal>

##### Задача 17
Правда ли, что пауки кусаются чаще, чем собаки -> Boolean (если данных для ответа недостаточно, вернуть false)

##### Задача 18
Найти самую тяжелую рыбку в 2-х или более списках -> Animal

##### Задача 19
Животные, в записях о которых есть ошибки: вернуть имя и список ошибок -> Map<String, Set<ValidationError>>.

Класс ValidationError и набор потенциальных проверок нужно придумать самостоятельно.

##### Задача 20
Сделать результат предыдущего задания более читабельным: вернуть имя и названия полей с ошибками, объединенные в
строку -> Map<String, String>

Решение: [AnimalsUtils.java](../src/main/java/edu/homework4/AnimalsUtils.java)

Вспомогательный класс: [Animal.java](../src/main/java/edu/homework4/Animal.java)

Класс ошибки: [ValidationError.java](../src/main/java/edu/homework4/ValidationError.java)

### Проект 2: Виселица
Лабиринт - структура, состоящая из запутанных путей. Лабиринт можно рассматривать как логическую головоломку, 
решение которой заключается в поиске пути от входа к выходу.

Подробное описание проекта можно посмотреть [тут](./project2.md).

Литература:
* о генерации лабиринтов https://habr.com/ru/articles/445378/
* поиск в глубину https://ru.algorithmica.org/cs/graph-traversals/dfs/
* поиск в ширину https://ru.algorithmica.org/cs/shortest-paths/bfs/

Существует множество алгоритмов генерации лабиринтов. В задании вы можете выбрать любой, например, поиск в 
глубину/ширину, алгоритм Крускала или алгоритм Эллера (см. ссылки).

После создания лабиринта, мы можем попытаться найти маршрут из клетки (x1, y1) в клетку (x2, y2). Аналогично предыдущей 
задаче существует несколько алгоритмов, например, подойдёт поиск в глубину/ширину или любой другой алгоритм (A*).

Требуется реализовать:
* 1 или более алгоритмов генерации лабиринта
* 1 или более алгоритмов решения лабиринта
* красивую печать в консоль (pretty print) лабиринта и маршрута из точки А в Б

В качестве тестов можете использовать следующие сценарии:
* Решение находит/не находит путь в заранее известном лабиринте и стартовой/конечной точке
* Отрисовка заранее известного лабиринта, вывод совпадает с ожидаемым
* Исключительные ситуации при создании и отрисовке лабиринта (неверные параметры и т.п.)

Оценка:
* Всего за проект можно получить 8 баллов
* За каждый дополнительный алгоритм можно получить + 1 балл, но не более 2 баллов за всё задание.

Решение: [../project2/](../src/main/java/edu/project2)
