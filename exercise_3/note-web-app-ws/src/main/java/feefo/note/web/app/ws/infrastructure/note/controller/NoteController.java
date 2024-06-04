package feefo.note.web.app.ws.infrastructure.note.controller;

import feefo.note.web.app.ws.application.NoteApplicationService;
import feefo.note.web.app.ws.domain.note.Note;
import feefo.note.web.app.ws.infrastructure.note.NoteMapper;
import java.net.URI;
import java.security.Principal;
import java.util.Collection;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

// Note controller operations represented by a CRUD
@RestController
@RequestMapping(path = "/notes")
class NoteController {

    private final NoteApplicationService noteApplicationService;

    private final NoteMapper mapper;

    NoteController(NoteApplicationService noteApplicationService, NoteMapper mapper) {
        this.noteApplicationService = noteApplicationService;
        this.mapper = mapper;
    }

    @PostMapping
    ResponseEntity<NoteResponseDto> create(
            @RequestBody NoteRequestDto dto,
            UriComponentsBuilder uriComponentsBuilder,
            Principal principal
    ) {

        Note note = mapper.from(dto, principal.getName());

        Note createdNote = noteApplicationService.create(note);

        URI locationOfNote = uriComponentsBuilder
                .path("/notes")
                .build()
                .toUri();

        NoteResponseDto response = mapper.toResponse(createdNote);

        return ResponseEntity
            .created(locationOfNote)
            .body(response);
    }

    @GetMapping
    ResponseEntity<Collection<NoteResponseDto>> findAll(Principal principal) {

        Collection<Note> notes = noteApplicationService.findAllBy(principal.getName());

        Collection<NoteResponseDto> response = notes
                .stream()
                .map(mapper::toResponse)
                .toList();

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    ResponseEntity<NoteResponseDto> update(
            @PathVariable Long id,
            @RequestBody NoteRequestDto request,
            Principal principal
    ) {

        Note updatedNote = noteApplicationService
            .update(id, request.getText(), principal.getName());

        NoteResponseDto response = mapper.toResponse(updatedNote);

        return ResponseEntity.ok(response);
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
