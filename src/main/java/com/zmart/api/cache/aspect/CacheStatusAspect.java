package com.zmart.api.cache.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CacheStatusAspect {

    private static final ThreadLocal<Boolean> serviceMethodExecuted = ThreadLocal.withInitial(() -> false);

    @Around("@annotation(com.zmart.api.cache.aspect.CacheStatusHeader)")
    public Object trackCustomCache(ProceedingJoinPoint joinPoint) throws Throwable {
        serviceMethodExecuted.set(false);
        Object result = joinPoint.proceed();
        return processResponse(result);
    }

    @Around("@annotation(com.zmart.api.cache.aspect.CacheStatusTracked)")
    public Object trackServiceExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        serviceMethodExecuted.set(true);
        return joinPoint.proceed();
    }

    private Object processResponse(Object result) {
        if (!(result instanceof ResponseEntity<?> response))
            return result;

        HttpHeaders newHeaders = new HttpHeaders();
        newHeaders.addAll(response.getHeaders());
        newHeaders.add("X-Cache-Status", serviceMethodExecuted.get() ? "MISS" : "HIT");

        serviceMethodExecuted.remove();

        return ResponseEntity.status(response.getStatusCode())
                .headers(newHeaders)
                .body(response.getBody());
    }
}