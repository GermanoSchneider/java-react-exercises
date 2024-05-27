package com.feefo.note_web_app_web_service.infrastructure.note;

import com.feefo.note_web_app_web_service.application.NoteApplicationService;
import com.feefo.note_web_app_web_service.application.UserApplicationService;
import com.feefo.note_web_app_web_service.domain.note.Note;
import com.feefo.note_web_app_web_service.domain.user.User;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.Collection;

@RestController
@RequestMapping(path = "/notes")
class NoteController {

    private final NoteApplicationService noteApplicationService;
    private final UserApplicationService userApplicationService;

    NoteController(NoteApplicationService noteApplicationService,
        UserApplicationService userApplicationService) {
        this.noteApplicationService = noteApplicationService;
        this.userApplicationService = userApplicationService;
    }

    @PostMapping
    ResponseEntity<Void> create(
            @RequestBody NoteRequestDto dto,
            UriComponentsBuilder uriComponentsBuilder,
            Principal principal
    ) {

        User user = userApplicationService.findBy(principal.getName());
        Note note = NoteMapper.from(dto, user);

        noteApplicationService.create(note);

        URI locationOfNote = uriComponentsBuilder
                .path("/notes")
                .build()
                .toUri();

        return ResponseEntity.created(locationOfNote).build();
    }

    @GetMapping
    ResponseEntity<Collection<NoteResponseDto>> findAll(Principal principal) {

        Collection<Note> notes = noteApplicationService.findAllBy(principal.getName());

        Collection<NoteResponseDto> response = notes
                .stream()
                .map(NoteMapper::toNoteResponseDto)
                .toList();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}")
    ResponseEntity<Void> update(
            @PathVariable Long id,
            @RequestBody NoteRequestDto request,
            Principal principal
    ) {

        Optional<Note> note = noteApplicationService.update(
                id,
                request.getText(),
                principal.getName()
            );

        if (note.isEmpty()) return ResponseEntity.notFound().build();

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(
            @PathVariable Long id,
            Principal principal
    ) {

        noteApplicationService.deleteBy(id, principal.getName());

        return ResponseEntity.noContent().build();
    }
}
