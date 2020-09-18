package de.pomc.yearbook.web.book;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@Getter
@Setter
public class CreateBookForm extends BookForm {
    private MultipartFile image;
}
