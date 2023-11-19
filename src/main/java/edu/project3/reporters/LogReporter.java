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

public class LogReporter {
    public static final int OK_RESPONSE_CODE = 200;

    public static final int LIMIT = 3;

    private final Log[] logs;

    private final String resource;

    private final LocalDateTime from;

    private final LocalDateTime to;

    public LogReporter(Log[] logs, String resource, LocalDateTime from, LocalDateTime to) {
        this.resource = resource;
        this.from = from;
        this.to = to;
        this.logs = filterLogs(logs);
    }

    private Log[] filterLogs(Log[] logs) {
        return Arrays.stream(logs)
            .filter(log -> {
                ZoneId zoneId = log.timeLocal().getZone();
                ZonedDateTime fromZoned = ZonedDateTime.of(from, zoneId);
                ZonedDateTime toZoned = ZonedDateTime.of(to, zoneId);
                return log.timeLocal().isBefore(toZoned) && log.timeLocal().isAfter(fromZoned);
            })
            .toArray(Log[]::new);
    }

    public GeneralInfo getGeneralInfoReport() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return new GeneralInfo(
            resource,
            from.format(formatter),
            to.format(formatter),
            logs.length,
            calculateAverageResponseSize()
        );
    }

    private Long calculateAverageResponseSize() {
        return Arrays.stream(logs).collect(Collectors.averagingLong(Log::bodyBytesSend)).longValue();
    }

    public List<Map.Entry<String, Long>> getMostPopularResources() {
        Map<String, Long> resourcesMap = Arrays.stream(logs)
            .collect(Collectors.toMap(
                Log::resource,
                item -> 1L,
                Long::sum
            ));

        return resourcesMap.entrySet().stream()
            .sorted((a, b) -> (int) (b.getValue() - a.getValue()))
            .limit(3)
            .toList();
    }

    public List<Map.Entry<Integer, Long>> getMostPopularResponseCodes() {
        Map<Integer, Long> codesMap = Arrays.stream(logs)
            .collect(Collectors.toMap(
                Log::status,
                item -> 1L,
                Long::sum
            ));

        return codesMap.entrySet().stream()
            .sorted((a, b) -> (int) (b.getValue() - a.getValue()))
            .limit(3)
            .toList();
    }

    public List<Map.Entry<RequestType, Long>> getMostPopularRequestTypes() {
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

    public List<Map.Entry<String, Long>> getRequestsPerResourceByType(RequestType type) {
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

    public List<Map.Entry<String, Double>> getMostStableResources() {
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
                (double) entry.getValue() / requestTypeMap.get(entry.getKey())))
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
}
