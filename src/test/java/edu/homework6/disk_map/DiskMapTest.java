package edu.homework6.disk_map;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Ассоциативный массив, хранящий пары ключ-значение на жестком диске.")
public class DiskMapTest {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final String DISK_MAP_DIRECTORY = "src/main/resources/disk_maps";

    private static final String MAP_FILE_PREFIX = "map_";

    @Test
    @DisplayName("Инициализация DiskMap.")
    void initDiskMap() {
        // given
        File mapDir = Paths.get(DISK_MAP_DIRECTORY, MAP_FILE_PREFIX.concat("0")).toFile();
        try (DiskMap diskMap = new DiskMap()) {
            // when
            int size = diskMap.size();
            boolean empty = diskMap.isEmpty();

            // then
            assertThat(size).isEqualTo(0);
            assertThat(empty).isTrue();
            assertThat(mapDir).isNotNull();
            assertThat(mapDir.exists()).isTrue();
            assertThat(mapDir.list()).isNotNull().isEmpty();
        } catch (IOException e) {
            LOGGER.info("Упс... Что-то пошло не так! " + e);
        }
    }

    @Test
    @DisplayName("Добавление пары.")
    void diskMapPut() {
        // given
        File mapDir = Paths.get(DISK_MAP_DIRECTORY, MAP_FILE_PREFIX.concat("0")).toFile();
        File keyFile = Paths.get(DISK_MAP_DIRECTORY, MAP_FILE_PREFIX.concat("0"), "key1.txt").toFile();
        try (DiskMap diskMap = new DiskMap()) {
            // when
            String oldValue = diskMap.put("key1", "value1");
            int size = diskMap.size();
            boolean empty = diskMap.isEmpty();
            FileReader fileReader = new FileReader(keyFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String value = bufferedReader.readLine();
            bufferedReader.close();
            fileReader.close();

            // then
            assertThat(size).isEqualTo(1);
            assertThat(empty).isFalse();
            assertThat(mapDir.exists()).isTrue();
            assertThat(keyFile.exists()).isTrue();
            assertThat(value).isEqualTo("value1");
            assertThat(oldValue).isNull();
        } catch (IOException e) {
            LOGGER.info("Упс... Что-то пошло не так! " + e);
        }
    }

    @Test
    @DisplayName("Обновление пары.")
    void diskMapPutUpdate() {
        // given
        File mapDir = Paths.get(DISK_MAP_DIRECTORY, MAP_FILE_PREFIX.concat("0")).toFile();
        File keyFile = Paths.get(DISK_MAP_DIRECTORY, MAP_FILE_PREFIX.concat("0"), "key1.txt").toFile();
        try (DiskMap diskMap = new DiskMap()) {
            // when
            diskMap.put("key1", "value1");
            String oldValue = diskMap.put("key1", "value2");
            int size = diskMap.size();
            boolean empty = diskMap.isEmpty();
            FileReader fileReader = new FileReader(keyFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String value = bufferedReader.readLine();
            bufferedReader.close();
            fileReader.close();

            // then
            assertThat(size).isEqualTo(1);
            assertThat(empty).isFalse();
            assertThat(mapDir.exists()).isTrue();
            assertThat(keyFile.exists()).isTrue();
            assertThat(value).isEqualTo("value2");
            assertThat(oldValue).isNotNull().isEqualTo("value1");
        } catch (IOException e) {
            LOGGER.info("Упс... Что-то пошло не так! " + e);
        }
    }

    @Test
    @DisplayName("Слияние с другой Map.")
    void diskMapPutAll() {
        // given
        try (DiskMap diskMap = new DiskMap(); DiskMap appendMap = new DiskMap()) {
            // when
            appendMap.put("k1", "v1");
            appendMap.put("k2", "v2");
            diskMap.putAll(appendMap);
            boolean containsKey1 = diskMap.containsKey("k1");
            boolean containsKey2 = diskMap.containsKey("k2");
            boolean containsValue1 = diskMap.containsValue("v1");
            boolean containsValue2 = diskMap.containsValue("v2");
            int size = diskMap.size();

            // then
            assertThat(size).isEqualTo(2);
            assertThat(containsKey1).isTrue();
            assertThat(containsValue1).isTrue();
            assertThat(containsKey2).isTrue();
            assertThat(containsValue2).isTrue();
        } catch (IOException e) {
            LOGGER.info("Упс... Что-то пошло не так! " + e);
        }
    }

    @Test
    @DisplayName("Поиск включения ключа пары. Ключ содержится.")
    void diskMapContainsKey() {
        // given
        try (DiskMap diskMap = new DiskMap()) {
            // when
            diskMap.put("k1", "v1");
            diskMap.put("k2", "v2");
            boolean contains = diskMap.containsKey("k2");

            // then
            assertThat(contains).isTrue();
        } catch (IOException e) {
            LOGGER.info("Упс... Что-то пошло не так! " + e);
        }
    }

    @Test
    @DisplayName("Поиск включения ключа пары. Ключ не содержится.")
    void diskMapNotContainsKey() {
        // given
        try (DiskMap diskMap = new DiskMap()) {
            // when
            diskMap.put("k1", "v1");
            diskMap.put("k2", "v2");
            boolean contains = diskMap.containsKey("k3");

            // then
            assertThat(contains).isFalse();
        } catch (IOException e) {
            LOGGER.info("Упс... Что-то пошло не так! " + e);
        }
    }

    @Test
    @DisplayName("Поиск включения значения пары. Значение содержится.")
    void diskMapContainsValue() {
        // given
        try (DiskMap diskMap = new DiskMap()) {
            // when
            diskMap.put("k1", "v1");
            diskMap.put("k2", "v2");
            boolean contains = diskMap.containsValue("v1");

            // then
            assertThat(contains).isTrue();
        } catch (IOException e) {
            LOGGER.info("Упс... Что-то пошло не так! " + e);
        }
    }

    @Test
    @DisplayName("Поиск включения значения пары. Значение не содержится.")
    void diskMapNotContainsValue() {
        // given
        try (DiskMap diskMap = new DiskMap()) {
            // when
            diskMap.put("k1", "v1");
            diskMap.put("k2", "v2");
            boolean contains = diskMap.containsValue("v0");

            // then
            assertThat(contains).isFalse();
        } catch (IOException e) {
            LOGGER.info("Упс... Что-то пошло не так! " + e);
        }
    }

    @Test
    @DisplayName("Получение значения по ключу.")
    void diskMapGet() {
        // given
        try (DiskMap diskMap = new DiskMap()) {
            // when
            diskMap.put("k1", "v1");
            diskMap.put("k2", "v2");
            String value = diskMap.get("k1");

            // then
            assertThat(value).isNotNull().isEqualTo("v1");
        } catch (IOException e) {
            LOGGER.info("Упс... Что-то пошло не так! " + e);
        }
    }

    @Test
    @DisplayName("Получение значения по ключу. Ключ не существует.")
    void diskMapGetNoSuchKey() {
        // given
        try (DiskMap diskMap = new DiskMap()) {
            // when
            diskMap.put("k1", "v1");
            diskMap.put("k2", "v2");
            String value = diskMap.get("k3");

            // then
            assertThat(value).isNull();
        } catch (IOException e) {
            LOGGER.info("Упс... Что-то пошло не так! " + e);
        }
    }

    @Test
    @DisplayName("Получение набора ключей.")
    void diskMapKeySet() {
        // given
        try (DiskMap diskMap = new DiskMap()) {
            // when
            diskMap.put("k1", "v1");
            diskMap.put("k2", "v2");
            Set<String> keySet = diskMap.keySet();

            // then
            assertThat(keySet).isNotNull().containsExactly("k1", "k2");
        } catch (IOException e) {
            LOGGER.info("Упс... Что-то пошло не так! " + e);
        }
    }

    @Test
    @DisplayName("Получение набора значений.")
    void diskMapValueSet() {
        // given
        try (DiskMap diskMap = new DiskMap()) {
            // when
            diskMap.put("k1", "v1");
            diskMap.put("k2", "v2");
            Collection<String> valueSet = diskMap.values();

            // then
            assertThat(valueSet).isNotNull().containsExactly("v1", "v2");
        } catch (IOException e) {
            LOGGER.info("Упс... Что-то пошло не так! " + e);
        }
    }

    @Test
    @DisplayName("Получение набора пар.")
    void diskMapEntrySet() {
        // given
        try (DiskMap diskMap = new DiskMap()) {
            // when
            diskMap.put("k1", "v1");
            diskMap.put("k2", "v2");
            Set<Map.Entry<String, String>> entrySet = diskMap.entrySet();

            // then
            assertThat(entrySet).isNotNull().containsExactly(
                Map.entry("k2", "v2"),
                Map.entry("k1", "v1")
            );
        } catch (IOException e) {
            LOGGER.info("Упс... Что-то пошло не так! " + e);
        }
    }

    @Test
    @DisplayName("Удаление пар.")
    void diskMapRemove() {
        // given
        File mapsDir = Paths.get(DISK_MAP_DIRECTORY, MAP_FILE_PREFIX.concat("0")).toFile();
        try (DiskMap diskMap = new DiskMap()) {
            // when
            diskMap.put("k1", "v1");
            diskMap.put("k2", "v2");
            String oldValue = diskMap.remove("k1");
            int size = diskMap.size();
            boolean containsKey = diskMap.containsKey("k1");
            boolean containsValue = diskMap.containsValue("v1");

            // then
            assertThat(size).isEqualTo(1);
            assertThat(containsKey).isFalse();
            assertThat(containsValue).isFalse();
            assertThat(mapsDir).isNotNull();
            assertThat(mapsDir.exists()).isTrue();
            assertThat(mapsDir.list()).isNotNull().isNotEmpty();
            assertThat(mapsDir.list().length).isEqualTo(1);
            assertThat(oldValue).isNotNull().isEqualTo("v1");
        } catch (IOException e) {
            LOGGER.info("Упс... Что-то пошло не так! " + e);
        }
    }

    @Test
    @DisplayName("Удаление пар. Пара не существует.")
    void diskMapRemoveNotExists() {
        // given
        File mapsDir = Paths.get(DISK_MAP_DIRECTORY, MAP_FILE_PREFIX.concat("0")).toFile();
        try (DiskMap diskMap = new DiskMap()) {
            // when
            diskMap.put("k1", "v1");
            diskMap.put("k2", "v2");
            String oldValue = diskMap.remove("k3");
            int size = diskMap.size();
            boolean containsKey = diskMap.containsKey("k3");

            // then
            assertThat(size).isEqualTo(2);
            assertThat(containsKey).isFalse();
            assertThat(mapsDir).isNotNull();
            assertThat(mapsDir.exists()).isTrue();
            assertThat(mapsDir.list()).isNotNull().isNotEmpty();
            assertThat(mapsDir.list().length).isEqualTo(2);
            assertThat(oldValue).isNull();
        } catch (IOException e) {
            LOGGER.info("Упс... Что-то пошло не так! " + e);
        }
    }

    @Test
    @DisplayName("Очистка.")
    void diskMapClear() {
        // given
        try (DiskMap diskMap = new DiskMap()) {
            // when
            diskMap.put("k1", "v1");
            diskMap.put("k2", "v2");
            diskMap.clear();
            int size = diskMap.size();
            boolean empty = diskMap.isEmpty();
            boolean containsKey1 = diskMap.containsKey("k1");
            boolean containsKey2 = diskMap.containsKey("k2");
            boolean containsValue1 = diskMap.containsValue("v1");
            boolean containsValue2 = diskMap.containsValue("v2");

            // then
            assertThat(size).isEqualTo(0);
            assertThat(empty).isTrue();
            assertThat(containsKey1).isFalse();
            assertThat(containsKey2).isFalse();
            assertThat(containsValue1).isFalse();
            assertThat(containsValue2).isFalse();
        } catch (IOException e) {
            LOGGER.info("Упс... Что-то пошло не так! " + e);
        }
    }

    @Test
    @DisplayName("Удаление файлов и директории при закрытии.")
    void closeDiskMap() {
        // given
        File mapsDir = Paths.get(DISK_MAP_DIRECTORY).toFile();
        DiskMap diskMap = new DiskMap();
        try {
            // when
            diskMap.put("k1", "v1");
            diskMap.put("k2", "v2");
            diskMap.close();

            // then
            assertThat(mapsDir).isNotNull();
            assertThat(mapsDir.exists()).isTrue();
            assertThat(mapsDir.list()).isNotNull().isEmpty();
        } catch (IOException e) {
            LOGGER.info("Упс... Что-то пошло не так! " + e);
        }
    }
}
