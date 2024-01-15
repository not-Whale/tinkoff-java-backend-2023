package edu.project3;

import edu.project3.arguments_parser.CommandLineArgumentParser;
import edu.project3.formatters.FormatType;
import edu.project3.formatters.FormatUtils;
import edu.project3.logs.Log;
import edu.project3.logs.LogParser;
import edu.project3.logs.RequestType;
import edu.project3.readers.LogReader;
import edu.project3.readers.Reader;
import edu.project3.reporters.GeneralInfo;
import edu.project3.reporters.LogReporter;
import edu.project3.writers.ReportFileWriter;
import edu.project3.writers.ReportSoutWriter;
import edu.project3.writers.ReportWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String[] GENERAL_INFO_NAMES = new String[] {
        "Имя ресурса",
        "Начальная дата",
        "Конечная дата",
        "Запросов отправлено",
        "Средний размер ответа"
    };

    private static final String EMPTY_REPORT = "-\n\n";

    private static final String GENERAL_INFO_STATISTICS_NAME = "Метрика";

    private static final String RESOURCE_STATISTICS_NAME = "Ресурс";

    private static final String REQUEST_TYPE_STATISTICS_NAME = "Тип запроса";

    private static final String RESPONSE_CODE_STATISTICS_NAME = "Код ответа";

    private static final String GENERAL_INFO_VALUES_NAME = "Значение";

    private static final String SUCCESS_PERCENTAGE_VALUES_NAME = "Успешных запросов, %";

    private static final String REQUESTS_NUMBER_VALUES_NAME = "Количество запросов";

    private static final String MOST_STABLE_RESOURCES_TITLE = "Наиболее стабильные ресурсы.\n\n";

    private static final String MOST_POPULAR_REQUEST_TYPES_TITLE = "Наиболее частые запросы.\n\n";

    private static final String MOST_POPULAR_RESPONSE_CODES_TITLE = "Наиболее частые коды ответа.\n\n";

    private static final String MOST_POPULAR_RESOURCES_TITLE = "Самые запрашиваемые ресурсы.\n\n";

    private Main() {}

    public static void main(String[] args) {
        CommandLineArgumentParser commandLineArgumentParser = CommandLineArgumentParser.with(args);
        String sourcePath = commandLineArgumentParser.sourcePath();
        LocalDateTime from = commandLineArgumentParser.from();
        LocalDateTime to = commandLineArgumentParser.to();
        FormatType formatType = commandLineArgumentParser.formatType();
        String savePath = commandLineArgumentParser.savePath();

        Log[] logs = getLogs(sourcePath);
        LogReporter logReporter = LogReporter.of(logs, sourcePath, from, to);

        ReportWriter reportWriter = savePath == null ? new ReportSoutWriter() : new ReportFileWriter(savePath);
        writeReports(reportWriter, logReporter, formatType);
    }

    private static void writeReports(ReportWriter reportWriter, LogReporter logReporter, FormatType formatType) {
        reportWriter.write(getGeneralInfoReport(logReporter.generalInfo(), formatType));
        reportWriter.write(getMostPopularResourcesReport(logReporter.mostPopularResources(), formatType));
        reportWriter.write(getMostPopularResponseCodesReport(logReporter.mostPopularResponseCodes(), formatType));
        reportWriter.write(getMostPopularRequestTypesReport(logReporter.mostPopularRequestTypes(), formatType));
        for (RequestType requestType : RequestType.values()) {
            reportWriter.write(getRequestsPerResourcesByTypeReport(
                logReporter.requestsPerResourceByType(requestType),
                formatType,
                requestType)
            );
        }
        reportWriter.write(getMostStableResourcesReport(logReporter.mostStableResources(), formatType));
    }

    private static Log[] getLogs(String sourcePath) {
        Reader logReader = LogReader.from(sourcePath);
        String[] logsStrings = logReader.read();
        List<Log> logs = new ArrayList<>();
        for (String logString : logsStrings) {
            logs.add(LogParser.parse(logString));
        }
        return logs.toArray(Log[]::new);
    }

    private static String getGeneralInfoReport(GeneralInfo generalInfo, FormatType formatType) {
        String[] values = new String[] {
            generalInfo.resource(),
            generalInfo.from(),
            generalInfo.to(),
            generalInfo.requests().toString(),
            generalInfo.averageResponseSize().toString()
        };
        return getFormatted(
            GENERAL_INFO_NAMES,
            values,
            GENERAL_INFO_STATISTICS_NAME,
            GENERAL_INFO_VALUES_NAME,
            formatType
        );
    }

    private static String getMostStableResourcesReport(
        List<Map.Entry<String, Double>> mostStableResources, FormatType formatType) {
        if (mostStableResources.isEmpty()) {
            return MOST_STABLE_RESOURCES_TITLE + EMPTY_REPORT;
        }
        List<String> names = new ArrayList<>();
        List<String> values = new ArrayList<>();
        for (var entry : mostStableResources) {
            names.add(entry.getKey());
            values.add(String.format("%.1f", entry.getValue()));
        }
        return MOST_STABLE_RESOURCES_TITLE + getFormatted(
            names.toArray(String[]::new),
            values.toArray(String[]::new),
            RESOURCE_STATISTICS_NAME,
            SUCCESS_PERCENTAGE_VALUES_NAME,
            formatType
        );
    }

    private static String getRequestsPerResourcesByTypeReport(
        List<Map.Entry<String, Long>> requestsPerResourcesByType, FormatType formatType, RequestType requestType) {
        if (requestsPerResourcesByType.isEmpty()) {
            return getRequestPerResourceByTypeTitle(requestType) + EMPTY_REPORT;
        }
        List<String> names = new ArrayList<>();
        List<String> values = new ArrayList<>();
        for (var entry : requestsPerResourcesByType) {
            names.add(entry.getKey());
            values.add(entry.getValue().toString());
        }
        return getRequestPerResourceByTypeTitle(requestType) + getFormatted(
            names.toArray(String[]::new),
            values.toArray(String[]::new),
            RESOURCE_STATISTICS_NAME,
            getRequestPerResourceByTypeValuesName(requestType),
            formatType
        );
    }

    private static String getMostPopularRequestTypesReport(
        List<Map.Entry<RequestType, Long>> mostPopularRequestTypes, FormatType formatType) {
        if (mostPopularRequestTypes.isEmpty()) {
            return MOST_POPULAR_REQUEST_TYPES_TITLE + EMPTY_REPORT;
        }
        List<String> names = new ArrayList<>();
        List<String> values = new ArrayList<>();
        for (var entry : mostPopularRequestTypes) {
            names.add(entry.getKey().verbal());
            values.add(entry.getValue().toString());
        }
        return MOST_POPULAR_REQUEST_TYPES_TITLE + getFormatted(
            names.toArray(String[]::new),
            values.toArray(String[]::new),
            REQUEST_TYPE_STATISTICS_NAME,
            REQUESTS_NUMBER_VALUES_NAME,
            formatType
        );
    }

    private static String getMostPopularResponseCodesReport(
        List<Map.Entry<Integer, Long>> mostPopularResponseCodes, FormatType formatType) {
        if (mostPopularResponseCodes.isEmpty()) {
            return MOST_POPULAR_RESPONSE_CODES_TITLE + EMPTY_REPORT;
        }
        List<String> names = new ArrayList<>();
        List<String> values = new ArrayList<>();
        for (var entry : mostPopularResponseCodes) {
            names.add(entry.getKey().toString());
            values.add(entry.getValue().toString());
        }
        return MOST_POPULAR_RESPONSE_CODES_TITLE + getFormatted(
            names.toArray(String[]::new),
            values.toArray(String[]::new),
            RESPONSE_CODE_STATISTICS_NAME,
            REQUESTS_NUMBER_VALUES_NAME,
            formatType
        );
    }

    private static String getMostPopularResourcesReport(
        List<Map.Entry<String, Long>> mostPopularResources, FormatType formatType) {
        if (mostPopularResources.isEmpty()) {
            return MOST_POPULAR_RESOURCES_TITLE + EMPTY_REPORT;
        }
        List<String> names = new ArrayList<>();
        List<String> values = new ArrayList<>();
        for (var entry : mostPopularResources) {
            names.add(entry.getKey());
            values.add(entry.getValue().toString());
        }
        return MOST_POPULAR_RESOURCES_TITLE + getFormatted(
            names.toArray(String[]::new),
            values.toArray(String[]::new),
            RESOURCE_STATISTICS_NAME,
            REQUESTS_NUMBER_VALUES_NAME,
            formatType
        );
    }

    private static String getRequestPerResourceByTypeValuesName(RequestType requestType) {
        return "Количество " + requestType.verbal() + " запросов";
    }

    private static String getRequestPerResourceByTypeTitle(RequestType requestType) {
        return "Ресурсы с наибольшим количеством " + requestType.verbal() + " запросов к ним.\n\n";
    }

    private static String getFormatted(
        String[] names, String[] values, String statisticsName, String valuesName, FormatType formatType) {
        return switch (formatType) {
            case ADOC -> FormatUtils.reportToADOC(
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
