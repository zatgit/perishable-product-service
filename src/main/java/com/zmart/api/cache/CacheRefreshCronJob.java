package com.zmart.api.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static com.zmart.api.product.util.ProductConstants.PRODUCTS;

@Slf4j
@Component
public class CacheRefreshCronJob {

    @CacheEvict(value = PRODUCTS, allEntries = true)
    @Scheduled(cron = "${pps.cron.cache.evict.interval}", zone = "${pps.timezone.zone:UTC}")
    public void evictCache() {
        log.info("[Cache] Refreshing the Cache");
    }
}
