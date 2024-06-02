package feefo.note.web.app.ws.domain.user;

// User repository domain service
public interface UserRepository {

    User save(User user);

    User findByName(String name);
}
