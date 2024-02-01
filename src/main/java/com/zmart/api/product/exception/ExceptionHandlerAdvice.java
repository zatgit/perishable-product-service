package com.zmart.api.product.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.Nullable;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static com.zmart.api.product.exception.ExceptionUtils.filterExceptionTraceElements;
import static com.zmart.api.product.exception.ExceptionUtils.getCauseMessage;
import static com.zmart.api.product.exception.ExceptionUtils.getConstraintViolationMessage;
import static com.zmart.api.product.exception.ExceptionUtils.getDeclaringClass;
import static com.zmart.api.product.exception.ExceptionUtils.getExceptionName;
import static com.zmart.api.product.exception.ExceptionUtils.getLineNumber;
import static com.zmart.api.product.exception.ExceptionUtils.getMessage;
import static com.zmart.api.product.exception.ExceptionUtils.getMethodCaller;
import static com.zmart.api.product.exception.ExceptionUtils.getMethodName;
import static com.zmart.api.product.exception.ExceptionUtils.getTransactionSystemExceptionMessage;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

/**
 * Controller advice to translate the server side exceptions to client-friendly json structures.
 */
@ControllerAdvice
@PropertySource(value = "classpath:/org/hibernate/validator/ValidationMessages.properties")
@Slf4j
public class ExceptionHandlerAdvice extends ResponseEntityExceptionHandler {

    @Value("${org.hibernate.validator.constraints.UniqueElements.message}")
    private String uniqueElementsViolationMessage;

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleException(final Exception ex) {
        //TODO: When upgrade to Java 21+ use switch statement
        // See JEP 441: Pattern Matching for switch
        if (ex instanceof ConstraintViolationException conEx) {
            return handleBadRequest(ex, getConstraintViolationMessage(conEx));
        } else if (ex instanceof TransactionSystemException tranEx) {
            return handleBadRequest(ex, getTransactionSystemExceptionMessage(tranEx));
        } else {
            return handleExceptionInternal(ex);
        }
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            final HttpMessageNotReadableException ex,
            final HttpHeaders headers,
            final HttpStatusCode status,
            final WebRequest request) {
        return handleBadRequest(ex, null);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            final HttpRequestMethodNotSupportedException ex,
            final HttpHeaders headers,
            final HttpStatusCode status,
            final WebRequest request) {
        final ExceptionBuilder exBuilder =
                ExceptionBuilder.builder()
                        .code(String.valueOf(status))
                        .message(getMessage(ex))
                        .exception(getExceptionName(ex))
                        .cause(ExceptionBuilder.Cause.builder()
                                .build())
                        .build();
        return new ResponseEntity<>(exBuilder, status);
    }

    /**
     * Handles MethodArgumentNotValidException.
     * Thrown by hibernate-validator violations.
     * Not thrown during entity binding.
     */
    @Nullable
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            final MethodArgumentNotValidException ex, final HttpHeaders headers, final HttpStatusCode status, final WebRequest request) {
        if (ex.hasFieldErrors() && String.valueOf(ex.getFieldError()).contains(uniqueElementsViolationMessage)) {
            return handleBadRequest(ex, ExceptionUtils.getConViolMsgForMethodNotValidUniqueElements(ex));
        } else {
            return handleBadRequest(ex, ExceptionUtils.getConViolMsgForMethodNotValid(ex));
        }
    }

    /**
     * Handles 400 client errors that are not overridden.
     */
    protected ResponseEntity<Object> handleBadRequest(
            final Exception ex,
            final String message) {
        final HttpStatus status = BAD_REQUEST;
        final StackTraceElement[] filteredTrace = filterExceptionTraceElements(ex);
        final ExceptionBuilder exBuilder =
                ExceptionBuilder.builder()
                        .code(String.valueOf(status))
                        .message(message)
                        .exception(getExceptionName(ex))
                        .cause(ExceptionBuilder.Cause.builder()
                                .message(getMessage(ex))
                                .causeMessage(getCauseMessage(ex))
                                .declaringClass(getDeclaringClass(filteredTrace))
                                .methodName(getMethodName(filteredTrace))
                                .methodCaller(getMethodCaller(filteredTrace))
                                .lineNumber(getLineNumber(filteredTrace))
                                .build())
                        .build();
        return new ResponseEntity<>(exBuilder, status);
    }

    /**
     * Handles 500 server errors.
     */
    protected ResponseEntity<Object> handleExceptionInternal(final Exception ex) {
        final HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        final StackTraceElement[] filteredTrace = filterExceptionTraceElements(ex);
        final ExceptionBuilder exBuilder =
                ExceptionBuilder.builder()
                        .code(String.valueOf(status))
                        .message(INTERNAL_SERVER_ERROR.getReasonPhrase())
                        .cause(ExceptionBuilder.Cause.builder()
                                .nestedException(getExceptionName(ex))
                                .message(getMessage(ex))
                                .causeMessage(getCauseMessage(ex))
                                .declaringClass(getDeclaringClass(filteredTrace))
                                .methodName(getMethodName(filteredTrace))
                                .methodCaller(getMethodCaller(filteredTrace))
                                .lineNumber(getLineNumber(filteredTrace))
                                .build())
                        .build();
        return new ResponseEntity<>(exBuilder, status);
    }
}
