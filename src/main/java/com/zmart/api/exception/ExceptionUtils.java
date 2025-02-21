package com.zmart.api.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.experimental.UtilityClass;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static com.zmart.api.product.util.ProductConstants.EMPTY_STRING;
import static com.zmart.api.product.util.ProductUtility.getClassAndMethodFromFqdn;
import static com.zmart.api.product.util.ProductUtility.isObjectEmpty;
import static com.zmart.api.product.util.ProductUtility.replaceBrackets;

@UtilityClass
@SuppressWarnings("java:S2259") //checked for null
public final class ExceptionUtils {

    public static final String PRODUCT_NOT_FOUND = "Product not found";
    private static final String EX_FQN_FILTER_INCLUDE = "com.zmart.food";
    private static final String EX_FQN_FILTER_EXCLUDE = "exception";
    private static final String FORMAT_WHITESPACE_TWO_STRINGS = "%s %s";

    /**
     * Filters stackTrace elements to:
     *
     * <br>include only project-specific packages &
     *
     * <br>exclude exception classes
     *
     * @param ex
     */
    static StackTraceElement[] filterExceptionTraceElements(final Exception ex) {
        return
                Arrays.stream(ex.getStackTrace())
                        .filter(
                                element ->
                                        element.getClassName().contains(EX_FQN_FILTER_INCLUDE)
                                                && !element.getClassName().contains(EX_FQN_FILTER_EXCLUDE))
                        .toArray(StackTraceElement[]::new);
    }

    // Also used for nestedException on 500 error
    static String getExceptionName(final Exception ex) {
        return ex.getClass().getSimpleName();
    }

    static String getMessage(final Exception ex) {
        return ex.getMessage();
    }

    static String getCauseMessage(final Exception ex) {
        return Optional.ofNullable(ex.getCause()).isPresent() &&
                Optional.ofNullable(ex.getCause().getMessage()).isPresent()
                ? replaceMessageIfDuplicatesDetailMessage(ex)
                : EMPTY_STRING;
    }

    private static String replaceMessageIfDuplicatesDetailMessage(Exception ex) {
        return ex.getCause().getMessage().contentEquals(
                ex.getMessage()) ? EMPTY_STRING : ex.getCause().getMessage();
    }

    static String getMethodName(final StackTraceElement[] filteredTrace) {
        return isStackTraceArrayNotEmpty(filteredTrace)
                ? filteredTrace[0].getMethodName()
                : null;
    }

    static String getMethodCaller(final StackTraceElement[] filteredTrace) {
        return isStackTraceArrayNotEmpty(filteredTrace)
                ? getClassAndMethodFromFqdn(String.format("%s.%s",
                        removeCglibGeneratedProxySuffixes(getMethodCallerDeclaringClass(filteredTrace)),
                        removeCglibGeneratedProxySuffixes(filteredTrace[1].getMethodName())))
                : null;
    }

    private static String removeCglibGeneratedProxySuffixes(final String fqdnCglib) {
        return fqdnCglib
                .replace("$$SpringCGLIB$$0", EMPTY_STRING)
                .replace("<generated>", EMPTY_STRING);
    }

    static String getDeclaringClass(final StackTraceElement[] filteredTrace) {
        return isStackTraceArrayNotEmpty(filteredTrace)
                ? filteredTrace[0].getClassName()
                : null;
    }

    private static String getMethodCallerDeclaringClass(final StackTraceElement[] filteredTrace) {
        return isStackTraceArrayNotEmpty(filteredTrace)
                ? filteredTrace[1].getClassName()
                : null;
    }

    /**
     * negative numbers indicate line number unavailable; could be library class -2 indicates native
     * method
     */
    static Integer getLineNumber(final StackTraceElement[] filteredTrace) {
        return isStackTraceArrayNotEmpty(filteredTrace)
                ? filteredTrace[0].getLineNumber()
                : null;
    }

    static boolean isStackTraceArrayNotEmpty(final StackTraceElement[] filteredTrace) {
        return filteredTrace != null && filteredTrace.length > 0;
    }

    /**
     * Helper for handling ConstraintViolationException.
     * Thrown by hibernate-validator violations.
     */
    static String getConstraintViolationMessage(final ConstraintViolationException conEx) {
        return Objects.requireNonNull(String.format(FORMAT_WHITESPACE_TWO_STRINGS,
                getConViolMsgPrefixForConstraintViolation(conEx.getConstraintViolations()),
                replaceBrackets(conEx.getConstraintViolations()
                        .parallelStream()
                        .map(violation -> String.format("%s%s%s %s",
                                "'" , violation.getPropertyPath()
                                        , "'" , violation.getMessage()))
                .toList()
                .toString())));
    }

    static String getConViolMsgForMethodNotValid(MethodArgumentNotValidException ex) {
        List<Object> validDetailMsgArgs = filterEmptyMethodNotValidDetailMsgArgs(ex.getDetailMessageArguments());
        return String.format(FORMAT_WHITESPACE_TWO_STRINGS,
                getConViolMsgPrefixForMethodNotValid(validDetailMsgArgs),
                replaceBrackets(
                        Objects.requireNonNull(
                                validDetailMsgArgs.toString())
                                .replace(":", EMPTY_STRING)
                                .replace("'", EMPTY_STRING)));
    }

    private static List<Object> filterEmptyMethodNotValidDetailMsgArgs(Object[] msgArgArray) {
        return Arrays.stream(msgArgArray)
                .filter(o -> !isObjectEmpty(o))
                .toList();
    }

    static String getConViolMsgForMethodNotValidUniqueElements(final MethodArgumentNotValidException ex) {
        final String invalidField = String.valueOf(ex.getFieldError()).split("'")[3];
        final String[] messageArr = ex.getMessage().split("default message");
        return replaceBrackets(String.format(
                FORMAT_WHITESPACE_TWO_STRINGS, invalidField, messageArr[messageArr.length - 1].trim()));
    }

    private static String getConViolMsgPrefixForMethodNotValid(
            final List<Object> violationMessageList) {
        return violationMessageList.size() > 1 ||
                hasMultipleViolationsInViolMsgSublist(violationMessageList)
                ? "Invalid properties:"
                : "Property";
    }

    private static boolean hasMultipleViolationsInViolMsgSublist(List<Object> violationMessageList) {
        return violationMessageList.get(0).toString().split(",").length > 1;
    }

    private static String getConViolMsgPrefixForConstraintViolation(
            final Set<ConstraintViolation<?>> violationMessageList) {
        return violationMessageList.size() > 1
                ? "Invalid properties:"
                : "Property";
    }

    /**Called when validation violation occurs during transaction.*/
    static String getTransactionSystemExceptionMessage(final TransactionSystemException tranEx) {
        if(tranEx.getCause().getCause() instanceof ConstraintViolationException conEX) {
            return getConstraintViolationMessage(conEX);
        }
        return tranEx.getCause().getMessage();
    }

    public static String getProductNotFoundMessage(String fieldName, String fieldValue) {
        return String.format("Product with %s '%s' not found", fieldName, fieldValue);
    }
}
