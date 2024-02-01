package com.zmart.api.product.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static com.zmart.api.product.util.ProductConstants.PRODUCTS;

@Slf4j
@Component
public class CacheRefreshCronJob {

    @CacheEvict(value = PRODUCTS, allEntries = true)
    @Scheduled(fixedRateString = "${pps.cron.cache.evict.seconds}")
    @Scheduled(cron = "${pps.cron.cache.evict.daily}", zone = "${pps.timezone.zone:UTC}")
    public void evictCache() {
        log.debug("[Cache] Refreshing the Cache");
    }
}
