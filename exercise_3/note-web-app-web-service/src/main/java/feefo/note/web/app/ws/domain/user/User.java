package feefo.note.web.app.ws.domain.user;

import static feefo.note.web.app.ws.domain.ConstraintValidator.validate;

import feefo.note.web.app.ws.domain.note.Note;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Collection;

public class User {

    private final Long id;

    @NotBlank(message = "should not be blank")
    private final String name;

    @NotBlank(message = "should not be blank")
    private final String password;

    private final Collection<Note> notes = new ArrayList<>();

    private User(UserBuilder userBuilder) {
        this.id = userBuilder.id;
        this.name = userBuilder.name;
        this.password = userBuilder.password;
        validate(this);
    }

    public static class UserBuilder {

        private Long id;

        private String name;

        private String password;

        public UserBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public UserBuilder name(String name) {
            this.name = name;
            return this;
        }

        public UserBuilder password(String password) {
            this.password = password;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public Collection<Note> getNotes() {
        return notes;
    }
}