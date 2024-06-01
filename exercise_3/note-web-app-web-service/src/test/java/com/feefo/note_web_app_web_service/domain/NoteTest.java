package com.feefo.note_web_app_web_service.domain;

import com.feefo.note_web_app_web_service.ModelFixture;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;

import static com.feefo.note_web_app_web_service.ModelFixture.noteBuilder;
import static org.junit.jupiter.api.Assertions.*;

class NoteTest {

    @Test
    void shouldCreateNoteModelWithSuccess() {

        assertDoesNotThrow(ModelFixture::buildNote);
    }

    @Test
    void shouldFailWhenTryingToCreateANoteModelWithoutText() {

        ConstraintViolationException exception = assertThrows(
                ConstraintViolationException.class,
                () -> noteBuilder().text(null).build()
        );

        String expectedMessage = "text: should not be null";

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void shouldFailWhenTryingToCreateANoteModelWithoutCreationDate() {

        ConstraintViolationException exception = assertThrows(
                ConstraintViolationException.class,
                () -> noteBuilder().creation(null).build()
        );

        String expectedMessage = "creation: should not be null";

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void shouldFailWhenTryingToCreateANoteModelWithoutLastUpdateDate() {

        ConstraintViolationException exception = assertThrows(
                ConstraintViolationException.class,
                () -> noteBuilder().lastUpdate(null).build()
        );

        String expectedMessage = "lastUpdate: should not be null";

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void shouldFailWhenTryingToCreateANoteModelWithoutOwner() {

        ConstraintViolationException exception = assertThrows(
                ConstraintViolationException.class,
            () -> noteBuilder().owner(null).build()
        );

        String expectedMessage = "owner: should not be blank";

        assertEquals(expectedMessage, exception.getMessage());
    }
}
