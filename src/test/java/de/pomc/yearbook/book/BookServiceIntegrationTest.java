package de.pomc.yearbook.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

@SpringBootTest
@Transactional
public class BookServiceIntegrationTest {

    @Autowired
    private BookService bookService;
}
