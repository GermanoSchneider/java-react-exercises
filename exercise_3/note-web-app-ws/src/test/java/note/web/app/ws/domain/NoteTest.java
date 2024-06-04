package note.web.app.ws.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import jakarta.validation.ConstraintViolationException;
import note.web.app.ws.ModelFixture;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class NoteTest {

    @Test
    void shouldCreateNoteModelWithSuccess() {

        Assertions.assertDoesNotThrow(ModelFixture::buildNote);
    }

    @Test
    void shouldFailWhenTryingToCreateANoteModelWithoutText() {

        ConstraintViolationException exception = assertThrows(
                ConstraintViolationException.class,
                () -> ModelFixture.noteBuilder().text(null).build()
        );

        String expectedMessage = "text: should not be null";

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void shouldFailWhenTryingToCreateANoteModelWithoutCreationDate() {

        ConstraintViolationException exception = assertThrows(
                ConstraintViolationException.class,
                () -> ModelFixture.noteBuilder().creation(null).build()
        );

        String expectedMessage = "creation: should not be null";

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void shouldFailWhenTryingToCreateANoteModelWithoutLastUpdateDate() {

        ConstraintViolationException exception = assertThrows(
                ConstraintViolationException.class,
                () -> ModelFixture.noteBuilder().lastUpdate(null).build()
        );

        String expectedMessage = "lastUpdate: should not be null";

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void shouldFailWhenTryingToCreateANoteModelWithoutOwner() {

        ConstraintViolationException exception = assertThrows(
                ConstraintViolationException.class,
            () -> ModelFixture.noteBuilder().owner(null).build()
        );

        String expectedMessage = "owner: should not be blank";

        assertEquals(expectedMessage, exception.getMessage());
    }
}
