package de.pomc.yearbook.web.profile;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
public class ProfileParticipationViewModel {

    @Getter
    private Long questionnaireId;

    @Getter
    private String bookName;

    public static List<ProfileParticipationViewModel> sampleData = List.of(
        new ProfileParticipationViewModel((long) 1, "Graduation 2020"),
        new ProfileParticipationViewModel((long) 2, "HSBA BI 2021"),
        new ProfileParticipationViewModel((long) 5, "NYU Gender Sciences 2019")
    );

}
