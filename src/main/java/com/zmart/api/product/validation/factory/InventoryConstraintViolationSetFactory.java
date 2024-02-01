package com.zmart.api.product.validation.factory;

import com.zmart.api.product.entity.Inventory;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.groups.Default;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class InventoryConstraintViolationSetFactory implements ConstraintViolationSetFactory<Inventory> {

    @Override
    public Set<ConstraintViolation<Inventory>> create(Inventory inventory) {
        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        return inventory.getBuilderValidationGroup() == null
                ? validator.validate(inventory, Default.class)
                : validator.validate(inventory, inventory.getBuilderValidationGroup());
    }
}