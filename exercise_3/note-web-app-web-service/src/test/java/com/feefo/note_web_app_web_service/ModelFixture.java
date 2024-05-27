package com.feefo.note_web_app_web_service;

import com.feefo.note_web_app_web_service.domain.note.Note;
import com.feefo.note_web_app_web_service.domain.user.User;

import java.time.LocalDateTime;
import java.util.List;

import static com.feefo.note_web_app_web_service.domain.note.Note.NoteBuilder;
import static com.feefo.note_web_app_web_service.domain.user.User.*;

public class ModelFixture {

    private ModelFixture() {}

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
