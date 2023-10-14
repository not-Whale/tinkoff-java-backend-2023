package edu.project1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

class Dictionary {
    private static final String REQUEST_URL = "http://free-generator.ru/generator.php?action=word&type=1";

    private static final String KEY = "word";

    private static final String DEFAULT_WORD = "тинькофф";

    private Dictionary() {}

    public static String randomWord() {
        try {
            return parseRandomWordFromJSON(getJSON());
        } catch (IOException | InterruptedException e) {
            return DEFAULT_WORD;
        }
    }

    private static String getJSON() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(REQUEST_URL))
            .GET()
            .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    private static String parseRandomWordFromJSON(String jsonStr) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Map<String, String>> jsonToMap = objectMapper.readValue(jsonStr, new TypeReference<>() {});
        return jsonToMap.get(KEY).get(KEY).toLowerCase();
    }
}
