package com.feefo.note_web_app_web_service.infrastructure.note;

import org.springframework.data.jpa.repository.JpaRepository;

interface NoteJpaRepository extends JpaRepository<NoteEntity, Long> {}
