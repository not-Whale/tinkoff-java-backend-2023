package edu.homework6.disk_map;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DiskMap implements Map<String, String>, Closeable {
    private static final String DISK_MAP_DIRECTORY = "src/main/resources/disk_maps";

    private static final String MAP_FILE_PREFIX = "map_";

    private static int count = 0;

    private int size;

    private final String mapName;

    public DiskMap() {
        this.mapName = MAP_FILE_PREFIX + count;
        count++;
        this.size = 0;
        this.initDiskMapFile();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        if (!(key instanceof String)) {
            throw new IllegalArgumentException("Ключ должен иметь тип String!");
        }
        Path keyPath = getKeyPath(((String) key));
        return Files.exists(keyPath);
    }

    @Override
    public boolean containsValue(Object value) {
        return values().contains(value);
    }

    @Override
    public String get(Object key) {
        if (!containsKey(key)) {
            return null;
        }
        File keyFile = getKeyPath((String) key).toFile();
        return readValue(keyFile);
    }

    @Nullable
    @Override
    public String put(String key, String value) {
        Path keyPath = getKeyPath(key);
        File keyFile = keyPath.toFile();
        String oldValue = null;
        if (!Files.exists(keyPath)) {
            try {
                Files.createFile(keyPath);
                size++;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            oldValue = readValue(keyFile);
        }
        writeValue(keyFile, value);
        return oldValue;
    }

    @Override
    public String remove(Object key) {
        if (!containsKey(key)) {
            return null;
        }
        Path keyPath = getKeyPath(((String) key));
        File keyFile = keyPath.toFile();
        try {
            String oldValue = readValue(keyFile);
            Files.delete(keyPath);
            size--;
            return oldValue;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void putAll(@NotNull Map<? extends String, ? extends String> m) {
        for (var entry : m.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void clear() {
        Set<String> keySet = keySet();
        for (String key : keySet) {
            remove(key);
        }
    }

    @NotNull
    @Override
    public Set<String> keySet() {
        File mapFile = getMapPath().toFile();
        String[] keyFiles = mapFile.list();
        Set<String> keySet = new HashSet<>();
        if (keyFiles != null) {
            for (String keyFileName : keyFiles) {
                String key = keyFileName.substring(0, keyFileName.lastIndexOf("."));
                keySet.add(key);
            }
        }
        return keySet;
    }

    @NotNull
    @Override
    public Collection<String> values() {
        File mapFile = getMapPath().toFile();
        String[] keyFiles = mapFile.list();
        Set<String> valueSet = new HashSet<>();
        if (keyFiles != null) {
            for (String keyFileName : keyFiles) {
                String key = keyFileName.substring(0, keyFileName.lastIndexOf("."));
                valueSet.add(get(key));
            }
        }
        return valueSet;
    }

    @NotNull
    @Override
    public Set<Entry<String, String>> entrySet() {
        File mapFile = getMapPath().toFile();
        String[] keyFiles = mapFile.list();
        Set<Map.Entry<String, String>> entrySet = new HashSet<>();
        if (keyFiles != null) {
            for (String keyFileName : keyFiles) {
                String key = keyFileName.substring(0, keyFileName.lastIndexOf("."));
                entrySet.add(Map.entry(key, get(key)));
            }
        }
        return entrySet;
    }

    @Override
    public void close() throws IOException {
        clear();
        Path path = Paths.get(DISK_MAP_DIRECTORY, mapName);
        Files.delete(path);
        count--;
    }

    private Path getKeyPath(String key) {
        return Paths.get(DISK_MAP_DIRECTORY, this.mapName, key.concat(".txt"));
    }

    private Path getMapPath() {
        return Paths.get(DISK_MAP_DIRECTORY, this.mapName);
    }

    private void initDiskMapFile() {
        Path path = Paths.get(DISK_MAP_DIRECTORY, mapName);
        if (Files.exists(path)) {
            throw new RuntimeException("DiskMap " + mapName + " уже существует!");
        }
        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String readValue(File keyFile) {
        try (FileReader fileReader = new FileReader(keyFile);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            return bufferedReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeValue(File keyFile, String value) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(keyFile, false)) {
            fileOutputStream.write(value.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
