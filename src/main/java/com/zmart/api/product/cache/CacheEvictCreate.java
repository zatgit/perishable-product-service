package com.zmart.api.product.cache;

import org.springframework.cache.annotation.CacheEvict;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.zmart.api.product.util.ProductConstants.PRODUCTS;
import static java.lang.annotation.ElementType.METHOD;

@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target({METHOD})
@CacheEvict(value = PRODUCTS, allEntries = true)
public @interface CacheEvictCreate {
}
