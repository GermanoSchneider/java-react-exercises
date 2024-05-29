package com.feefo.note_web_app_web_service.infrastructure.user;

import com.feefo.note_web_app_web_service.domain.note.Note;
import com.feefo.note_web_app_web_service.domain.user.User;
import com.feefo.note_web_app_web_service.infrastructure.note.persistence.NoteEntity;
import com.feefo.note_web_app_web_service.infrastructure.note.NoteMapper;
import com.feefo.note_web_app_web_service.infrastructure.user.controller.UserRequestDto;
import com.feefo.note_web_app_web_service.infrastructure.user.controller.UserResponseDto;
import com.feefo.note_web_app_web_service.infrastructure.user.persistence.UserEntity;

import java.util.Collection;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User fromEntity(UserEntity userEntity) {

        User user = User.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .password(userEntity.getPassword())
                .build();

        user.getNotes().addAll(getNotesFrom(userEntity.getNotes()));

        return user;
    }

    public User fromRequest(UserRequestDto requestDto) {

        return User.builder()
            .name(requestDto.getUsername())
            .password(requestDto.getPassword())
            .build();
    }

    public UserResponseDto toResponse(User user) {

        return new UserResponseDto(
          user.getId(),
          user.getName()
        );
    }

    public UserEntity toEntity(User user) {

        return UserEntity.builder()
            .name(user.getName())
            .password(user.getPassword())
            .notes(getNoteEntitiesFrom(user.getNotes()))
            .build();
    }

    private static Collection<NoteEntity> getNoteEntitiesFrom(Collection<Note> notes) {

        return notes
            .stream()
            .map(note -> NoteMapper.from(note).build())
            .collect(Collectors.toList());
    }

    private Collection<Note> getNotesFrom(Collection<NoteEntity> notes) {

        return notes
                .stream()
                .map(noteEntity -> NoteMapper.from(noteEntity).build())
                .collect(Collectors.toList());
    }
}
