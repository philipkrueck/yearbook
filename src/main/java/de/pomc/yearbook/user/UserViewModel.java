package de.pomc.yearbook.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class UserViewModel {

    @Getter
    private String name;

    @Getter
    private String email;
}
