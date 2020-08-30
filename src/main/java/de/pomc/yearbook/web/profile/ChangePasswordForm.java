package de.pomc.yearbook.web.profile;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordForm {

    String oldPasword = "";
    String newPasswordOne = "";
    String newPasswordTwo = "";
}
