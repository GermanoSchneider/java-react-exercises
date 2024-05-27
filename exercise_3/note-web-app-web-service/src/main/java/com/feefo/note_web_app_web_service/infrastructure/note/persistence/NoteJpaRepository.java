package com.feefo.note_web_app_web_service.infrastructure.note.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

interface NoteJpaRepository extends JpaRepository<NoteEntity, Long> {

    Collection<NoteEntity> findByUserName(String name);
}
