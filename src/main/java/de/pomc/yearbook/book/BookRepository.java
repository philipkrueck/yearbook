package de.pomc.yearbook.book;


import de.pomc.yearbook.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findBooksByOwner(User owner);

    @Query("select b from Book b where b.published = true")
    List<Book> findPublishedBooks();

}
