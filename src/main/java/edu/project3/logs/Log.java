package edu.project3.logs;

import java.time.ZonedDateTime;
import org.jetbrains.annotations.Nullable;

public record Log(
    String remoteAddress,
    @Nullable String remoteUser,
    ZonedDateTime timeLocal,
    RequestType requestType,
    String resource,
    String httpVersion,
    Integer status,
    Long bodyBytesSend,
    @Nullable String httpRefer,
    @Nullable String httpUserAgent
) {}
