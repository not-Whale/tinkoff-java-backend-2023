package edu.project3.logs;

import java.time.ZonedDateTime;

public record Log(
    String remoteAddress,
    String remoteUser,
    ZonedDateTime timeLocal,
    RequestType requestType,
    String resource,
    String httpVersion,
    Integer status,
    Long bodyBytesSend,
    String httpRefer,
    String httpUserAgent
) {}
