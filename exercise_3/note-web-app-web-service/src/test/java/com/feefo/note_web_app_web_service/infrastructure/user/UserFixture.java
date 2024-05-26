package com.feefo.note_web_app_web_service.infrastructure.user;

import com.feefo.note_web_app_web_service.domain.note.Note;
import com.feefo.note_web_app_web_service.domain.user.User;
import com.feefo.note_web_app_web_service.infrastructure.note.NoteEntity;
import com.feefo.note_web_app_web_service.infrastructure.note.NoteFixture;

import java.util.Collection;
import java.util.stream.Collectors;

class UserFixture {

    private UserFixture() {}

    static UserEntity buildFrom(User user) {

        return UserEntity
                .builder()
                .id(user.getId())
                .name(user.getName())
                .password(user.getPassword())
                .notes(getNoteEntitiesFrom(user.getNotes()))
                .build();
    }

    private static Collection<NoteEntity> getNoteEntitiesFrom(Collection<Note> notes) {

        return notes
                .stream()
                .map(NoteFixture::buildFrom)
                .collect(Collectors.toList());
    }
}
