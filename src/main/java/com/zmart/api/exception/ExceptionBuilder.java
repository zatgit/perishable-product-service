package com.zmart.api.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.annotation.PostConstruct;
import lombok.Builder;

import java.time.Instant;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;
import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Builder
@JsonInclude(NON_NULL)
@JsonPropertyOrder({"timestamp", "code", "exception", "message", "cause"})
public record ExceptionBuilder(
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
        Instant timestamp,
        String code,
        String message,
        String exception,
        Cause cause
) {

    public ExceptionBuilder {
        cause = validateCause(cause);
    }

    /**
     * <br>Nullifies Cause object if invalid
     * <br>Jackson omits from response if null
     */
    @PostConstruct
    static Cause validateCause(Cause cause) {
        if (cause.message == null && cause.declaringClass == null) {
            cause = null;
        }
        return cause;
    }

    @Builder
    @JsonInclude(NON_EMPTY)
    @JsonPropertyOrder({"exception", "message", "detail", "declaringClass",
            "methodName", "methodCaller", "line"})
    static record Cause(
            @JsonProperty("exception")
            String nestedException,
            @JsonProperty("message")
            String message,
            @JsonProperty("detail")
            String causeMessage,
            @JsonProperty("class")
            String declaringClass,
            @JsonProperty("method")
            String methodName,
            @JsonProperty("caller")
            String methodCaller,
            @JsonProperty("line")
            Integer lineNumber
    ) {
    }
}
