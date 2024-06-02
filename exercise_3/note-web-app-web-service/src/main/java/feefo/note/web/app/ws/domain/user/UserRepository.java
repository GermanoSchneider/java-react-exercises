package feefo.note.web.app.ws.domain.user;

public interface UserRepository {

    User save(User user);

    User findByName(String name);
}
