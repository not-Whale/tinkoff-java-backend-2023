package edu.project3;

import edu.project3.arguments_parser.CommandLineArgumentParser;
import edu.project3.formatters.FormatType;
import edu.project3.formatters.FormatUtils;
import edu.project3.logs.Log;
import edu.project3.logs.LogParser;
import edu.project3.logs.RequestType;
import edu.project3.readers.LogReader;
import edu.project3.reporters.GeneralInfo;
import edu.project3.reporters.LogReporter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("MultipleStringLiterals")
public class Main {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final String NEW_LINE_WITHOUT_HIGHLIGHT = "\033[0m\n";

    private Main() {}

    public static void main(String[] args) {
        // arguments reader
        CommandLineArgumentParser commandLineArgumentParser = CommandLineArgumentParser.with(args);
        // get arguments
        String file = commandLineArgumentParser.sourcePath();
        LocalDateTime from = commandLineArgumentParser.from();
        LocalDateTime to = commandLineArgumentParser.to();
        FormatType formatType = commandLineArgumentParser.formatType();

        // get logs in strings
        String[] logsStrings = LogReader.read(file);

        // for each log convert string to object
        List<Log> logsList = new ArrayList<>();
        for (String logString : logsStrings) {
            logsList.add(LogParser.parse(logString));
        }

        // get reports
        LogReporter logReporter = new LogReporter(logsList.toArray(Log[]::new), file, from, to);
        var generalInfo = logReporter.getGeneralInfoReport();
        var mostPopularResources = logReporter.getMostPopularResources();
        var mostPopularResponseCodes = logReporter.getMostPopularResponseCodes();
        var mostPopularRequestTypes = logReporter.getMostPopularRequestTypes();
        var getRequestsPerResource = logReporter.getRequestsPerResourceByType(RequestType.GET);
        var headRequestsPerResource = logReporter.getRequestsPerResourceByType(RequestType.HEAD);
        var postRequestsPerResource = logReporter.getRequestsPerResourceByType(RequestType.POST);
        var putRequestsPerResource = logReporter.getRequestsPerResourceByType(RequestType.PUT);
        var deleteRequestsPerResource = logReporter.getRequestsPerResourceByType(RequestType.DELETE);
        var connectRequestsPerResource = logReporter.getRequestsPerResourceByType(RequestType.CONNECT);
        var traceRequestsPerResource = logReporter.getRequestsPerResourceByType(RequestType.TRACE);
        var patchRequestsPerResource = logReporter.getRequestsPerResourceByType(RequestType.PATCH);
        var mostStableResources = logReporter.getMostStableResources();

        // print reports
        printGeneralInfo(generalInfo, formatType);
        printMostPopularResources(mostPopularResources, formatType);
        printMostPopularResponseCodes(mostPopularResponseCodes, formatType);
        printMostPopularRequestTypes(mostPopularRequestTypes, formatType);
        printRequestsPerResourcesByType(getRequestsPerResource, formatType, RequestType.GET);
        printRequestsPerResourcesByType(headRequestsPerResource, formatType, RequestType.HEAD);
        printRequestsPerResourcesByType(postRequestsPerResource, formatType, RequestType.POST);
        printRequestsPerResourcesByType(putRequestsPerResource, formatType, RequestType.PUT);
        printRequestsPerResourcesByType(deleteRequestsPerResource, formatType, RequestType.DELETE);
        printRequestsPerResourcesByType(connectRequestsPerResource, formatType, RequestType.CONNECT);
        printRequestsPerResourcesByType(traceRequestsPerResource, formatType, RequestType.TRACE);
        printRequestsPerResourcesByType(patchRequestsPerResource, formatType, RequestType.PATCH);
        printMostStableResources(mostStableResources, formatType);
    }

    private static void printGeneralInfo(GeneralInfo generalInfo, FormatType formatType) {
        List<String> names = new ArrayList<>();
        List<String> values = new ArrayList<>();
        names.add("Ресурс");
        values.add(generalInfo.resource());
        names.add("Начальная дата");
        values.add(generalInfo.from());
        names.add("Конечная дата");
        values.add(generalInfo.to());
        names.add("Количество запросов");
        values.add(generalInfo.requests().toString());
        names.add("Средний размер ответа");
        values.add(generalInfo.averageResponseSize().toString());
        String formatted = getFormatted(
            names.toArray(String[]::new),
            values.toArray(String[]::new),
            "Метрика",
            "Значение",
            formatType
        );
        LOGGER.info(NEW_LINE_WITHOUT_HIGHLIGHT + "Общая информация\n\n" + formatted);
    }

