package feefo.note.web.app.ws.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import feefo.note.web.app.ws.ModelFixture;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UserTest {

    @Test
    void shouldCreateUserModelWithSuccess() {

        Assertions.assertDoesNotThrow(ModelFixture::buildUser);
    }

    @Test
    void shouldFailWhenTryingToCreateAUserModelWithoutName() {

        ConstraintViolationException exception = assertThrows(
                ConstraintViolationException.class,
                () -> ModelFixture.userBuilder().name(null).build()
        );

        String expectedMessage = "name: should not be blank";

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void shouldFailWhenTryingToCreateAUserModelWithoutPassword() {

        ConstraintViolationException exception = assertThrows(
                ConstraintViolationException.class,
                () -> ModelFixture.userBuilder().password(null).build()
        );

        String expectedMessage = "password: should not be blank";

        assertEquals(expectedMessage, exception.getMessage());
    }
}
