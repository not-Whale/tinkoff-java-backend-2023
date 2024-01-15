package edu.homework9;

import edu.homework9.tree_analytics.FilesPredicates;
import edu.homework9.tree_analytics.TreeAnalytics;
import java.nio.file.Path;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.homework9.tree_analytics.FilesPredicates.hasExtension;
import static edu.homework9.tree_analytics.FilesPredicates.hasSize;
import static edu.homework9.tree_analytics.FilesPredicates.largerThan;
import static edu.homework9.tree_analytics.FilesPredicates.smallerThan;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Параллельная функция обработки дерева на примере файловой системы.")
public class TreeAnalyticsTest {
    @Test
    @DisplayName("Поиск директорий, в которых больше, чем N, вложенных файлов. Подходят все директории.")
    void findDirsWithMoreThanNNestedFilesAllDirs() {
        // given
        Path rootDirectory = Path.of("src", "main", "java", "edu", "homework9");
        TreeAnalytics treeAnalytics = new TreeAnalytics(rootDirectory);

        // when
        Path[] dirs = treeAnalytics.findDirsWithMoreThanNNestedFiles(0);

        // then
        assertThat(dirs).isNotNull().isNotEmpty();
        assertThat(dirs).containsOnly(
            rootDirectory,
            rootDirectory.resolve("stats_collector"),
            rootDirectory.resolve("tree_analytics")
        );
    }

    @Test
    @DisplayName("Поиск директорий, в которых больше, чем N, вложенных файлов. Подходят некоторые директории")
    void findDirsWithMoreThanNNestedFiles() {
        // given
        Path rootDirectory = Path.of("src", "main", "java", "edu", "homework9");
        TreeAnalytics treeAnalytics = new TreeAnalytics(rootDirectory);

        // when
        Path[] dirs = treeAnalytics.findDirsWithMoreThanNNestedFiles(3);

        // then
        assertThat(dirs).isNotNull().isNotEmpty();
        assertThat(dirs).containsOnly(
            rootDirectory,
            rootDirectory.resolve("tree_analytics")
        );
    }

    @Test
    @DisplayName("Поисков файлов с расширением \".xml\".")
    void findXmlFiles() {
        // given
        Path rootDirectory = Path.of("src");
        TreeAnalytics treeAnalytics = new TreeAnalytics(rootDirectory);

        // when
        Path[] files = treeAnalytics.findFilesByPredicate(hasExtension("xml"));

        // then
        assertThat(files).isNotNull().isNotEmpty();
        assertThat(files).containsOnly(Path.of("src", "main", "resources", "log4j2.xml"));
    }

    @Test
    @DisplayName("Поиск файлов с расширением \".java\".")
    void findJavaFiles() {
        // given
        Path rootDirectory = Path.of("src", "main", "java", "edu", "homework2");
        TreeAnalytics treeAnalytics = new TreeAnalytics(rootDirectory);

        // when
        Path[] files = treeAnalytics.findFilesByPredicate(FilesPredicates.hasExtension("java"));

        // then
        assertThat(files).isNotNull().isNotEmpty();
        assertThat(files.length).isEqualTo(13);
    }

    @Test
    @DisplayName("Поиск файлов с размером больше 1500 байт.")
    void findFilesLargerThan() {
        // given
        Path rootDirectory = Path.of("src", "main", "java", "edu", "homework9");
        TreeAnalytics treeAnalytics = new TreeAnalytics(rootDirectory);

        // when
        Path[] files = treeAnalytics.findFilesByPredicate(largerThan(1_500));

        // then
        assertThat(files).isNotNull().isNotEmpty();
        assertThat(files).containsOnly(
            rootDirectory.resolve("stats_collector").resolve("StatsCollector.java"),
            rootDirectory.resolve("tree_analytics").resolve("DirectoryFilesCounter.java"),
            rootDirectory.resolve("tree_analytics").resolve("FilesFilter.java")
        );
    }

    @Test
    @DisplayName("Поиск файлов с размером меньше 1000 байт и больше 100 байт.")
    void findFilesSmallerThan() {
        // given
        Path rootDirectory = Path.of("src", "main", "java", "edu", "homework9");
        TreeAnalytics treeAnalytics = new TreeAnalytics(rootDirectory);

        // when
        Path[] files = treeAnalytics.findFilesByPredicate(smallerThan(1000).and(largerThan(100)));

        // then
        assertThat(files).isNotNull().isNotEmpty();
        assertThat(files).containsOnly(
            rootDirectory.resolve("stats_collector").resolve("Metric.java"),
            rootDirectory.resolve("tree_analytics").resolve("DirectoryState.java"),
            rootDirectory.resolve("tree_analytics").resolve("TreeAnalytics.java")
        );
    }

    @Test
    @DisplayName("Поиск файлов с размером равным 86 байтам.")
    void findFilesHasSize() {
        // given
        Path rootDirectory = Path.of("src", "main", "java", "edu", "homework9");
        TreeAnalytics treeAnalytics = new TreeAnalytics(rootDirectory);

        // when
        Path[] files = treeAnalytics.findFilesByPredicate(hasSize(86));

        // then
        assertThat(files).isNotNull().isNotEmpty();
        assertThat(files).containsOnly(
            rootDirectory.resolve("stats_collector").resolve("MetricType.java")
        );
    }
}
