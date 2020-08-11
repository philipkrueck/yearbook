package de.pomc.yearbook.web;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
public class IndexViewModel {

    @Getter
    List<FeaturedBookViewModel> featuredBookViewModels;
}
