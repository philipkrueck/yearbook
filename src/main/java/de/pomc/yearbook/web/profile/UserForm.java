package de.pomc.yearbook.web.profile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UserForm {

    private String firstName;

    private String email;

    private String twitterHandle;

    private String location;

    private String website;

    private String bio;
}
