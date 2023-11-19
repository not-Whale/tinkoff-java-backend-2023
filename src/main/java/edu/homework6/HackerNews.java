package edu.homework6;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HackerNews {
    private static final String TOP_STORIES_URL = "https://hacker-news.firebaseio.com/v0/topstories.json";

    private static final String ITEM_URL = "https://hacker-news.firebaseio.com/v0/item/";

    private static final String JSON_TYPE_FILE = ".json";

    private static final String NEWS_TITLE_PATTERN = "\"title\":\"([\\w\\s-?!,.]*)\"";

    private HackerNews() {}

    public static Long[] hackerNewsTopStories() {
        String response = requestNewsTopStoriesEndpoint();
        String storiesList = response.substring(1, response.length() - 1);
        return Arrays.stream(storiesList.split(", "))
            .map(Long::parseLong)
            .toArray(Long[]::new);
    }

    public static String news(Long id) {
        String response = requestItemEndpoint(id);
        Pattern pattern = Pattern.compile(NEWS_TITLE_PATTERN);
        Matcher matcher = pattern.matcher(response);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }

    private static String requestItemEndpoint(Long item) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(ITEM_URL + item + JSON_TYPE_FILE))
            .GET()
            .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (IOException | InterruptedException e) {
            return "";
        }
    }

    private static String requestNewsTopStoriesEndpoint() {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(TOP_STORIES_URL))
            .GET()
            .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (IOException | InterruptedException e) {
            return "";
        }
    }
}
