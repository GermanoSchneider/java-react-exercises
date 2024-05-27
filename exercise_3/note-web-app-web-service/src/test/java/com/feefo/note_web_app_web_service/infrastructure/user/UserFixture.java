package com.feefo.note_web_app_web_service.infrastructure.user;

import com.feefo.note_web_app_web_service.domain.note.Note;
import com.feefo.note_web_app_web_service.domain.user.User;
import com.feefo.note_web_app_web_service.infrastructure.note.NoteFixture;
import com.feefo.note_web_app_web_service.infrastructure.note.NoteMapper;
import com.feefo.note_web_app_web_service.infrastructure.note.persistence.NoteEntity;

import com.feefo.note_web_app_web_service.infrastructure.user.persistence.UserEntity;
import java.util.Collection;
import java.util.stream.Collectors;

public class UserFixture {

    private UserFixture() {}

    public static UserEntity buildFrom(User user) {

        return UserEntity
                .builder()
                .id(user.getId())
                .name(user.getName())
                .password(user.getPassword())
                .notes(getNoteEntitiesFrom(user.getNotes()))
                .build();
    }

    public static User buildFrom(UserEntity user) {

        return User
                .builder()
                .id(user.getId())
                .name(user.getName())
                .password(user.getPassword())
                .build();
    }

    private static Collection<NoteEntity> getNoteEntitiesFrom(Collection<Note> notes) {

        return notes
                .stream()
                .map(NoteFixture::buildFrom)
                .collect(Collectors.toList());
    }

    private static Collection<Note> getNotesFrom(Collection<NoteEntity> notes) {

        return notes
                .stream()
                .map(noteEntity -> NoteMapper.from(noteEntity).build())
                .collect(Collectors.toList());
    }
}
