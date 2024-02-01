package com.zmart.api.product.cache;

import org.springframework.cache.annotation.Cacheable;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target({ElementType.METHOD})
@Cacheable(
        key =
                "{#root.targetClass.canonicalName.substring("
                        + "#root.targetClass.canonicalName.lastIndexOf('.') + 1), "
                        + "#root.methodName, "
                        + "#request.itemCode, "
                        + "#request.dataViewDto.limit, "
                        + "#request.dataViewDto.sortBy, "
                        + "#request.dataViewDto.sortOrder, "
                        + "#request.dataViewDto.dayOffset}",
        sync = true)
public @interface CacheableItemCode {
}
