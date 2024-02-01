package com.zmart.api.product.validation.validator;

import com.zmart.api.product.util.QualityOperationEnum;
import com.zmart.api.product.validation.ValidQualityOperation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class QualityOperationValidator implements ConstraintValidator<ValidQualityOperation, Integer> {

    @Override
    public boolean isValid(final Integer qualityOperation, final ConstraintValidatorContext ctx) {
        ctx.disableDefaultConstraintViolation();
        if(qualityOperation == null) {
            ctx.buildConstraintViolationWithTemplate("must not be null")
                    .addConstraintViolation();
            return false;
        } else {
            ctx.buildConstraintViolationWithTemplate(
                    String.format("%s%s%s",
                            "is an illegal option (available special cases are 0-",
                            QualityOperationEnum.values().length, ")"))
                    .addConstraintViolation();
            return isQualityOperationAllowed(qualityOperation);
        }
    }

    public static boolean isQualityOperationAllowed(final Integer qualityOperation) {
        if(qualityOperation != null)
            return qualityOperation <= QualityOperationEnum.values().length && qualityOperation >= 0;
        return false;
    }
}