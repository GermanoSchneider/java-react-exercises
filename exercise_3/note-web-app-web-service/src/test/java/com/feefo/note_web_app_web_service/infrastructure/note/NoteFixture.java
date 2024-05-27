package com.feefo.note_web_app_web_service.infrastructure.note;

import com.feefo.note_web_app_web_service.domain.note.Note;
import com.feefo.note_web_app_web_service.infrastructure.note.persistence.NoteEntity;
import com.feefo.note_web_app_web_service.infrastructure.user.UserFixture;

import static com.feefo.note_web_app_web_service.infrastructure.user.UserFixture.*;

public class NoteFixture {

    private NoteFixture() {}

    public static NoteEntity buildFrom(Note note) {

        return NoteEntity.builder()
                .id(note.getId())
                .text(note.getText())
                .creation(note.getCreation())
                .lastUpdate(note.getLastUpdate())
                .userEntity(UserFixture.buildFrom(note.getUser()))
                .build();
    }

    public static Note buildFrom(NoteEntity noteEntity) {

        return Note.builder()
                .id(noteEntity.getId())
                .text(noteEntity.getText())
                .creation(noteEntity.getCreation())
                .lastUpdate(noteEntity.getLastUpdate())
                .user(UserFixture.buildFrom(noteEntity.getUser()))
                .build();
    }

    public static NoteResponseDto toNoteResponseDto(Note note) {

        return new NoteResponseDto(
            note.getId(),
            note.getText(),
            note.getCreation(),
            note.getLastUpdate()
        );
    }
}
