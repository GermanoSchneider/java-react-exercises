package com.feefo.note_web_app_web_service.infrastructure.note;

import com.feefo.note_web_app_web_service.domain.note.Note;
import com.feefo.note_web_app_web_service.infrastructure.note.persistence.NoteEntity;
import com.feefo.note_web_app_web_service.infrastructure.note.persistence.NoteEntity.NoteEntityBuilder;
import com.feefo.note_web_app_web_service.infrastructure.user.UserMapper;
import org.aspectj.weaver.ast.Not;

import java.time.LocalDateTime;

import static com.feefo.note_web_app_web_service.domain.note.Note.NoteBuilder;
import static com.feefo.note_web_app_web_service.domain.note.Note.builder;
import static com.feefo.note_web_app_web_service.infrastructure.user.UserMapper.*;

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
                note.getText(),
                note.getCreation(),
                note.getLastUpdate()
        );
    }

    public static Note fromNoteRequestDto(NoteRequestDto dto) {

        return builder()
                .text(dto.getText())
                .creation(LocalDateTime.now())
                .lastUpdate(LocalDateTime.now())
                .build();
    }
}
