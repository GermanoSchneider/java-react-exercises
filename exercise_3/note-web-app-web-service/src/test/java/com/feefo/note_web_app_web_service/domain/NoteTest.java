package com.feefo.note_web_app_web_service.domain;

import com.feefo.note_web_app_web_service.ModelFixture;
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

        ConstraintException exception = assertThrows(
                ConstraintException.class,
                () -> noteBuilder().text(null).build()
        );

        String expectedMessage = "text should not be a null value";

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void shouldFailWhenTryingToCreateANoteModelWithoutCreationDate() {

        ConstraintException exception = assertThrows(
                ConstraintException.class,
                () -> noteBuilder().creation(null).build()
        );

        String expectedMessage = "creation should not be a null value";

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void shouldFailWhenTryingToCreateANoteModelWithoutLastUpdateDate() {

        ConstraintException exception = assertThrows(
                ConstraintException.class,
                () -> noteBuilder().lastUpdate(null).build()
        );

        String expectedMessage = "lastUpdate should not be a null value";

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void shouldFailWhenTryingToCreateANoteModelWithoutUser() {

        ConstraintException exception = assertThrows(
            ConstraintException.class,
            () -> noteBuilder().user(null).build()
        );

        String expectedMessage = "user should not be a null value";

        assertEquals(expectedMessage, exception.getMessage());
    }
}
