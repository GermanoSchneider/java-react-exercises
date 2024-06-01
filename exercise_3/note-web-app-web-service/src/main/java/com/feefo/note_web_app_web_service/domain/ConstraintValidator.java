package com.feefo.note_web_app_web_service.domain;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import static java.lang.String.format;
import static java.util.Objects.isNull;

public class ConstraintValidator {

    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    public static <T> void validate(T model) {

        var errors = VALIDATOR.validate(model);

        if (!errors.isEmpty()) {
            throw new ConstraintViolationException(errors);
        }
    }
}
