package com.feefo.note_web_app_web_service.domain;

import com.feefo.note_web_app_web_service.ModelFixture;
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

        ConstraintException exception = assertThrows(
                ConstraintException.class,
                () -> userBuilder().name(null).build()
        );

        String expectedMessage = "name should not be a null value";

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void shouldFailWhenTryingToCreateANoteModelWithoutPassword() {

        ConstraintException exception = assertThrows(
                ConstraintException.class,
                () -> userBuilder().password(null).build()
        );

        String expectedMessage = "password should not be a null value";

        assertEquals(expectedMessage, exception.getMessage());
    }
}
