package feefo.note.web.app.ws.domain;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

public class ConstraintValidator {

    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    public static <T> void validate(T model) {

        var errors = VALIDATOR.validate(model);

        if (!errors.isEmpty()) {
            throw new ConstraintViolationException(errors);
        }
    }
}
