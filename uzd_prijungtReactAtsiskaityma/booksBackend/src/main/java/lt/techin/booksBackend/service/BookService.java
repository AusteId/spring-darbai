package lt.techin.booksBackend.service;

import lt.techin.booksBackend.model.Book;
import lt.techin.booksBackend.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

  private final BookRepository bookRepository;

  @Autowired
  public BookService(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  public List<Book> findAllBooks() {
    return bookRepository.findAll();
  }

  public Optional<Book> findBookById(long id) {
    return bookRepository.findById(id);
  }

  public boolean existsBookByTitle(String title) {
    return bookRepository.existsByTitle(title);
  }

  public boolean existsBookByAuthor(String author) {
    return bookRepository.existsByAuthor(author);
  }

  public Book saveBook(Book book) {
    return bookRepository.save(book);
  }

  public boolean existsBookById(long id) {
    return bookRepository.existsById(id);
  }

  public void deleteBookById(long id) {
    bookRepository.deleteById(id);
  }
}
