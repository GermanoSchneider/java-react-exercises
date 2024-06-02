package feefo.note.web.app.ws.domain;

import static feefo.note.web.app.ws.domain.ConstraintValidator.validate;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.Test;

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
