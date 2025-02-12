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
                        + "#itemCode, "
                        + "#request.limit, "
                        + "#request.sortBy, "
                        + "#request.sortOrder, "
                        + "#request.dayOffset}",
        sync = true)
public @interface CacheableItemCode {
}
