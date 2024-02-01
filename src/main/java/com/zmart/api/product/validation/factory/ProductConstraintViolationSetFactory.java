package com.zmart.api.product.validation.factory;

import com.zmart.api.product.entity.Product;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class ProductConstraintViolationSetFactory implements ConstraintViolationSetFactory<Product> {
    @Override
    public Set<ConstraintViolation<Product>> create(Product product) {
        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        return validator.validate(product);
    }
}
