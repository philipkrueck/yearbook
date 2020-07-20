package de.pomc.yearbook.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/addBook")
public class AddBookController {

    @GetMapping
    public String showAddBookView(Model model) {
        model.addAttribute("addBookViewModel", new AddBookViewModel());
        return "pages/addBook/addBook";
    }

    @PostMapping("/new")
    public String createNewBook(@ModelAttribute AddBookViewModel addBookViewModel) {
        System.out.println("title: " + addBookViewModel.getTitle());
        System.out.println("description: " + addBookViewModel.getDescription());
        return "redirect:/book/1/";
    }

}
