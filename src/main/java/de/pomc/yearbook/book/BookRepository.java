package de.pomc.yearbook.book;


import de.pomc.yearbook.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findBooksByOwner(User owner);

}
