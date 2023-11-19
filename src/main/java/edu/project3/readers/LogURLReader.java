package edu.project3.readers;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class LogURLReader implements Reader {
    private static final String PROTOCOL_DOMAIN_PATTERN =
        "((http|https)://(www.)?)?[a-zA-Z0-9]{2,}(\\.[a-zA-Z0-9]{2,})*";

    private static final String SUBDOMAIN_PATTERN =
        "(/[a-z-A-Z0-9%_-]{2,})*";

    private static final String FILE_PATTERN =
        "(/[a-zA-Z0-9%_-]{2,}\\.[a-zA-Z0-9]{2,})?";

    private static final String URL_PATTERN =
        "^" + PROTOCOL_DOMAIN_PATTERN + SUBDOMAIN_PATTERN + FILE_PATTERN + "$";

    private final String path;

    public LogURLReader(String path) {
        this.path = path;
    }

    @Override
    public boolean canRead() {
        return path.matches(URL_PATTERN);
    }

    @Override
    public String[] readLogs() {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(path))
            .GET()
            .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();
            return responseBody.split(" ");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            return new String[0];
        }
    }
}
