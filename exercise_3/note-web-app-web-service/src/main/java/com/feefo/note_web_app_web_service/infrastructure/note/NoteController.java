package com.feefo.note_web_app_web_service.infrastructure.note;

import com.feefo.note_web_app_web_service.application.NoteApplicationService;
import com.feefo.note_web_app_web_service.domain.note.Note;
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

    NoteController(NoteApplicationService noteApplicationService) {
        this.noteApplicationService = noteApplicationService;
    }

    @PostMapping(path = "/create")
    ResponseEntity<Void> create(
            @RequestBody NoteRequestDto dto,
            UriComponentsBuilder uriComponentsBuilder
    ) {

        Note note = NoteMapper.fromNoteRequestDto(dto);

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
            @RequestBody NoteRequestDto request
    ) {

        try {
            noteApplicationService.update(id, request.getText());
            return ResponseEntity.noContent().build();
        } catch (Exception exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(
            @PathVariable Long id
    ) {

        try {
            noteApplicationService.deleteBy(id);
            return ResponseEntity.noContent().build();
        } catch (Exception exception) {
            return ResponseEntity.notFound().build();
        }
    }
}
