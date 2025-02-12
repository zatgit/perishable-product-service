package com.zmart.api.product.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


/**
 * Validates that Alphabet chars size min 1 & max 50,
 * not blank, has no special chars except for space.
 */
@NotBlank
@NoSpecialCharsBarSpace
@Size(min = 1, max = 50)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {})
public @interface ValidAlphaWithSpace {

    String message() default "must only contain not blank, not special chars except spaces " +
            "sized between 1 & 50";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}