package lt.techin.booksBackend.repository;

import lt.techin.booksBackend.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

  boolean existsByTitle(String title);

  boolean existsByAuthor(String author);

}
