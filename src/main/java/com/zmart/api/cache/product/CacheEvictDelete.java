package com.zmart.api.cache.product;

import org.springframework.cache.annotation.CacheEvict;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.zmart.api.product.util.ProductConstants.PRODUCTS;

@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target({ElementType.METHOD})
@CacheEvict(value = PRODUCTS, allEntries = true)
public @interface CacheEvictDelete {
}
