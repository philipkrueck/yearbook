package de.pomc.yearbook.web.book;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/book/{id}")
public class BookController {

    @GetMapping
    public String show(@PathVariable("id") Long id) {
        return "pages/book/book";
    }
}
