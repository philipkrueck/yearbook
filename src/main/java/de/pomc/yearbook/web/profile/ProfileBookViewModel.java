package de.pomc.yearbook.web.profile;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
public class ProfileBookViewModel {

    @Getter
    private Long id;

    @Getter
    private String bookName;

    @Getter
    public static List<ProfileBookViewModel> sampleData = List.of(
        new ProfileBookViewModel((long) 1, "Graduation 2020"),
        new ProfileBookViewModel((long) 2, "HSBA BI '21")
    );
}
