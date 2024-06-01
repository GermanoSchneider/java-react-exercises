package com.feefo.note_web_app_web_service.domain;

import com.feefo.note_web_app_web_service.ModelFixture;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;

import static com.feefo.note_web_app_web_service.ModelFixture.userBuilder;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void shouldCreateUserModelWithSuccess() {

        assertDoesNotThrow(ModelFixture::buildUser);
    }

    @Test
    void shouldFailWhenTryingToCreateAUserModelWithoutName() {

        ConstraintViolationException exception = assertThrows(
                ConstraintViolationException.class,
                () -> userBuilder().name(null).build()
        );

        String expectedMessage = "name: should not be blank";

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void shouldFailWhenTryingToCreateAUserModelWithoutPassword() {

        ConstraintViolationException exception = assertThrows(
                ConstraintViolationException.class,
                () -> userBuilder().password(null).build()
        );

        String expectedMessage = "password: should not be blank";

        assertEquals(expectedMessage, exception.getMessage());
    }
}
