package com.zmart.api.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Slf4j
@EnableWebMvc
@Configuration
public class LoggerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(
            final HttpServletRequest request, final HttpServletResponse response, final Object handler) {
        logRequestMethodAndUri(request);
        logRemoteIpAddress(request);
        return true;
    }

    private void logRequestMethodAndUri(final HttpServletRequest request) {
        log.info("[" + request.getMethod() + "] " + request.getRequestURI());
    }

    private void logRemoteIpAddress(final HttpServletRequest request) {
        final String headerIp = request.getHeader("X-FORWARDED-FOR");
        if (headerIp != null && headerIp.length() > 0) {
            log.debug("[IP Address]: " + request.getHeader("X-FORWARDED-FOR"));
        }
    }
}
