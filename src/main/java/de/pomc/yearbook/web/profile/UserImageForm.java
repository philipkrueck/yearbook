package de.pomc.yearbook.web.profile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UserImageForm {
    private byte[] image;
}