package edu.homework8.brute_force.parser;

import java.util.HashMap;
import java.util.Map;

public class UserParser {
    private UserParser() {}

    public static Map<String, String> parse(String passwordsString) {
        if (passwordsString == null) {
            throw new IllegalArgumentException();
        }
        Map<String, String> usersMap = new HashMap<>();
        String[] users = passwordsString.split("\n");
        for (String user : users) {
            String[] parts = user.split(" ");
            if (parts.length != 2) {
                throw new IllegalArgumentException();
            }
            usersMap.put(parts[1], parts[0]);
        }
        return usersMap;
    }
}
