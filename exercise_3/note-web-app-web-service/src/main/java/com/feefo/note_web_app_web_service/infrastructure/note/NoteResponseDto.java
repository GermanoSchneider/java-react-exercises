package com.feefo.note_web_app_web_service.infrastructure.note;

import java.time.LocalDateTime;

class NoteResponseDto {

    private final String text;

    private final LocalDateTime creation;

    private final LocalDateTime lastUpdate;

    NoteResponseDto(String text, LocalDateTime creation, LocalDateTime lastUpdate) {
        this.text = text;
        this.creation = creation;
        this.lastUpdate = lastUpdate;
    }

    String getText() {
        return text;
    }

    LocalDateTime getCreation() {
        return creation;
    }

    LocalDateTime getLastUpdate() {
        return lastUpdate;
    }
}
