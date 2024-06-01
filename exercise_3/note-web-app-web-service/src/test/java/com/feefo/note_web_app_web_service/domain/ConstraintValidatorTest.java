package com.feefo.note_web_app_web_service.domain;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.Test;

import static com.feefo.note_web_app_web_service.domain.ConstraintValidator.validate;
import static org.junit.jupiter.api.Assertions.*;

class ConstraintValidatorTest {

    @Test
    void shouldValidateConstraintWithError() {

        DummyClass dummyClass = new DummyClass(null);

        var exception = assertThrows(ConstraintViolationException.class, () -> validate(dummyClass));

        String expectedErrorMessage = "dummyAttribute: should not be null";

        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    void shouldValidateConstraintWithoutError() {

        DummyClass dummyClass = new DummyClass("dummy message");

        assertDoesNotThrow(() -> validate(dummyClass));
    }

    private static class DummyClass {

        @NotNull(message = "should not be null")
        private final String dummyAttribute;

        DummyClass(String dummyAttribute) {
            this.dummyAttribute = dummyAttribute;
        }
    }


}
