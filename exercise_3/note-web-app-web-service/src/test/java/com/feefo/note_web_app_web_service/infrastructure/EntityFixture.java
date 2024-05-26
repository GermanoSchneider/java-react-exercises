package com.feefo.note_web_app_web_service.infrastructure;

import com.feefo.note_web_app_web_service.domain.Note;

import java.time.LocalDateTime;

import static com.feefo.note_web_app_web_service.domain.Note.builder;

class EntityFixture {

    private EntityFixture() {}

    static NoteEntity buildFrom(Note note) {

        return new NoteEntity(
                null,
                note.getText(),
                note.getCreation(),
                note.getLastUpdate()
        );
    }

    static Note buildFrom(NoteEntity noteEntity) {

        return Note.builder()
                .id(noteEntity.getId())
                .text(noteEntity.getText())
                .creation(noteEntity.getCreation())
                .lastUpdate(noteEntity.getLastUpdate())
                .build();
    }
}
