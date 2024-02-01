package com.zmart.api.product.validation.factory;

import com.zmart.api.product.util.StaticPropertyValidationMap;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

import java.util.Set;

public interface ConstraintViolationSetFactory<T> {

    Set<ConstraintViolation<T>> create(T type);

    default Set<ConstraintViolation<StaticPropertyValidationMap>> create(StaticPropertyValidationMap map, String propertyName) {
            final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
            return validator.validateProperty(map, propertyName);
    }
}

