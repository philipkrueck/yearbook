package de.pomc.yearbook.user;

import lombok.*;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;

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

    @Basic(optional = false)
    private String email;

    @Basic(optional = false)
    private String role; // ToDo: model this with an enum

    @Basic(optional = false)
    private String firstName;

    @Basic(optional = false)
    private String lastName;

    @Basic(optional = false)
    private String password;

    private String twitterHandle;

    private String location;

    private String website;

    private String bio;

    @Lob
    private byte[] image;

    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = "USER";
    }

    public User(Long id, String firstName, String lastName, String email, String password, String role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public static String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null &&  !(authentication instanceof AnonymousAuthenticationToken)) {
            return authentication.getName();
        }
        return null;
    }

    public String getFullName() {
        return getFirstName() + " " + getLastName();
    }
}
