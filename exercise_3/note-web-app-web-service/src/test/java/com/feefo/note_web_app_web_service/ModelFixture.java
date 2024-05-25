package com.feefo.note_web_app_web_service;

import com.feefo.note_web_app_web_service.domain.Note;

import java.time.LocalDateTime;

import static com.feefo.note_web_app_web_service.domain.Note.*;
import static com.feefo.note_web_app_web_service.domain.Note.builder;

public class ModelFixture {

    private ModelFixture() {}

    public static Note buildNote() {

        return noteBuilder()
                .build();
    }

    public static NoteBuilder noteBuilder() {

        return builder()
                .text("dummy text")
                .creation(LocalDateTime.now())
                .lastUpdate(LocalDateTime.now());
    }
}
