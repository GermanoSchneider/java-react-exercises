package com.feefo.note_web_app_web_service.infrastructure.note;

import com.feefo.note_web_app_web_service.domain.note.Note;

import static com.feefo.note_web_app_web_service.domain.note.Note.NoteBuilder;
import static com.feefo.note_web_app_web_service.domain.note.Note.builder;

public class NoteMapper {

    private NoteMapper() {}

    public static NoteEntity.NoteEntityBuilder from(Note note) {

        return NoteEntity.builder()
                .id(note.getId())
                .text(note.getText())
                .creation(note.getCreation())
                .lastUpdate(note.getLastUpdate());
    }

    public static NoteBuilder from(NoteEntity noteEntity) {

        return builder()
                .id(noteEntity.getId())
                .text(noteEntity.getText())
                .creation(noteEntity.getCreation())
                .lastUpdate(noteEntity.getLastUpdate());
    }
}
