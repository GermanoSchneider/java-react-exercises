package com.feefo.note_web_app_web_service.domain.user;

public interface UserRepository {

    User save(User user);

    User findBy(Long id);
}
