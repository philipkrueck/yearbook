package de.pomc.yearbook.web;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class UserViewModel {

    @Getter
    private String name;

    @Getter
    private String email;
}
