package com.zmart.api.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.system.SystemProperties;
import org.springframework.context.event.EventListener;
import org.springframework.core.SpringVersion;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StartupLoggingListener {

    private static final String ROYAL_FLUSH = "\uD83C\uDCA1\uD83C\uDCAE\uD83C\uDCAD\uD83C\uDCAB\uD83C\uDCAA";

    @EventListener(ApplicationReadyEvent.class)
    public void logPostStartup() {
        final String versions = String.valueOf(
                new StringBuilder("Spring: ".concat(SpringVersion.getVersion()))
                        .append(" | ")
                        .append("JDK: ".concat(SystemProperties.get("java.version")))

        );
        log.info(versions);
        log.info("APP READY {}", ROYAL_FLUSH);
    }
}
