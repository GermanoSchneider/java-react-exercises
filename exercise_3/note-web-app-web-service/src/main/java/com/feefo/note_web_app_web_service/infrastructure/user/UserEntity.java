package com.feefo.note_web_app_web_service.infrastructure.user;

import com.feefo.note_web_app_web_service.infrastructure.note.persistence.NoteEntity;
import jakarta.persistence.*;

import java.util.Collection;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = SEQUENCE)
    private Long id;

    @Column(unique = true)
    private String name;

    private String password;

    @OneToMany(cascade = ALL, fetch = FetchType.EAGER)
    private Collection<NoteEntity> notes;

    UserEntity() {}

    private UserEntity(UserEntityBuilder userEntityBuilder) {
        this.id = userEntityBuilder.id;
        this.name = userEntityBuilder.name;
        this.password = userEntityBuilder.password;
        this.notes = userEntityBuilder.notes;
    }

    public static class UserEntityBuilder {

        private Long id;

        private String name;

        private String password;

        private Collection<NoteEntity> notes;

        public UserEntityBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public UserEntityBuilder name(String name) {
            this.name = name;
            return this;
        }

        public UserEntityBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserEntityBuilder notes(Collection<NoteEntity> notes) {
            this.notes = notes;
            return this;
        }

        public UserEntity build() {
            return new UserEntity(this);
        }

    }

    public static UserEntityBuilder builder() {
        return new UserEntityBuilder();
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

    public Collection<NoteEntity> getNotes() {
        return notes;
    }
}
