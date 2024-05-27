package com.feefo.note_web_app_web_service.infrastructure.note;

public class NoteRequestDto {

    private final String text;

    NoteRequestDto(String text) {
        this.text = text;
    }

    String getText() {
        return text;
    }
}
