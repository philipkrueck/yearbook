package de.pomc.yearbook.web.book;

import de.pomc.yearbook.participation.Participation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

public class PromotionForm {

    @Getter
    @Setter
    private List<Boolean> adminList;

    public PromotionForm(List<Participation> participations) {
        this.adminList = participations.stream().map(Participation::isAdmin).collect(Collectors.toList());
    }
}