    private static void printMostStableResources(
        List<Map.Entry<String, Double>> mostStableResources, FormatType formatType) {
        if (mostStableResources.isEmpty()) {
            return;
        }
        List<String> names = new ArrayList<>();
        List<String> values = new ArrayList<>();
        for (var entry : mostStableResources) {
            names.add(entry.getKey());
            values.add(String.format("%.1f", entry.getValue()) + "%");
        }
        String formatted = getFormatted(
            names.toArray(String[]::new),
            values.toArray(String[]::new),
            "Ресурс",
            "Процент успешных запросов",
            formatType
        );
        LOGGER.info(NEW_LINE_WITHOUT_HIGHLIGHT + "Ресурсы с наибольшим процентом успешных запросов\n\n" + formatted);
    }

    private static void printRequestsPerResourcesByType(
        List<Map.Entry<String, Long>> requestsPerResourcesByType, FormatType formatType, RequestType requestType) {
        if (requestsPerResourcesByType.isEmpty()) {
            return;
        }
        List<String> names = new ArrayList<>();
        List<String> values = new ArrayList<>();
        for (var entry : requestsPerResourcesByType) {
            names.add(entry.getKey());
            values.add(entry.getValue().toString());
        }
        String formatted = getFormatted(
            names.toArray(String[]::new),
            values.toArray(String[]::new),
            "Ресурс",
            "Количество " + requestType.verbal() + " запросов",
            formatType
        );
        LOGGER.info(NEW_LINE_WITHOUT_HIGHLIGHT
            + "Ресурсы с наибольшим количеством "
            + requestType.verbal()
            + " запросов к ним\n\n"
            + formatted
        );
    }

    private static void printMostPopularRequestTypes(
        List<Map.Entry<RequestType, Long>> mostPopularRequestTypes, FormatType formatType) {
        if (mostPopularRequestTypes.isEmpty()) {
            return;
        }
        List<String> names = new ArrayList<>();
        List<String> values = new ArrayList<>();
        for (var entry : mostPopularRequestTypes) {
            names.add(entry.getKey().verbal());
            values.add(entry.getValue().toString());
        }
        String formatted = getFormatted(
            names.toArray(String[]::new),
            values.toArray(String[]::new),
            "Тип запроса",
            "Количество запросов",
            formatType
        );
        LOGGER.info(NEW_LINE_WITHOUT_HIGHLIGHT + "Наиболее частые запросы\n\n" + formatted);
    }

    private static void printMostPopularResponseCodes(
        List<Map.Entry<Integer, Long>> mostPopularResponseCodes, FormatType formatType) {
        if (mostPopularResponseCodes.isEmpty()) {
            return;
        }
        List<String> names = new ArrayList<>();
        List<String> values = new ArrayList<>();
        for (var entry : mostPopularResponseCodes) {
            names.add(entry.getKey().toString());
            values.add(entry.getValue().toString());
        }
        String formatted = getFormatted(
            names.toArray(String[]::new),
            values.toArray(String[]::new),
            "Код ответа",
            "Количество запросов",
            formatType
        );
        LOGGER.info(NEW_LINE_WITHOUT_HIGHLIGHT + "Наиболее частые коды ответа\n\n" + formatted);
    }

    private static void printMostPopularResources(
        List<Map.Entry<String, Long>> mostPopularResources, FormatType formatType) {
        if (mostPopularResources.isEmpty()) {
            return;
        }
        List<String> names = new ArrayList<>();
        List<String> values = new ArrayList<>();
        for (var entry : mostPopularResources) {
            names.add(entry.getKey());
            values.add(entry.getValue().toString());
        }
        String formatted = getFormatted(
            names.toArray(String[]::new),
            values.toArray(String[]::new),
            "Ресурс",
            "Количество запросов",
            formatType
        );
        LOGGER.info(NEW_LINE_WITHOUT_HIGHLIGHT + "Самые запрашиваемые ресурсы\n\n" + formatted);
    }

    private static String getFormatted(
        String[] names, String[] values, String statisticsName, String valuesName, FormatType formatType) {
        return switch (formatType) {
            case ADOC -> FormatUtils.reportToAdoc(
                names,
                values,
                statisticsName,
                valuesName
            );
            case MARKDOWN -> FormatUtils.reportToMarkdown(
                names,
                values,
                statisticsName,
                valuesName
            );
            default -> FormatUtils.reportToString(
                names,
                values,
                statisticsName,
                valuesName
            );
        };
    }
}
