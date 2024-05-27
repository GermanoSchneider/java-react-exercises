package com.feefo.note_web_app_web_service.infrastructure.user;

import com.feefo.note_web_app_web_service.domain.note.Note;
import com.feefo.note_web_app_web_service.domain.user.User;
import com.feefo.note_web_app_web_service.infrastructure.note.persistence.NoteEntity;
import com.feefo.note_web_app_web_service.infrastructure.note.NoteMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.stream.Collectors;

public class UserMapper {

    private static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    private UserMapper() {}

    public static UserEntity.UserEntityBuilder from(User user) {

        return UserEntity
                .builder()
                .id(user.getId())
                .name(user.getName())
                .password(PASSWORD_ENCODER.encode(user.getPassword()))
                .notes(getNoteEntitiesFrom(user.getNotes()));
    }

    public static User.UserBuilder from(UserEntity userEntity) {

        return User.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .password(userEntity.getPassword())
                .notes(getNotesFrom(userEntity.getNotes()));
    }

    private static Collection<NoteEntity> getNoteEntitiesFrom(Collection<Note> notes) {

        return notes
                .stream()
                .map(note -> NoteMapper.from(note).build())
                .collect(Collectors.toList());
    }

    private static Collection<Note> getNotesFrom(Collection<NoteEntity> notes) {

        return notes
                .stream()
                .map(noteEntity -> NoteMapper.from(noteEntity).build())
                .collect(Collectors.toList());
    }
}
