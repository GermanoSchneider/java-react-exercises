package note.web.app.ws.infrastructure.user;

import java.util.Collection;
import java.util.stream.Collectors;
import note.web.app.ws.domain.note.Note;
import note.web.app.ws.domain.user.User;
import note.web.app.ws.infrastructure.note.NoteMapper;
import note.web.app.ws.infrastructure.note.persistence.NoteEntity;
import note.web.app.ws.infrastructure.user.controller.UserRequestDto;
import note.web.app.ws.infrastructure.user.controller.UserResponseDto;
import note.web.app.ws.infrastructure.user.persistence.UserEntity;
import org.springframework.stereotype.Component;

// User mapper is responsible to convert user objects from the anti-corruption layer
@Component
public class UserMapper {

    private final NoteMapper mapper;

    public UserMapper(NoteMapper mapper) {
        this.mapper = mapper;
    }

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

    private Collection<NoteEntity> getNoteEntitiesFrom(Collection<Note> notes) {

        return notes
            .stream()
            .map(mapper::toEntity)
            .collect(Collectors.toList());
    }

    private Collection<Note> getNotesFrom(Collection<NoteEntity> notes) {

        return notes
                .stream()
                .map(mapper::fromEntity)
                .collect(Collectors.toList());
    }
}
