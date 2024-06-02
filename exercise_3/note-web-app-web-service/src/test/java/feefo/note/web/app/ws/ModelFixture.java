package feefo.note.web.app.ws;

import static feefo.note.web.app.ws.domain.note.Note.NoteBuilder;
import static feefo.note.web.app.ws.domain.user.User.UserBuilder;

import feefo.note.web.app.ws.domain.note.Note;
import feefo.note.web.app.ws.domain.user.User;
import java.time.LocalDateTime;

public class ModelFixture {

    private ModelFixture() {

    }

    public static Note buildNote() {

        return noteBuilder()
                .build();
    }

    public static User buildUser() {

        return userBuilder()
                .build();
    }

    public static NoteBuilder noteBuilder() {

        return Note.builder()
                .id(1L)
                .text("dummy text")
                .creation(LocalDateTime.now())
                .lastUpdate(LocalDateTime.now())
                .owner("john");
    }

    public static UserBuilder userBuilder() {

        return User.builder()
                .id(1L)
                .name("john")
                .password("123");
    }
}
