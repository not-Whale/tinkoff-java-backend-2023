package edu.homework8.brute_force.brute_forcer;

import edu.homework8.brute_force.generators.Generator;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleBruteForcer {
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

    public SimpleBruteForcer(Map<String, String> usersMap, int passwordSize) {
        this.passwordSize = passwordSize;
        this.usersMap = usersMap;
    }

    public Map<String, String> decode() {
        try {
            Map<String, String> passwords = new HashMap<>();
            for (int i = 1; i <= passwordSize; i++) {
                System.out.println("i = " + i);
                Generator generator = new Generator(alphabet, i);
                MessageDigest md = MessageDigest.getInstance("MD5");
                while (generator.hasNext()) {
                    String currentPassword = generator.getNext();
                    md.update(currentPassword.getBytes());
                    String hash = bytesToHashString(md.digest());
                    if (usersMap.get(hash) != null) {
                        passwords.put(usersMap.get(hash), currentPassword);
                    }
                }
            }
            return passwords;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private String bytesToHashString(byte[] bytes) {
        BigInteger bigInteger = new BigInteger(1, bytes);
        String bytesString = bigInteger.toString(HASH_RADIX);
        StringBuilder hashString = new StringBuilder();
        hashString.repeat("0", HASH_SIZE - bytesString.length());
        hashString.append(bytesString);
        return hashString.toString();
    }
}
