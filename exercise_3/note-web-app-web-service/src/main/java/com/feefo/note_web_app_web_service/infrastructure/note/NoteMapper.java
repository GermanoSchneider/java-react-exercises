package com.feefo.note_web_app_web_service.infrastructure.note;

import com.feefo.note_web_app_web_service.domain.note.Note;
import com.feefo.note_web_app_web_service.domain.user.User;
import com.feefo.note_web_app_web_service.infrastructure.note.persistence.NoteEntity;
import com.feefo.note_web_app_web_service.infrastructure.note.persistence.NoteEntity.NoteEntityBuilder;
import com.feefo.note_web_app_web_service.infrastructure.user.UserMapper;

import java.time.LocalDateTime;

import static com.feefo.note_web_app_web_service.domain.note.Note.NoteBuilder;
import static com.feefo.note_web_app_web_service.domain.note.Note.builder;

public class NoteMapper {

    private NoteMapper() {}

    public static NoteEntityBuilder from(Note note) {

        return NoteEntity.builder()
                .id(note.getId())
                .text(note.getText())
                .creation(note.getCreation())
                .lastUpdate(note.getLastUpdate())
                .userEntity(UserMapper.from(note.getUser()).build());
    }

    public static NoteBuilder from(NoteEntity noteEntity) {

        return builder()
                .id(noteEntity.getId())
                .text(noteEntity.getText())
                .creation(noteEntity.getCreation())
                .lastUpdate(noteEntity.getLastUpdate())
                .user(UserMapper.from(noteEntity.getUser()).build());
    }

    public static NoteResponseDto toNoteResponseDto(Note note) {

        return new NoteResponseDto(
                note.getId(),
                note.getText(),
                note.getCreation(),
                note.getLastUpdate()
        );
    }

    public static Note from(NoteRequestDto dto, User user) {

        return builder()
                .text(dto.getText())
                .creation(LocalDateTime.now())
                .lastUpdate(LocalDateTime.now())
                .user(user)
                .build();
    }
}
