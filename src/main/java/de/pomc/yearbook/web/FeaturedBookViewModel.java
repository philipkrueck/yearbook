package de.pomc.yearbook.web;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
public class FeaturedBookViewModel {

    @Getter
    private int id;

    @Getter
    private String bookTitle;

    // TODO: add image

    public static List<FeaturedBookViewModel> sampleData = List.of(
        new FeaturedBookViewModel(1,"Blue Mountain State 2020"),
        new FeaturedBookViewModel(2, "HSBA BI '21"),
        new FeaturedBookViewModel(3, "Stanford Law '19"),
        new FeaturedBookViewModel(4, "MIT Robotics 2020"),
            new FeaturedBookViewModel(5, "NYU Gender Sciences 2019")
    );
}
