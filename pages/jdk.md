# Стандартная библиотка JDK
### Содержание
1. [Домашнее задание 5](#домашнее-задание-5)
2. [Домашнее задание 6](#домашнее-задание-6)
3. [Проект 3: Анализатор логов](#проект-3-анализатор-логов)

### Домашнее задание 5
##### Задание 1
Вас попросили сделать аналитику для компьютерного клуба: нужно посчитать, сколько времени в среднем посетители 
проводят времени за один сеанс.

На вход функции даётся набор строк вида `2022-03-12, 20:20 - 2022-03-12, 23:50`.

Например, для входных данных
```
2022-03-12, 20:20 - 2022-03-12, 23:50
2022-04-01, 21:30 - 2022-04-02, 01:20
```

Вывод должен быть `3ч 40м`.

Программа не должна учитывать часовые пояса, дополнительные секунды и другие особые случаи - день может длиться 
ровно 24 часа.

Для решения задания может пригодиться класс `Duration`.

Решение: [../homework5/computer_club/](../src/main/java/edu/homework5/computer_club)

##### Задание 2
Напишите программу, которая ищет все пятницы, выпадающие на 13-е число в заданном году.

Для 1925 года вывод может выглядеть следующим образом: `[1925-02-13, 1925-03-13, 1925-11-13]`. 
Для 2024 года: `[2024-09-13, 2024-12-13]`.

После этого используя `TemporalAdjuster`, напишите функцию, которая для заданной даты ищет следующую ближайшую 
пятницу 13.

Решение: [../homework5/unlucky_friday/](../src/main/java/edu/homework5/unlucky_friday)

##### Задание 3
Существует много способов указать дату, например:
```
2020-10-10
2020-12-2
1/3/1976
1/3/20
tomorrow
today
yesterday
1 day ago
2234 days ago
```

Напишите метод `Optional<LocalDate> parseDate(String string)`, который распознает перечисленные выше форматы.

Если строка передана в одном из форматов, то функция должна преобразовать ее в `LocalDate` и вернуть в `Optional`. 
Если ни один из форматов не подошёл, то возвращается `Optional.empty()`.

В задании может пригодиться шаблон:
* https://www.baeldung.com/chain-of-responsibility-pattern
* https://java-design-patterns.com/patterns/chain-of-responsibility
* https://refactoring.guru/ru/design-patterns/chain-of-responsibility/java/example

Решение: [../homework5/date_parser/](../src/main/java/edu/homework5/date_parser)

##### Задание 4
Предположим, что в целях безопасности вы требуете, чтобы все пароли содержали хотя бы один из следующих символов:
```
~ ! @ # $ % ^ & * |
```

Напишите регулярное выражение, которое возвращает true тогда и только тогда, когда пароль содержит один из требуемых 
символов.

Решение: [../homework5/password_validator/](../src/main/java/edu/homework5/password_validator)

##### Задание 5
Напишите регулярное выражение для валидации российских номерных знаков.

Примеры правильных номерных знаков:
```
А123ВЕ777
О777ОО177
```

Примеры неправильных номерных знаков:
```
123АВЕ777
А123ВГ77
А123ВЕ7777
```

Решение: [../homework5/vehicle_registration_plates_validator/](../src/main/java/edu/homework5/vehicle_registration_plates_validator)

##### Задание 6
Напишите функцию, которая определяет, что заданная строка S является подпоследовательностью другой строки T.

Например, `abc` является подпоследовательностью `achfdbaabgabcaabg`.

Решите задачу при помощи регулярных выражений.

Решение: [../homework5/subsequence_validator/](../src/main/java/edu/homework5/subsequence_validator)

##### Задание 7
Для отладки можно использовать сайты:
* https://regex101.com
* https://www.debuggex.com

Важно: при отладке выбирайте правильную версию движка.

Напишите регулярные выражения для строк из алфавита {0, 1} (это важно и нужно использовать при решении):
* содержит не менее 3 символов, причем третий символ равен 0
* начинается и заканчивается одним и тем же символом
* длина не менее 1 и не более 3

Решение: [RegexEx.java](../src/main/java/edu/homework5/regex_excercies/RegexEx.java)

### Задание 8 (бонусное, +1 балл)
Для получения бонуса нужно решить хотя бы половину.

Напишите регулярные выражения для строк из алфавита {0, 1}:
* нечетной длины
* начинается с 0 и имеет нечетную длину, или начинается с 1 и имеет четную длину
* количество 0 кратно 3
* любая строка, кроме 11 или 111
* каждый нечетный символ равен 1
* содержит не менее двух 0 и не более одной 1
* нет последовательных 1

Решение: [RegexExPro.java](../src/main/java/edu/homework5/regex_excercies/RegexExPro.java)

### Домашнее задание 6
##### Задание 1
Реализуйте класс `DiskMap`, который представляет собой ассоциативный массив, хранящий пары ключ-значение на жестком 
диске. Класс должен реализовывать интерфейс `Map<String, String>`.

Ключи и значения должны быть сохранены на жестком диске в файле в формате "ключ:значение". Класс должен поддерживать 
сохранение и загрузку из файла на диске.

Решение: [../homework6/disk_map/](../src/main/java/edu/homework6/disk_map)

##### Задание 2
При копировании файла в ту же папку в Проводнике Windows создается его копия, копия автоматически получает новое имя. 
Воспроизведём это поведение.

Напишите функцию `cloneFile(Path path)`, которая создает копию файла с новым именем.

Например, файл называется `Tinkoff Bank Biggest Secret.txt`. Тогда новые имена файлов должны выглядеть следующим 
образом:
```
Tinkoff Bank Biggest Secret.txt
Tinkoff Bank Biggest Secret — копия.txt
Tinkoff Bank Biggest Secret — копия (2).txt
Tinkoff Bank Biggest Secret — копия (3).txt
```

Решение: [../homework6/file_cloner/](../src/main/java/edu/homework6/file_cloner)

##### Задание 3
Класс `Files` предоставляет три статических метода для запроса всех записей в каталоге:
```
newDirectoryStream(Path dir)
newDirectoryStream(Path dir, String glob)
newDirectoryStream(Path dir, DirectoryStream.Filter<? super Path> filter)
```

Результатом всегда является `DirectoryStream<Path>`. Первый метод не фильтрует результаты, второй метод позволяет 
использовать glob-строку, например `*.txt`, а третий метод позволяет использовать произвольный фильтр.

`java.nio.file.DirectoryStream.Filter<T>` - это интерфейс-предикат, который должны реализовывать фильтры. 
В JDK объявлен интерфейс, но нет реализаций.

Напишите реализации для `DirectoryStream.Filter`, которые проверяют:
* атрибуты (например, readable, writable)
* размер файла
* расширения файлов
* имя файла с помощью регулярных выражений
* магические начальные идентификаторы

Итоговый API нужно сделать цепочечным, т.е. должна быть возможность объединить все фильтры в один:
```java
public interface AbstractFilter extends DirectoryStream.Filter<Path> {
    // TODO
}

public static final AbstractFilter regularFile = Files::isRegularFile;
public static final AbstractFilter readable = Files::isReadable;

DirectoryStream.Filter<Path> filter = regularFile
    .and(readable)
    .and(largerThan(100_000))
    .and(magicNumber(0x89, 'P', 'N', 'G'))
    .and(globMatches("*.png"))
    .and(regexContains("[-]"));

try (DirectoryStream<Path> entries = Files.newDirectoryStream(dir, filter)) {
    entries.forEach(System.out::println);
}
```

Решение: [../homework6/directory_stream_filter/](../src/main/java/edu/homework6/directory_stream_filter)

##### Задание 4
В этом задании мы будем делать композицию OutputStream'ов, стрелка -> указывает, кто куда пишет:
```
PrintWriter -> OutputStreamWriter -> BufferedOutputStream -> CheckedOutputStream -> file OutputStream.
```

При построении цепочек такого типа всегда следует начинать с самого нижнего уровня:
* Создайте файл (`Files.new*(...)`) и получите из него OutputStream
* Добавьте к нему `CheckedOutputStream` для проверки записи при помощи контрольной суммы
* Для буферизации данных добавьте `BufferedOutputStream`
* Чтобы не работать с сырыми байтами добавьте `OutputStreamWriter`, и включите поддержку UTF-8.
* Добавьте финальный PrintWriter и запишите в файл текст: `Programming is learned by writing programs. ― Brian Kernighan`

Не забудьте закрыть ресурсы с помощью `try-with-resources`.

Решение: [../homework6/stream_composition/](../src/main/java/edu/homework6/stream_composition)

##### Задание 5
Hacker News - это сайт с актуальными обсуждениями технологических тенденций. Доступ к статьям осуществляется через 
веб-сервис, а документацию можно найти на сайте https://github.com/HackerNews/API.

Нас интересуют 2 endpoint'а:
* https://hacker-news.firebaseio.com/v0/topstories.json: возвращает JSON-массив с идентификаторами наиболее обсуждаемых статей.
* https://hacker-news.firebaseio.com/v0/item/37570037.json: возвращает JSON-объект, содержащий сообщение с идентификатором 37570037.

Создайте класс `HackerNews`. Реализуйте метод `long[] hackerNewsTopStories()`, который будет:
* делать HTTP-запрос при помощи `HttpClient` к https://hacker-news.firebaseio.com/v0/topstories.json
* конвертировать возвращаемый `JSON` в `long[]`

В общем случае для чтения JSON используются специальные парсеры, но т.к. структура массива очень простая, то 
можем обойтись без них. В реальном приложении, конечно, мы бы использовали библиотеку, например, Jackson.

В случае ошибки ввода-вывода должен быть возвращен пустой массив.

Напишите метод `String news(long id)`, который возвращает название новости.

Для получения имени новости используйте регулярное выражение.

Пример:
```java
System.out.println(Arrays.toString(hackerNewsTopStories()));
String newsTitle = news(37570037);
System.out.println(newsTitle);
```

Решение: [../homework6/hacker_news/](../src/main/java/edu/homework6/hacker_news)

##### Задание 6
Напишите программу, которая сканирует порты и определяет заняты они или нет.

Для этого нужно зарегистрировать `ServerSocket` и `DatagramSocket` на всех TCP/UDP-портах от 0 до 49151.

В случае успеха порт свободен, в противном случае он занят.

Дополнительно выведите информацию о потенциальном приложении, которое использует этот порт, список известных портов.

Выберите несколько, не нужно брать всё, например, https://www.tutorialspoint.com/50-common-ports-you-should-know.

Пример вывода программы:
```
Протокол  Порт   Сервис
TCP       135    EPMAP
UDP       137    Служба имен NetBIOS
UDP       138    Служба датаграмм NetBIOS
TCP       139    Служба сеансов NetBIOS
TCP       445    Microsoft-DS Active Directory
TCP       843    Adobe Flash
UDP       1900   Simple Service Discovery Protocol (SSDP)
UDP       3702   Динамическое обнаружение веб-служб
TCP       5040   
UDP       5050   
UDP       5353   Многоадресный DNS
UDP       5355   Link-Local Multicast Name Resolution (LLMNR)
TCP       5939   
TCP       6463   
TCP       6942   
TCP       17500  Dropbox
UDP       17500  Dropbox
TCP       17600
TCP       27017  MongoDB
UDP       42420
```

Решение: [../homework6/port_scanner/](../src/main/java/edu/homework6/port_scanner)

### Проект 3: Анализатор логов
Лог-файлы являются важной частью работы любого сервера, так как они содержат информацию о том, какие запросы были 
отправлены на сервер, какие ошибки возникли и какие действия были выполнены.

Однако, обрабатывать и анализировать эти логи вручную может быть очень трудоемким процессом. Для решения этой проблемы 
напишем программу-анализатор логов.

Подробное описание проекта можно посмотреть [тут](./project3.md).

На вход программе через аргументы командной строки задаётся:
* путь к одному или нескольким NGINX лог-файлам в виде локального шаблона или URL
* опциональные временные параметры from и to в формате `ISO8601`
* необязательный аргумент формата вывода результата: markdown или adoc

Примеры вызова программы:
```shell
java -jar nginx-log-stats.jar --path logs/2023* --from 2023-08-31 --format markdown

java -jar nginx-log-stats.jar --path https://raw.githubusercontent.com/elastic/examples/master/Common%20Data%20Formats/nginx_logs/nginx_logs --format adoc

java -jar nginx-log-stats.jar --path logs/**/2023-08-31.txt
```

Программа должна выполнять следующие задачи:
* Подсчитывать общее количество запросов
* Определять наиболее часто запрашиваемые ресурсы
* Определять наиболее часто встречающиеся коды ответа
* Рассчитывать средний размер ответа сервера

Пример вывода:
```markdown
#### Общая информация

|        Метрика        |     Значение |
|:---------------------:|-------------:|
|       Файл(-ы)        | `access.log` |
|    Начальная дата     |   31.08.2023 |
|     Конечная дата     |            - |
|  Количество запросов  |       10_000 |
| Средний размер ответа |         500b |

#### Запрашиваемые ресурсы

|     Ресурс      | Количество |
|:---------------:|-----------:|
|  `/index.html`  |      5_000 |
|  `/about.html`  |      2_000 |
| `/contact.html` |      1_000 |

#### Коды ответа

| Код |          Имя          | Количество |
|:---:|:---------------------:|-----------:|
| 200 |          OK           |       8000 |
| 404 |       Not Found       |       1000 |
| 500 | Internal Server Error |        500 |
```

Программа-анализатор в конечном счете должна выглядеть как конвейер:
```
user input (file names, URL, etc)
=>
Stream<LogRecord>
=>
LogReport
=>
текстовый отчёт в формате .md/.adoc
```

Оценка:
* Всего за проект можно получить 6 баллов
* За каждую дополнительную статистику можно получить + 1 балл, но не более 2 баллов за всё задание

Схема NGINX-логов
```
'$remote_addr - $remote_user [$time_local] ' '"$request" $status $body_bytes_sent ' '"$http_referer" "$http_user_agent"'
```

* Логи нужно трансформировать в типизированное промежуточное представление
* Для работы с датами можно использовать классы `java.time.OffsetDateTime`
* Для чтения файлов можно использовать классы из пакета `NIO`
* Для сбора статистики используйте коллекции из стандартной библиотеки Java, например, `Map` и `List`

Примеры логов можно взять по ссылке или создать самостоятельно:
```shell
docker run --rm -it -e 'RATE=10000' kscarlett/nginx-log-generator >> $HOME/logs.txt
```

После запуска подождите какое-то время (5-10с) и отправьте завершающий сигнал (Ctrl+C). 
Ваши логи будут в файле `$HOME/logs.txt`.

Решение: [../project3/](../src/main/java/edu/project3)
