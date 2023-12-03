package edu.homework8.brute_force.brute_forcer;

import edu.homework8.brute_force.generators.Generator;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ParallelBruteForcer {
    private static final List<String> alphabet = List.of(
        "A", "B", "C", "D", "E", "F", "G", "H", "I", "K",
        "L", "M", "N", "O", "P", "Q", "R", "S", "T", "V",
        "X", "Y", "Z", "0", "1", "2", "3", "4", "5", "6",
        "7", "8", "9"
    );

    private static final int HASH_RADIX = 16;

    private static final int HASH_SIZE = 32;

    private final Map<String, String> usersMap;

    private final int passwordSize;

    private final int coreNumber;

    public ParallelBruteForcer(Map<String, String> usersMap, int passwordSize, int coreNumber) {
        this.passwordSize = passwordSize;
        this.usersMap = usersMap;
        this.coreNumber = coreNumber;
    }

    public Map<String, String> decode() {
        Map<String, String> passwords = new HashMap<>();
        ExecutorService executorService = Executors.newFixedThreadPool(coreNumber);
        List<Future<Map<String, String>>> futures = new ArrayList<>();
        for (int i = 1; i <= passwordSize; i++) {
            futures.add(executorService.submit(getTask(usersMap, i)));
        }
        try {
            for (var future: futures) {
                passwords.putAll(future.get());
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        return passwords;
    }

    private static Callable<Map<String, String>> getTask(Map<String, String> usersMap, int a) {
        return () -> {
            Map<String, String> currentPasswords = new HashMap<>();
            Generator generator = new Generator(alphabet, a);
            MessageDigest md = MessageDigest.getInstance("MD5");
            while (generator.hasNext()) {
                String currentPassword = generator.getNext();
                md.update(currentPassword.getBytes());
                String hash = bytesToHashString(md.digest());
                if (usersMap.get(hash) != null) {
                    currentPasswords.put(usersMap.get(hash), currentPassword);
                }
            }
            return currentPasswords;
        };
    }

    private static String bytesToHashString(byte[] bytes) {
        BigInteger bigInteger = new BigInteger(1, bytes);
        String bytesString = bigInteger.toString(HASH_RADIX);
        StringBuilder hashString = new StringBuilder();
        hashString.repeat("0", HASH_SIZE - bytesString.length());
        hashString.append(bytesString);
        return hashString.toString();
    }
}
