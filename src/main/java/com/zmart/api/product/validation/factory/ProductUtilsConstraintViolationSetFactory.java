package com.zmart.api.product.validation.factory;

import com.zmart.api.product.util.StaticPropertyValidationMap;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class ProductUtilsConstraintViolationSetFactory implements ConstraintViolationSetFactory<StaticPropertyValidationMap> {

    @Override
    public Set<ConstraintViolation<StaticPropertyValidationMap>> create(StaticPropertyValidationMap map) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<ConstraintViolation<StaticPropertyValidationMap>> create(
            StaticPropertyValidationMap map, String propertyName) {
        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        return validator.validateProperty(map, propertyName);
    }
}
