package edu.homework8.client_server_cyberbullying.server;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dictionary {
    private static final Map<String, List<String>> syberbullyingMap = new HashMap<>() {{
        put("личности",
            List.of("Не переходи на личности там, где их нет"));
        put("личность",
            List.of("Не переходи на личности там, где их нет"));
        put("оскорбления",
            List.of(
                "Если твои противники перешли на личные оскорбления, будь уверена — твоя победа не за горами",
                "Чем ниже интеллект, тем громче оскорбления"));
        put("глупый",
            List.of("А я тебе говорил, что ты глупый? Так вот, я забираю свои слова обратно... Ты просто бог идиотизма."));
        put("интеллект",
            List.of("Чем ниже интеллект, тем громче оскорбления"));
    }};

    private Dictionary() {}

    public static String getQuote(String key) {
        SecureRandom random = new SecureRandom();
        List<String> quotes = syberbullyingMap.getOrDefault(key.toLowerCase(), List.of("такого я еще не знаю"));
        return quotes.get(random.nextInt(0, quotes.size()));
    }
}
