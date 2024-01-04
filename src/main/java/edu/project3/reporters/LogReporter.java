package edu.project3.reporters;

import edu.project3.logs.Log;
import edu.project3.logs.RequestType;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.jetbrains.annotations.Nullable;

public class LogReporter {
    public static final int OK_RESPONSE_CODE = 200;

    public static final int LIMIT = 3;

    private final Log[] logs;

    private final String resource;

    private final LocalDateTime from;

    private final LocalDateTime to;

    private LogReporter(
        Log[] logs,
        String resource,
        @Nullable LocalDateTime from,
        @Nullable LocalDateTime to) {
        this.logs = logs;
        this.resource = resource;
        this.from = from;
        this.to = to;
    }

    public static LogReporter of(
        Log[] logs,
        @Nullable String resource,
        @Nullable LocalDateTime from,
        @Nullable LocalDateTime to) {
        if (logs == null) {
            throw new IllegalArgumentException("Logs must not be null!");
        }
        return resource == null || resource.isEmpty()
            ? new LogReporter(filterLogs(logs, from, to), "unknown", from, to)
            : new LogReporter(filterLogs(logs, from, to), resource, from, to);
    }

    public GeneralInfo generalInfo() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy hh:mm:ss");
        String fromString = from != null ? from.format(formatter) : "-";
        String toString = to != null ? to.format(formatter) : "-";
        return new GeneralInfo(
            resource,
            fromString,
            toString,
            (long) logs.length,
            calculateAverageResponseSize()
        );
    }

    public List<Map.Entry<String, Long>> mostPopularResources() {
        Map<String, Long> resourcesMap = Arrays.stream(logs)
            .collect(Collectors.toMap(
                Log::resource,
                item -> 1L,
                Long::sum
            ));

        return resourcesMap.entrySet().stream()
            .sorted((a, b) -> (int) (b.getValue() - a.getValue()))
            .limit(LIMIT)
            .toList();
    }

    public List<Map.Entry<Integer, Long>> mostPopularResponseCodes() {
        Map<Integer, Long> codesMap = Arrays.stream(logs)
            .collect(Collectors.toMap(
                Log::status,
                item -> 1L,
                Long::sum
            ));

        return codesMap.entrySet().stream()
            .sorted((a, b) -> (int) (b.getValue() - a.getValue()))
            .limit(LIMIT)
            .toList();
    }

    public List<Map.Entry<RequestType, Long>> mostPopularRequestTypes() {
        Map<RequestType, Long> requestsMap = Arrays.stream(logs)
            .collect(Collectors.toMap(
                Log::requestType,
                item -> 1L,
                Long::sum
            ));

        return requestsMap.entrySet().stream()
            .sorted((a, b) -> (int) (b.getValue() - a.getValue()))
            .limit(LIMIT)
            .toList();
    }

    public List<Map.Entry<String, Long>> requestsPerResourceByType(RequestType type) {
        Map<String, Long> requestTypeMap = Arrays.stream(logs)
            .filter(log -> log.requestType().equals(type))
            .collect(Collectors.toMap(
                Log::resource,
                item -> 1L,
                Long::sum
            ));

        return requestTypeMap.entrySet().stream()
            .sorted((a, b) -> (int) (b.getValue() - a.getValue()))
            .toList();
    }

    public List<Map.Entry<String, Double>> mostStableResources() {
        Map<String, Long> resourcesMap = Arrays.stream(logs)
            .collect(Collectors.toMap(
                Log::resource,
                item -> 1L,
                Long::sum
            ));

        Map<String, Long> requestTypeMap = Arrays.stream(logs)
            .filter(log -> log.status().equals(OK_RESPONSE_CODE))
            .collect(Collectors.toMap(
                Log::resource,
                item -> 1L,
                Long::sum
            ));

        return resourcesMap.entrySet().stream()
            .map(entry -> Map.entry(
                entry.getKey(),
                (double) requestTypeMap.getOrDefault(entry.getKey(), 0L) / entry.getValue()
            ))
            .sorted((a, b) -> {
                if (b.getValue() > a.getValue()) {
                    return 1;
                } else if (b.getValue() < a.getValue()) {
                    return -1;
                }
                return 0;
            })
            .toList();
    }

    private Long calculateAverageResponseSize() {
        return Arrays.stream(logs)
            .collect(Collectors.averagingLong(Log::bodyBytesSend))
            .longValue();
    }

    private static Log[] filterLogs(Log[] logs, LocalDateTime from, LocalDateTime to) {
        if (from == null && to == null) {
            return logs;
        }
        Log[] filteredLogs = Arrays.copyOf(logs, logs.length);
        if (from != null) {
            filteredLogs = Arrays.stream(filteredLogs)
                .filter(log -> {
                    ZoneId zoneId = log.timeLocal().getZone();
                    ZonedDateTime fromZoned = ZonedDateTime.of(from, zoneId);
                    return log.timeLocal().isAfter(fromZoned);
                })
                .toArray(Log[]::new);
        }
        if (to != null) {
            filteredLogs = Arrays.stream(filteredLogs)
                .filter(log -> {
                    ZoneId zoneId = log.timeLocal().getZone();
                    ZonedDateTime toZoned = ZonedDateTime.of(to, zoneId);
                    return log.timeLocal().isBefore(toZoned);
                })
                .toArray(Log[]::new);
        }
        return filteredLogs;
    }
}
