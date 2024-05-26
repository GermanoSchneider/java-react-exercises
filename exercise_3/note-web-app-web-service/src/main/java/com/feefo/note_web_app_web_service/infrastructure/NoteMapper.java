package com.feefo.note_web_app_web_service.infrastructure;

import com.feefo.note_web_app_web_service.domain.Note;

import static com.feefo.note_web_app_web_service.domain.Note.*;

class NoteMapper {

    private NoteMapper() {}

    static NoteEntity from(Note note) {

        return new NoteEntity(
                note.getId(),
                note.getText(),
                note.getCreation(),
                note.getLastUpdate()
        );
    }

    static NoteBuilder from(NoteEntity noteEntity) {

        return builder()
                .id(noteEntity.getId())
                .text(noteEntity.getText())
                .creation(noteEntity.getCreation())
                .lastUpdate(noteEntity.getLastUpdate());
    }
}
