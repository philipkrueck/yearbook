package de.pomc.yearbook.user;

import lombok.*;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private Long id;

    private String email;

    private String role; // ToDo: model this with an enum

    private String password;

    private String twitterHandle;

    private String location;

    private String website;

    @Basic(optional = false)
    private String name;

    public User(String name) {
        this.name = name;
    }

    public User(Long id, String name, String email) {
        this.name = name;
        this.email = email;
        this.id = id;
    }
}
